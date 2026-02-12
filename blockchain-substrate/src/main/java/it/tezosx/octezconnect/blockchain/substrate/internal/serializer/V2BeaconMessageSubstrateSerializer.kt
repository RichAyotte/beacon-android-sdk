package it.tezosx.octezconnect.blockchain.substrate.internal.serializer

import it.tezosx.octezconnect.core.internal.blockchain.serializer.V2BeaconMessageBlockchainSerializer
import it.tezosx.octezconnect.core.internal.message.v2.V2BeaconMessage
import it.tezosx.octezconnect.core.internal.utils.failWithUnsupportedMessageVersion
import it.tezosx.octezconnect.blockchain.substrate.Substrate
import kotlinx.serialization.KSerializer

internal class V2BeaconMessageSubstrateSerializer : V2BeaconMessageBlockchainSerializer {
    @get:Throws(IllegalArgumentException::class)
    override val message: KSerializer<V2BeaconMessage>
        get() = failWithUnsupportedMessageVersion("2", Substrate.IDENTIFIER)
}