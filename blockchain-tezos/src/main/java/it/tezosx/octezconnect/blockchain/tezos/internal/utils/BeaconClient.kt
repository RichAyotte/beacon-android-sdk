package it.tezosx.octezconnect.blockchain.tezos.internal.utils

import it.tezosx.octezconnect.blockchain.tezos.data.TezosNetwork
import it.tezosx.octezconnect.blockchain.tezos.data.TezosPermission
import it.tezosx.octezconnect.core.client.BeaconClient
import it.tezosx.octezconnect.core.internal.utils.failWithAccountNetworkNotFound

internal suspend fun BeaconClient<*>.getNetworkFor(accountId: String): TezosNetwork {
    val permission = getPermissionsFor(accountId) as? TezosPermission ?: failWithAccountNetworkNotFound(accountId)
    return permission.network
}