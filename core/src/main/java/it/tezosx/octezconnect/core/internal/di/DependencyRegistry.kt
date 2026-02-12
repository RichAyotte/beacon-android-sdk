package it.tezosx.octezconnect.core.internal.di

import androidx.annotation.RestrictTo
import it.tezosx.octezconnect.core.data.Connection
import it.tezosx.octezconnect.core.internal.blockchain.BlockchainRegistry
import it.tezosx.octezconnect.core.internal.compat.Compat
import it.tezosx.octezconnect.core.internal.compat.VersionedCompat
import it.tezosx.octezconnect.core.internal.controller.connection.ConnectionController
import it.tezosx.octezconnect.core.internal.controller.message.MessageController
import it.tezosx.octezconnect.core.internal.crypto.Crypto
import it.tezosx.octezconnect.core.internal.migration.Migration
import it.tezosx.octezconnect.core.internal.network.HttpClient
import it.tezosx.octezconnect.core.internal.serializer.Serializer
import it.tezosx.octezconnect.core.internal.storage.StorageManager
import it.tezosx.octezconnect.core.internal.transport.Transport
import it.tezosx.octezconnect.core.internal.utils.Base58
import it.tezosx.octezconnect.core.internal.utils.Base58Check
import it.tezosx.octezconnect.core.internal.utils.IdentifierCreator
import it.tezosx.octezconnect.core.internal.utils.Logger
import it.tezosx.octezconnect.core.internal.utils.Poller
import it.tezosx.octezconnect.core.network.provider.HttpClientProvider
import it.tezosx.octezconnect.core.scope.BeaconScope
import kotlinx.serialization.json.Json
import kotlin.reflect.KClass

public interface DependencyRegistry {

    public val beaconScope: BeaconScope

    // -- extended --

    public val extended: Map<String, DependencyRegistry>
    public fun addExtended(extended: DependencyRegistry)
    public fun <T : DependencyRegistry> findExtended(targetClass: KClass<T>): T?

    // -- storage --

    public val storageManager: StorageManager

    // -- blockchain --

    public val blockchainRegistry: BlockchainRegistry

    // -- controller --

    public val messageController: MessageController
    public fun connectionController(connections: List<Connection>): ConnectionController

    // -- transport --

    public fun transport(connection: Connection): Transport

    // -- utils --

    public val crypto: Crypto
    public val serializer: Serializer

    public val identifierCreator: IdentifierCreator
    public val base58: Base58
    public val base58Check: Base58Check
    public val poller: Poller

    public fun logger(tag: String): Logger?

    // -- network --

    public val json: Json
    public fun httpClient(httpClientProvider: HttpClientProvider?): HttpClient

    // -- migration --

    public val migration: Migration

    // -- compat --

    public val compat: Compat<VersionedCompat>
}

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public inline fun <reified T : DependencyRegistry> DependencyRegistry.findExtended(): T? = findExtended(T::class)