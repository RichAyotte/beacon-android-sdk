package it.tezosx.octezconnect.blockchain.substrate.internal.utils

import it.tezosx.octezconnect.blockchain.substrate.data.SubstrateNetwork
import it.tezosx.octezconnect.blockchain.substrate.data.SubstratePermission
import it.tezosx.octezconnect.core.client.BeaconClient
import it.tezosx.octezconnect.core.internal.utils.failWithAccountNetworkNotFound

internal suspend fun BeaconClient<*>.getNetworkFor(accountId: String): SubstrateNetwork {
    val permission = getPermissionsFor(accountId) as? SubstratePermission
    return permission?.account?.network ?: failWithAccountNetworkNotFound(accountId)
}