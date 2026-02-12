package it.tezosx.octezconnect.blockchain.tezos.internal.creator

import it.tezosx.octezconnect.blockchain.tezos.internal.message.v1.V1TezosMessage
import it.tezosx.octezconnect.core.internal.blockchain.creator.V1BeaconMessageBlockchainCreator
import it.tezosx.octezconnect.core.internal.message.v1.V1BeaconMessage
import it.tezosx.octezconnect.core.message.BeaconMessage

internal class V1BeaconMessageTezosCreator : V1BeaconMessageBlockchainCreator {
    override fun from(senderId: String, message: BeaconMessage): Result<V1BeaconMessage> = runCatching { V1TezosMessage.from(senderId, message) }
}