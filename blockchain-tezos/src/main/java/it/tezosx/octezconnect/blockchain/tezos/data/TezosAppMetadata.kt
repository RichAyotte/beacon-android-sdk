package it.tezosx.octezconnect.blockchain.tezos.data

import it.tezosx.octezconnect.blockchain.tezos.Tezos
import it.tezosx.octezconnect.core.data.AppMetadata
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

/**
 * Metadata describing a Tezos dApp.
 *
 * @property [blockchainIdentifier] The unique name of the blockchain on which the dApp operates.
 * @property [senderId] The value that identifies the dApp.
 * @property [name] The name of the dApp.
 * @property [icon] An optional URL for the dApp icon.
 */
@OptIn(ExperimentalSerializationApi::class)
@Serializable
public data class TezosAppMetadata(
    override val senderId: String,
    override val name: String,
    override val icon: String? = null,
) : AppMetadata() {
    @EncodeDefault
    override val blockchainIdentifier: String = Tezos.IDENTIFIER

    public companion object {}
}