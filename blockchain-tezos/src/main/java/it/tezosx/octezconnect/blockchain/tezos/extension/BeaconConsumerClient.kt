package it.tezosx.octezconnect.blockchain.tezos.extension

import it.tezosx.octezconnect.blockchain.tezos.data.TezosAccount
import it.tezosx.octezconnect.blockchain.tezos.data.TezosAppMetadata
import it.tezosx.octezconnect.blockchain.tezos.data.TezosNotification
import it.tezosx.octezconnect.blockchain.tezos.data.TezosPermission
import it.tezosx.octezconnect.blockchain.tezos.data.TezosThreshold
import it.tezosx.octezconnect.blockchain.tezos.message.request.BroadcastTezosRequest
import it.tezosx.octezconnect.blockchain.tezos.message.request.OperationTezosRequest
import it.tezosx.octezconnect.blockchain.tezos.message.request.PermissionTezosRequest
import it.tezosx.octezconnect.blockchain.tezos.message.request.SignPayloadTezosRequest
import it.tezosx.octezconnect.blockchain.tezos.message.response.BroadcastTezosResponse
import it.tezosx.octezconnect.blockchain.tezos.message.response.OperationTezosResponse
import it.tezosx.octezconnect.blockchain.tezos.message.response.PermissionTezosResponse
import it.tezosx.octezconnect.blockchain.tezos.message.response.SignPayloadTezosResponse
import it.tezosx.octezconnect.core.client.BeaconClient
import it.tezosx.octezconnect.core.client.BeaconConsumer
import it.tezosx.octezconnect.core.data.SigningType

// -- response --

public suspend fun <T> T.respondToTezosPermission(
    request: PermissionTezosRequest,
    account: TezosAccount,
    scopes: List<TezosPermission.Scope> = request.scopes,
    threshold: TezosThreshold? = null,
    notification: TezosNotification? = null

) where T : BeaconConsumer, T : BeaconClient<*> {
    val response = PermissionTezosResponse.from(request, account, this, scopes, threshold, notification)
    respond(response)
}

public suspend fun <T> T.respondToTezosOperation(
    request: OperationTezosRequest,
    transactionHash: String,
) where T : BeaconConsumer, T : BeaconClient<*> {
    val response = OperationTezosResponse.from(request, transactionHash)
    respond(response)
}

public suspend fun <T> T.respondToTezosSignPayload(
    request: SignPayloadTezosRequest,
    signingType: SigningType,
    signature: String,
) where T : BeaconConsumer, T : BeaconClient<*> {
    val response = SignPayloadTezosResponse.from(request, signingType, signature)
    respond(response)
}

public suspend fun <T> T.respondToTezosBroadcast(
    request: BroadcastTezosRequest,
    transactionHash: String,
) where T : BeaconConsumer, T : BeaconClient<*> {
    val response = BroadcastTezosResponse.from(request, transactionHash)
    respond(response)
}