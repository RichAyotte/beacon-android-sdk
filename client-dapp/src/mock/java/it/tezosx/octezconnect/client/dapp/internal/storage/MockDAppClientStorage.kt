package it.tezosx.octezconnect.client.dapp.internal.storage

import it.tezosx.octezconnect.client.dapp.internal.storage.decorator.DecoratedDAppClientStorage
import it.tezosx.octezconnect.client.dapp.storage.DAppClientStorage
import it.tezosx.octezconnect.client.dapp.storage.ExtendedDAppClientStorage
import it.tezosx.octezconnect.client.dapp.data.PairedAccount
import it.tezosx.octezconnect.core.internal.BeaconConfiguration
import it.tezosx.octezconnect.core.internal.storage.MockStorage
import it.tezosx.octezconnect.core.scope.BeaconScope
import it.tezosx.octezconnect.core.storage.Storage

public class MockDAppClientStorage : DAppClientStorage, Storage by MockStorage() {
    private var activeAccount: PairedAccount? = null
    private var activePeer: String? = null

    override suspend fun getActiveAccount(): PairedAccount? = activeAccount
    override suspend fun setActiveAccount(account: PairedAccount?) {
        this.activeAccount = account
    }

    override suspend fun getActivePeer(): String? = activePeer
    override suspend fun setActivePeer(peerId: String?) {
        this.activePeer = peerId
    }

    override fun scoped(beaconScope: BeaconScope): DAppClientStorage = this
    override fun extend(beaconConfiguration: BeaconConfiguration): ExtendedDAppClientStorage = DecoratedDAppClientStorage(this, beaconConfiguration)
}