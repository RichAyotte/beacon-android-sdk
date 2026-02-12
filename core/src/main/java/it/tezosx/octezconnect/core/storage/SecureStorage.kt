package it.tezosx.octezconnect.core.storage

import it.tezosx.octezconnect.core.scope.BeaconScope

public interface SecureStorage {
    public suspend fun getSdkSecretSeed(): String?
    public suspend fun setSdkSecretSeed(sdkSecretSeed: String)

    public fun scoped(beaconScope: BeaconScope): SecureStorage
}