package it.tezosx.octezconnect.client.dapp.storage

import it.tezosx.octezconnect.core.internal.BeaconConfiguration
import it.tezosx.octezconnect.core.scope.BeaconScope
import it.tezosx.octezconnect.core.storage.Storage
import it.tezosx.octezconnect.client.dapp.internal.storage.decorator.DecoratedDAppClientStorage
import it.tezosx.octezconnect.client.dapp.internal.storage.plugin.DAppClientStoragePlugin

public interface DAppClientStorage : Storage, DAppClientStoragePlugin {
    override fun scoped(beaconScope: BeaconScope): DAppClientStorage
    override fun extend(beaconConfiguration: BeaconConfiguration): ExtendedDAppClientStorage = DecoratedDAppClientStorage(this, beaconConfiguration)
}