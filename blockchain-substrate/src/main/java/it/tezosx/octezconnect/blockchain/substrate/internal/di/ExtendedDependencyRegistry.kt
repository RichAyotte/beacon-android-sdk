package it.tezosx.octezconnect.blockchain.substrate.internal.di

import it.tezosx.octezconnect.blockchain.substrate.Substrate
import it.tezosx.octezconnect.blockchain.substrate.internal.creator.SubstrateCreator
import it.tezosx.octezconnect.blockchain.substrate.internal.serializer.SubstrateSerializer
import it.tezosx.octezconnect.core.internal.di.DependencyRegistry
import it.tezosx.octezconnect.core.internal.di.findExtended

internal interface ExtendedDependencyRegistry : DependencyRegistry {

    // -- blockchain --

    val substrate: Substrate

    // -- creator --

    val substrateCreator: SubstrateCreator

    // -- serializer --

    val substrateSerializer: SubstrateSerializer
}

internal fun DependencyRegistry.extend(): ExtendedDependencyRegistry =
    if (this is ExtendedDependencyRegistry) this
    else findExtended<SubstrateDependencyRegistry>() ?: SubstrateDependencyRegistry(this).also { addExtended(it) }