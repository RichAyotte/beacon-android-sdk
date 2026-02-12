package it.tezosx.octezconnect.client.dapp.internal.controller.account.store

import androidx.annotation.RestrictTo
import it.tezosx.octezconnect.client.dapp.data.PairedAccount
import it.tezosx.octezconnect.core.data.Peer

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public data class AccountControllerStoreState(
    val activeAccount: PairedAccount?,
    val activePeer: Peer?,
)
