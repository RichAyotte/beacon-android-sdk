package it.tezosx.octezconnect.core.data

import kotlinx.serialization.Serializable

@Serializable
public data class Account(
    public val accountId: String,
    public val address: String,
)