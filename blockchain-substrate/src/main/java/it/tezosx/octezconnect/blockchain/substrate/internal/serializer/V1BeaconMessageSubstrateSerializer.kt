package it.tezosx.octezconnect.blockchain.substrate.internal.serializer

import it.tezosx.octezconnect.core.internal.blockchain.serializer.V1BeaconMessageBlockchainSerializer
import it.tezosx.octezconnect.core.internal.message.v1.V1BeaconMessage
import it.tezosx.octezconnect.core.internal.utils.failWithUnsupportedMessageVersion
import it.tezosx.octezconnect.blockchain.substrate.Substrate
import kotlinx.serialization.KSerializer

internal class V1BeaconMessageSubstrateSerializer : V1BeaconMessageBlockchainSerializer {
    @get:Throws(IllegalArgumentException::class)
    override val message: KSerializer<V1BeaconMessage>
        get() = failWithUnsupportedMessageVersion("1", Substrate.IDENTIFIER)
}