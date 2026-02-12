package it.tezosx.octezconnect.client.dapp.storage

import it.tezosx.octezconnect.core.internal.BeaconConfiguration
import it.tezosx.octezconnect.core.scope.BeaconScope
import it.tezosx.octezconnect.core.storage.ExtendedStorage
import it.tezosx.octezconnect.client.dapp.internal.storage.plugin.ExtendedDAppClientStoragePlugin

public interface ExtendedDAppClientStorage : ExtendedStorage, DAppClientStorage, ExtendedDAppClientStoragePlugin {
    override fun scoped(beaconScope: BeaconScope): ExtendedDAppClientStorage
    override fun extend(beaconConfiguration: BeaconConfiguration): ExtendedDAppClientStorage = this
}