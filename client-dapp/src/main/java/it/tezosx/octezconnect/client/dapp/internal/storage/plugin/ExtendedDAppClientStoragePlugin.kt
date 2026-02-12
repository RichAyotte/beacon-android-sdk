package it.tezosx.octezconnect.client.dapp.internal.storage.plugin

import it.tezosx.octezconnect.core.internal.BeaconConfiguration
import it.tezosx.octezconnect.core.scope.BeaconScope

public interface ExtendedDAppClientStoragePlugin : DAppClientStoragePlugin {
    public suspend fun removeActiveAccount()
    public suspend fun removeActivePeer()

    override fun scoped(beaconScope: BeaconScope): ExtendedDAppClientStoragePlugin
    override fun extend(beaconConfiguration: BeaconConfiguration): ExtendedDAppClientStoragePlugin = this
}