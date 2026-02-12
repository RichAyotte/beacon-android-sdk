package it.tezosx.octezconnect.blockchain.substrate.internal.creator

import it.tezosx.octezconnect.core.internal.blockchain.creator.V2BeaconMessageBlockchainCreator
import it.tezosx.octezconnect.core.internal.message.v2.V2BeaconMessage
import it.tezosx.octezconnect.core.internal.utils.failWithUnsupportedMessageVersion
import it.tezosx.octezconnect.core.message.BeaconMessage
import it.tezosx.octezconnect.blockchain.substrate.Substrate

internal class V2BeaconMessageSubstrateCreator : V2BeaconMessageBlockchainCreator {
    @Throws(IllegalStateException::class)
    override fun from(senderId: String, message: BeaconMessage): Result<V2BeaconMessage> =
        runCatching { failWithUnsupportedMessageVersion("2", Substrate.IDENTIFIER) }
}