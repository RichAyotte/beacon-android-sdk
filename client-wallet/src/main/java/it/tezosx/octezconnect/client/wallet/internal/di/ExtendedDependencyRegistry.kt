package it.tezosx.octezconnect.client.wallet.internal.di

import it.tezosx.octezconnect.client.wallet.BeaconWalletClient
import it.tezosx.octezconnect.core.data.Connection
import it.tezosx.octezconnect.core.internal.BeaconConfiguration
import it.tezosx.octezconnect.core.internal.di.DependencyRegistry
import it.tezosx.octezconnect.core.internal.di.findExtended

internal interface ExtendedDependencyRegistry : DependencyRegistry {

    // -- client --

    fun walletClient(connections: List<Connection>, configuration: BeaconConfiguration): BeaconWalletClient
}

internal fun DependencyRegistry.extend(): ExtendedDependencyRegistry =
    if (this is ExtendedDependencyRegistry) this
    else findExtended<WalletClientDependencyRegistry>() ?: WalletClientDependencyRegistry(this).also { addExtended(it) }