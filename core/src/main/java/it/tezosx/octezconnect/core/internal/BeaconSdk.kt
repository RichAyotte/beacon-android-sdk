package it.tezosx.octezconnect.core.internal

import android.content.Context
import androidx.annotation.RestrictTo
import it.tezosx.octezconnect.core.blockchain.Blockchain
import it.tezosx.octezconnect.core.internal.crypto.Crypto
import it.tezosx.octezconnect.core.internal.crypto.data.KeyPair
import it.tezosx.octezconnect.core.internal.data.BeaconApplication
import it.tezosx.octezconnect.core.internal.di.CoreDependencyRegistry
import it.tezosx.octezconnect.core.internal.di.DependencyRegistry
import it.tezosx.octezconnect.core.internal.storage.StorageManager
import it.tezosx.octezconnect.core.internal.utils.failWithUninitialized
import it.tezosx.octezconnect.core.internal.utils.toHexString
import it.tezosx.octezconnect.core.scope.BeaconScope
import it.tezosx.octezconnect.core.storage.SecureStorage
import it.tezosx.octezconnect.core.storage.Storage

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class BeaconSdk(context: Context) {
    public val applicationContext: Context = context.applicationContext
    private val beaconContext: MutableMap<BeaconScope, BeaconContext> = mutableMapOf()

    public val beaconScopes: Set<BeaconScope>
        get() = beaconContext.keys

    public fun dependencyRegistry(beaconScope: BeaconScope): DependencyRegistry =
        beaconContext[beaconScope]?.dependencyRegistry ?: failWithUninitialized(beaconScope)

    public fun app(beaconScope: BeaconScope): BeaconApplication =
        beaconContext[beaconScope]?.app ?: failWithUninitialized(beaconScope)

    public fun beaconId(beaconScope: BeaconScope): String = app(beaconScope).keyPair.publicKey.toHexString().asString()

    public suspend fun add(
        beaconScope: BeaconScope,
        partialApp: BeaconApplication.Partial,
        configuration: BeaconConfiguration,
        blockchainFactories: List<Blockchain.Factory<*>>,
        storage: Storage,
        secureStorage: SecureStorage,
    ) {
        if (beaconContext.containsKey(beaconScope)) return

        val dependencyRegistry = CoreDependencyRegistry(beaconScope, blockchainFactories, storage, secureStorage, configuration)

        val storageManager = dependencyRegistry.storageManager
        val crypto = dependencyRegistry.crypto

        setSdkVersion(storageManager)

        val app = partialApp.toFinal(loadOrGenerateKeyPair(storageManager, crypto))

        beaconContext[beaconScope] = BeaconContext(app, dependencyRegistry)
    }

    private suspend fun setSdkVersion(storageManager: StorageManager) {
        storageManager.setSdkVersion(BeaconConfiguration.sdkVersion)
    }

    private suspend fun loadOrGenerateKeyPair(storageManager: StorageManager, crypto: Crypto): KeyPair {
        val seed = storageManager.getSdkSecretSeed()
            ?: crypto.guid().getOrThrow().also { storageManager.setSdkSecretSeed(it) }

        return crypto.getKeyPairFromSeed(seed).getOrThrow()
    }

    public companion object {
        internal const val TAG = "BeaconSdk"

        @Suppress("ObjectPropertyName")
        private var _instance: BeaconSdk? = null
        public var instance: BeaconSdk
            get() = _instance ?: failWithUninitialized(TAG)
            private set(value) {
                _instance = value
            }

        internal fun create(context: Context) {
            instance = BeaconSdk(context)
        }
    }
}