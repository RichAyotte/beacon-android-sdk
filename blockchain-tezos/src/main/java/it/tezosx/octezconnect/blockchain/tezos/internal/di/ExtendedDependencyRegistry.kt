package it.tezosx.octezconnect.blockchain.tezos.internal.di

import it.tezosx.octezconnect.blockchain.tezos.Tezos
import it.tezosx.octezconnect.blockchain.tezos.internal.creator.TezosCreator
import it.tezosx.octezconnect.blockchain.tezos.internal.serializer.TezosSerializer
import it.tezosx.octezconnect.blockchain.tezos.internal.wallet.TezosWallet
import it.tezosx.octezconnect.core.internal.di.DependencyRegistry
import it.tezosx.octezconnect.core.internal.di.findExtended

internal interface ExtendedDependencyRegistry : DependencyRegistry {

    // -- blockchain --

    val tezos: Tezos

    // -- wallet --

    val tezosWallet: TezosWallet

    // -- creator --

    val tezosCreator: TezosCreator

    // -- serializer --

    val tezosSerializer: TezosSerializer
}

internal fun DependencyRegistry.extend(): ExtendedDependencyRegistry =
    if (this is ExtendedDependencyRegistry) this
    else findExtended<TezosDependencyRegistry>() ?: TezosDependencyRegistry(this).also { addExtended(it) }