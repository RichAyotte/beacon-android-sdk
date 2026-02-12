package it.tezosx.octezconnect.blockchain.substrate.internal.creator

import it.tezosx.octezconnect.core.internal.blockchain.creator.V1BeaconMessageBlockchainCreator
import it.tezosx.octezconnect.core.internal.message.v1.V1BeaconMessage
import it.tezosx.octezconnect.core.internal.utils.failWithUnsupportedMessageVersion
import it.tezosx.octezconnect.core.message.BeaconMessage
import it.tezosx.octezconnect.blockchain.substrate.Substrate

internal class V1BeaconMessageSubstrateCreator : V1BeaconMessageBlockchainCreator {
    @Throws(IllegalStateException::class)
    override fun from(senderId: String, message: BeaconMessage): Result<V1BeaconMessage> =
        runCatching { failWithUnsupportedMessageVersion("1", Substrate.IDENTIFIER) }
}