package it.tezosx.octezconnect.client.dapp.internal.storage.plugin

import it.tezosx.octezconnect.client.dapp.data.PairedAccount
import it.tezosx.octezconnect.core.internal.BeaconConfiguration
import it.tezosx.octezconnect.core.scope.BeaconScope
import it.tezosx.octezconnect.core.storage.StoragePlugin
import it.tezosx.octezconnect.client.dapp.internal.storage.decorator.DecoratedDAppClientStoragePlugin

public interface DAppClientStoragePlugin : StoragePlugin {
    public suspend fun getActiveAccount(): PairedAccount?
    public suspend fun setActiveAccount(account: PairedAccount?)

    public suspend fun getActivePeer(): String?
    public suspend fun setActivePeer(peerId: String?)

    override fun scoped(beaconScope: BeaconScope): DAppClientStoragePlugin
    public fun extend(beaconConfiguration: BeaconConfiguration): ExtendedDAppClientStoragePlugin = DecoratedDAppClientStoragePlugin(this, beaconConfiguration)
}