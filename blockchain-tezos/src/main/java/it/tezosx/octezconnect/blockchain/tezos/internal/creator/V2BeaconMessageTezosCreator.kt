package it.tezosx.octezconnect.blockchain.tezos.internal.creator

import it.tezosx.octezconnect.blockchain.tezos.internal.message.v2.V2TezosMessage
import it.tezosx.octezconnect.core.internal.blockchain.creator.V2BeaconMessageBlockchainCreator
import it.tezosx.octezconnect.core.internal.message.v2.V2BeaconMessage
import it.tezosx.octezconnect.core.message.BeaconMessage

internal class V2BeaconMessageTezosCreator : V2BeaconMessageBlockchainCreator {
    override fun from(senderId: String, message: BeaconMessage): Result<V2BeaconMessage> = runCatching { V2TezosMessage.from(senderId, message) }
}