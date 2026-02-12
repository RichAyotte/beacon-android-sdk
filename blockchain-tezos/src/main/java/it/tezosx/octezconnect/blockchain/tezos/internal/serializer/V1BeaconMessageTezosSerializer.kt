package it.tezosx.octezconnect.blockchain.tezos.internal.serializer

import it.tezosx.octezconnect.blockchain.tezos.internal.message.v1.V1TezosMessage
import it.tezosx.octezconnect.core.internal.blockchain.serializer.V1BeaconMessageBlockchainSerializer
import it.tezosx.octezconnect.core.internal.message.v1.V1BeaconMessage
import it.tezosx.octezconnect.core.internal.utils.SuperClassSerializer
import kotlinx.serialization.KSerializer

internal class V1BeaconMessageTezosSerializer : V1BeaconMessageBlockchainSerializer {
    override val message: KSerializer<V1BeaconMessage>
        get() = SuperClassSerializer(V1TezosMessage.serializer())
}