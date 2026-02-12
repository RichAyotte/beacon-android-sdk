package it.tezosx.octezconnect.blockchain.substrate.internal.serializer

import it.tezosx.octezconnect.blockchain.substrate.data.SubstrateAppMetadata
import it.tezosx.octezconnect.core.data.AppMetadata
import it.tezosx.octezconnect.core.data.BeaconError
import it.tezosx.octezconnect.core.data.Network
import it.tezosx.octezconnect.core.data.Permission
import it.tezosx.octezconnect.core.internal.blockchain.serializer.DataBlockchainSerializer
import it.tezosx.octezconnect.core.internal.utils.SuperClassSerializer
import it.tezosx.octezconnect.blockchain.substrate.data.SubstrateError
import it.tezosx.octezconnect.blockchain.substrate.data.SubstrateNetwork
import it.tezosx.octezconnect.blockchain.substrate.data.SubstratePermission
import kotlinx.serialization.KSerializer

internal class DataSubstrateSerializer : DataBlockchainSerializer {
    override val network: KSerializer<Network>
        get() = SuperClassSerializer(SubstrateNetwork.serializer())

    override val permission: KSerializer<Permission>
        get() = SuperClassSerializer(SubstratePermission.serializer())

    override val appMetadata: KSerializer<AppMetadata>
        get() = SuperClassSerializer(SubstrateAppMetadata.serializer())

    override val error: KSerializer<BeaconError>
        get() = SuperClassSerializer(SubstrateError.serializer())
}