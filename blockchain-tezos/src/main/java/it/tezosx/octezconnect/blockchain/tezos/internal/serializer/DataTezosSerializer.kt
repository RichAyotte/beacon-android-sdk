package it.tezosx.octezconnect.blockchain.tezos.internal.serializer

import it.tezosx.octezconnect.blockchain.tezos.data.TezosAppMetadata
import it.tezosx.octezconnect.blockchain.tezos.data.TezosError
import it.tezosx.octezconnect.blockchain.tezos.data.TezosNetwork
import it.tezosx.octezconnect.blockchain.tezos.data.TezosPermission
import it.tezosx.octezconnect.core.data.AppMetadata
import it.tezosx.octezconnect.core.data.BeaconError
import it.tezosx.octezconnect.core.data.Network
import it.tezosx.octezconnect.core.data.Permission
import it.tezosx.octezconnect.core.internal.blockchain.serializer.DataBlockchainSerializer
import it.tezosx.octezconnect.core.internal.utils.SuperClassSerializer
import kotlinx.serialization.KSerializer

internal class DataTezosSerializer : DataBlockchainSerializer {
    override val network: KSerializer<Network>
        get() = SuperClassSerializer(TezosNetwork.serializer())

    override val permission: KSerializer<Permission>
        get() = SuperClassSerializer(TezosPermission.serializer())

    override val appMetadata: KSerializer<AppMetadata>
        get() = SuperClassSerializer(TezosAppMetadata.serializer())

    override val error: KSerializer<BeaconError>
        get() = SuperClassSerializer(TezosError.serializer())
}