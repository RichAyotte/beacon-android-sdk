package it.tezosx.octezconnect.core.internal.storage

import it.tezosx.octezconnect.core.scope.BeaconScope
import it.tezosx.octezconnect.core.storage.SecureStorage
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

public class MockSecureStorage : SecureStorage {
    private val mutex: Mutex = Mutex()

    private var sdkSecretSeed: String? = null

    override suspend fun getSdkSecretSeed(): String? = mutex.withLock { sdkSecretSeed }
    override suspend fun setSdkSecretSeed(sdkSecretSeed: String) {
        mutex.withLock {
            this.sdkSecretSeed = sdkSecretSeed
        }
    }

    override fun scoped(beaconScope: BeaconScope): SecureStorage = this
}