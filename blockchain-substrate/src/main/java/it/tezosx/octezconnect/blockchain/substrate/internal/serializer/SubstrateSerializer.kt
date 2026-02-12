package it.tezosx.octezconnect.blockchain.substrate.internal.serializer

import it.tezosx.octezconnect.core.blockchain.Blockchain
import it.tezosx.octezconnect.core.internal.blockchain.serializer.DataBlockchainSerializer
import it.tezosx.octezconnect.core.internal.blockchain.serializer.V1BeaconMessageBlockchainSerializer
import it.tezosx.octezconnect.core.internal.blockchain.serializer.V2BeaconMessageBlockchainSerializer
import it.tezosx.octezconnect.core.internal.blockchain.serializer.V3BeaconMessageBlockchainSerializer

internal class SubstrateSerializer(
    override val data: DataBlockchainSerializer,
    override val v1: V1BeaconMessageBlockchainSerializer,
    override val v2: V2BeaconMessageBlockchainSerializer,
    override val v3: V3BeaconMessageBlockchainSerializer,
) : Blockchain.Serializer