package it.tezosx.octezconnect.blockchain.tezos.internal.di

import it.tezosx.octezconnect.blockchain.tezos.Tezos
import it.tezosx.octezconnect.blockchain.tezos.internal.creator.*
import it.tezosx.octezconnect.blockchain.tezos.internal.serializer.*
import it.tezosx.octezconnect.blockchain.tezos.internal.wallet.TezosWallet
import it.tezosx.octezconnect.core.internal.di.DependencyRegistry
import it.tezosx.octezconnect.core.internal.utils.delegate.lazyWeak

internal class TezosDependencyRegistry(dependencyRegistry: DependencyRegistry) : ExtendedDependencyRegistry, DependencyRegistry by dependencyRegistry {

    // -- blockchain --

    override val tezos: Tezos by lazyWeak { Tezos(tezosWallet, tezosCreator, tezosSerializer) }

    // -- wallet --

    override val tezosWallet: TezosWallet by lazyWeak { TezosWallet(crypto, base58Check) }

    // -- creator --

    override val tezosCreator: TezosCreator by lazyWeak {
        TezosCreator(
            DataTezosCreator(storageManager, identifierCreator),
            V1BeaconMessageTezosCreator(),
            V2BeaconMessageTezosCreator(),
            V3BeaconMessageTezosCreator(),
        )
    }

    // -- serializer --

    override val tezosSerializer: TezosSerializer by lazyWeak {
        TezosSerializer(
            DataTezosSerializer(),
            V1BeaconMessageTezosSerializer(),
            V2BeaconMessageTezosSerializer(),
            V3BeaconMessageTezosSerializer(),
        )
    }
}