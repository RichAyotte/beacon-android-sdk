package it.tezosx.octezconnect.blockchain.substrate.internal.serializer

import it.tezosx.octezconnect.core.internal.blockchain.serializer.V3BeaconMessageBlockchainSerializer
import it.tezosx.octezconnect.core.internal.message.v3.BlockchainV3BeaconRequestContent
import it.tezosx.octezconnect.core.internal.message.v3.BlockchainV3BeaconResponseContent
import it.tezosx.octezconnect.core.internal.message.v3.PermissionV3BeaconRequestContent
import it.tezosx.octezconnect.core.internal.message.v3.PermissionV3BeaconResponseContent
import it.tezosx.octezconnect.core.internal.utils.SuperClassSerializer
import it.tezosx.octezconnect.blockchain.substrate.internal.message.v3.BlockchainV3SubstrateRequest
import it.tezosx.octezconnect.blockchain.substrate.internal.message.v3.BlockchainV3SubstrateResponse
import it.tezosx.octezconnect.blockchain.substrate.internal.message.v3.PermissionV3SubstrateRequest
import it.tezosx.octezconnect.blockchain.substrate.internal.message.v3.PermissionV3SubstrateResponse
import kotlinx.serialization.KSerializer

internal class V3BeaconMessageSubstrateSerializer : V3BeaconMessageBlockchainSerializer {
    override val permissionRequestData: KSerializer<PermissionV3BeaconRequestContent.BlockchainData>
        get() = SuperClassSerializer(PermissionV3SubstrateRequest.serializer())

    override val blockchainRequestData: KSerializer<BlockchainV3BeaconRequestContent.BlockchainData>
        get() = SuperClassSerializer(BlockchainV3SubstrateRequest.serializer())

    override val permissionResponseData: KSerializer<PermissionV3BeaconResponseContent.BlockchainData>
        get() = SuperClassSerializer(PermissionV3SubstrateResponse.serializer())

    override val blockchainResponseData: KSerializer<BlockchainV3BeaconResponseContent.BlockchainData>
        get() = SuperClassSerializer(BlockchainV3SubstrateResponse.serializer())
}