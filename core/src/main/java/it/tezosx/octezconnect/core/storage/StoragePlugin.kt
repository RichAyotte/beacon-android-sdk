package it.tezosx.octezconnect.core.storage

import it.tezosx.octezconnect.core.scope.BeaconScope

public interface StoragePlugin {
    public fun scoped(beaconScope: BeaconScope): StoragePlugin
}