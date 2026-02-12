package it.tezosx.octezconnect.blockchain.substrate.internal.creator

import it.tezosx.octezconnect.core.blockchain.Blockchain
import it.tezosx.octezconnect.core.internal.blockchain.creator.DataBlockchainCreator
import it.tezosx.octezconnect.core.internal.blockchain.creator.V1BeaconMessageBlockchainCreator
import it.tezosx.octezconnect.core.internal.blockchain.creator.V2BeaconMessageBlockchainCreator
import it.tezosx.octezconnect.core.internal.blockchain.creator.V3BeaconMessageBlockchainCreator

internal class SubstrateCreator(
    override val data: DataBlockchainCreator,
    override val v1: V1BeaconMessageBlockchainCreator,
    override val v2: V2BeaconMessageBlockchainCreator,
    override val v3: V3BeaconMessageBlockchainCreator,
) : Blockchain.Creator