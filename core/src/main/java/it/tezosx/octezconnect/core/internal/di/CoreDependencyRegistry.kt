package it.tezosx.octezconnect.core.internal.di

import it.tezosx.octezconnect.core.blockchain.Blockchain
import it.tezosx.octezconnect.core.configuration.LogLevel
import it.tezosx.octezconnect.core.data.Connection
import it.tezosx.octezconnect.core.data.P2P
import it.tezosx.octezconnect.core.internal.BeaconConfiguration
import it.tezosx.octezconnect.core.internal.blockchain.BlockchainRegistry
import it.tezosx.octezconnect.core.internal.compat.Compat
import it.tezosx.octezconnect.core.internal.compat.CoreCompat
import it.tezosx.octezconnect.core.internal.compat.VersionedCompat
import it.tezosx.octezconnect.core.internal.controller.connection.ConnectionController
import it.tezosx.octezconnect.core.internal.controller.message.MessageController
import it.tezosx.octezconnect.core.internal.crypto.Crypto
import it.tezosx.octezconnect.core.internal.crypto.provider.CryptoProvider
import it.tezosx.octezconnect.core.internal.crypto.provider.LazySodiumCryptoProvider
import it.tezosx.octezconnect.core.internal.migration.CoreMigration
import it.tezosx.octezconnect.core.internal.migration.Migration
import it.tezosx.octezconnect.core.internal.network.HttpClient
import it.tezosx.octezconnect.core.internal.network.provider.KtorHttpClientProvider
import it.tezosx.octezconnect.core.internal.serializer.Serializer
import it.tezosx.octezconnect.core.internal.serializer.coreJson
import it.tezosx.octezconnect.core.internal.serializer.provider.Base58CheckSerializerProvider
import it.tezosx.octezconnect.core.internal.serializer.provider.SerializerProvider
import it.tezosx.octezconnect.core.internal.storage.StorageManager
import it.tezosx.octezconnect.core.internal.transport.Transport
import it.tezosx.octezconnect.core.internal.transport.p2p.P2pTransport
import it.tezosx.octezconnect.core.internal.transport.p2p.store.P2pTransportStore
import it.tezosx.octezconnect.core.internal.utils.Base58
import it.tezosx.octezconnect.core.internal.utils.Base58Check
import it.tezosx.octezconnect.core.internal.utils.IdentifierCreator
import it.tezosx.octezconnect.core.internal.utils.Logger
import it.tezosx.octezconnect.core.internal.utils.Poller
import it.tezosx.octezconnect.core.internal.utils.delegate.lazyWeak
import it.tezosx.octezconnect.core.network.provider.HttpClientProvider
import it.tezosx.octezconnect.core.scope.BeaconScope
import it.tezosx.octezconnect.core.storage.SecureStorage
import it.tezosx.octezconnect.core.storage.Storage
import kotlinx.serialization.json.Json
import kotlin.reflect.KClass

internal class CoreDependencyRegistry(
    override val beaconScope: BeaconScope,
    blockchainFactories: List<Blockchain.Factory<*>>,
    storage: Storage,
    secureStorage: SecureStorage,
    private val beaconConfiguration: BeaconConfiguration,
) : DependencyRegistry {

    // -- extended --

    private val _extended: MutableMap<String, DependencyRegistry> = mutableMapOf()
    override val extended: Map<String, DependencyRegistry>
        get() = _extended

    override fun addExtended(extended: DependencyRegistry) {
        val key = extended::class.simpleName ?: return
        _extended[key] = extended
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : DependencyRegistry> findExtended(targetClass: KClass<T>): T? {
        val key = targetClass.simpleName ?: return null
        return extended[key] as T?
    }

    // -- storage --

    override val storageManager: StorageManager by lazyWeak { StorageManager(beaconScope, storage.scoped(beaconScope), secureStorage.scoped(beaconScope), identifierCreator, beaconConfiguration) }

    // -- blockchain --

    override val blockchainRegistry: BlockchainRegistry by lazyWeak {
        val blockchainFactories = blockchainFactories.associate { it.identifier to { it.create(this) } }

        BlockchainRegistry(blockchainFactories)
    }

    // -- controller --

    override val messageController: MessageController by lazyWeak { MessageController(beaconScope, blockchainRegistry, storageManager, identifierCreator, compat) }

    override fun connectionController(connections: List<Connection>): ConnectionController {
        val transports = connections.distinctBy { it.type }.map { transport(it) }

        return ConnectionController(transports, serializer)
    }

    // -- transport --

    override fun transport(connection: Connection): Transport {
        val logger = logger("${Transport.TAG} ${connection.type}")

        return when (connection) {
            is P2P -> P2pTransport(storageManager, connection.client.create(this), p2pTransportStore, logger)
        }
    }

    private val p2pTransportStore: P2pTransportStore
        get() = P2pTransportStore()

    // -- utils --

    override val crypto: Crypto by lazyWeak { Crypto(cryptoProvider) }
    override val serializer: Serializer by lazyWeak { Serializer(serializerProvider) }

    override val identifierCreator: IdentifierCreator by lazyWeak { IdentifierCreator(crypto, base58Check) }
    override val base58: Base58 by lazyWeak { Base58() }
    override val base58Check: Base58Check by lazyWeak { Base58Check(base58, crypto) }
    override val poller: Poller by lazyWeak { Poller() }

    override fun logger(tag: String): Logger? =
        when (beaconConfiguration.logLevel) {
            LogLevel.Off -> null
            else -> Logger(tag, beaconConfiguration)
        }

    private val cryptoProvider: CryptoProvider by lazyWeak {
        when (BeaconConfiguration.cryptoProvider) {
            BeaconConfiguration.CryptoProvider.LazySodium -> LazySodiumCryptoProvider()
        }
    }
    private val serializerProvider: SerializerProvider by lazyWeak {
        when (BeaconConfiguration.serializerProvider) {
            BeaconConfiguration.SerializerProvider.Base58Check -> Base58CheckSerializerProvider(base58Check, json)
        }
    }

    // -- network --

    override val json: Json by lazy {
        Json(from = coreJson(blockchainRegistry, compat)) {
            prettyPrint = false
        }
    }

    private val httpClients: MutableMap<Int, HttpClient> = mutableMapOf()
    override fun httpClient(httpClientProvider: HttpClientProvider?): HttpClient {
        val httpClientProvider = httpClientProvider ?: this.httpClientProvider

        return httpClients.getOrPut(httpClientProvider.hashCode()) { HttpClient(httpClientProvider, json) }
    }

    private val httpClientProvider: HttpClientProvider by lazyWeak {
        when (BeaconConfiguration.httpClientProvider) {
            BeaconConfiguration.HttpClientProvider.Ktor -> KtorHttpClientProvider(
                json,
                logger("${KtorHttpClientProvider.TAG}\$$beaconScope")
            )
        }
    }

    // -- migration --

    override val migration: Migration by lazyWeak {
        CoreMigration(
            storageManager,
            listOf(),
            logger(CoreMigration.TAG),
        )
    }

    // -- compat --

    override val compat: Compat<VersionedCompat> by lazyWeak { CoreCompat(beaconScope) }
}