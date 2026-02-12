package it.tezosx.octezconnect.blockchain.tezos.internal.serializer

import it.tezosx.octezconnect.blockchain.tezos.internal.message.v2.V2TezosMessage
import it.tezosx.octezconnect.core.internal.blockchain.serializer.V2BeaconMessageBlockchainSerializer
import it.tezosx.octezconnect.core.internal.message.v2.V2BeaconMessage
import it.tezosx.octezconnect.core.internal.utils.SuperClassSerializer
import kotlinx.serialization.KSerializer

internal class V2BeaconMessageTezosSerializer : V2BeaconMessageBlockchainSerializer {
    override val message: KSerializer<V2BeaconMessage>
        get() = SuperClassSerializer(V2TezosMessage.serializer())
}