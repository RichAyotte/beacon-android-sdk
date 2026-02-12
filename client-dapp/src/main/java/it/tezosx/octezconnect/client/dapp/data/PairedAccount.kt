package it.tezosx.octezconnect.client.dapp.data

import it.tezosx.octezconnect.core.data.Account
import kotlinx.serialization.Serializable

@Serializable
public data class PairedAccount(
    public val account: Account,
    public val peerId: String,
)
