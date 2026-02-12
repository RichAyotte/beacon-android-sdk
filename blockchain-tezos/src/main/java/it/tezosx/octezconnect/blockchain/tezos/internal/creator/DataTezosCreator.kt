package it.tezosx.octezconnect.blockchain.tezos.internal.creator

import it.tezosx.octezconnect.blockchain.tezos.data.TezosAppMetadata
import it.tezosx.octezconnect.blockchain.tezos.data.TezosPermission
import it.tezosx.octezconnect.blockchain.tezos.internal.utils.failWithUnknownMessage
import it.tezosx.octezconnect.blockchain.tezos.message.request.PermissionTezosRequest
import it.tezosx.octezconnect.blockchain.tezos.message.response.PermissionTezosResponse
import it.tezosx.octezconnect.core.data.Account
import it.tezosx.octezconnect.core.data.Connection
import it.tezosx.octezconnect.core.data.Permission
import it.tezosx.octezconnect.core.internal.blockchain.creator.DataBlockchainCreator
import it.tezosx.octezconnect.core.internal.storage.StorageManager
import it.tezosx.octezconnect.core.internal.utils.IdentifierCreator
import it.tezosx.octezconnect.core.internal.utils.asHexString
import it.tezosx.octezconnect.core.internal.utils.currentTimestamp
import it.tezosx.octezconnect.core.internal.utils.failWithIllegalState
import it.tezosx.octezconnect.core.message.PermissionBeaconRequest
import it.tezosx.octezconnect.core.message.PermissionBeaconResponse
import it.tezosx.octezconnect.core.storage.findAppMetadata

internal class DataTezosCreator(
    private val storageManager: StorageManager,
    private val identifierCreator: IdentifierCreator,
) : DataBlockchainCreator {
    override suspend fun extractIncomingPermission(request: PermissionBeaconRequest, response: PermissionBeaconResponse, origin: Connection.Id): Result<List<Permission>> =
        runCatching {
            if (request !is PermissionTezosRequest) failWithUnknownMessage(request)
            if (response !is PermissionTezosResponse) failWithUnknownMessage(response)

            val peer = storageManager.findPeer { it.publicKey == origin.id } ?: failWithAppMetadataNotFound()
            val senderId = identifierCreator.senderId(origin.id.asHexString().toByteArray()).getOrThrow()
            val appMetadata = TezosAppMetadata(senderId, peer.name, peer.icon)

            listOf(
                TezosPermission(
                    response.account.accountId,
                    senderId,
                    connectedAt = currentTimestamp(),
                    response.account.address,
                    response.account.publicKey,
                    response.account.network,
                    appMetadata,
                    response.scopes,
                ),
            )
        }

    override suspend fun extractOutgoingPermission(request: PermissionBeaconRequest, response: PermissionBeaconResponse): Result<List<Permission>> =
        runCatching {
            if (request !is PermissionTezosRequest) failWithUnknownMessage(request)
            if (response !is PermissionTezosResponse) failWithUnknownMessage(response)

            val appMetadata = storageManager.findAppMetadata<TezosAppMetadata> { it.senderId == request.senderId } ?: failWithAppMetadataNotFound()
            val senderId = identifierCreator.senderId(request.origin.id.asHexString().toByteArray()).getOrThrow()

            listOf(
                TezosPermission(
                    response.account.accountId,
                    senderId,
                    connectedAt = currentTimestamp(),
                    response.account.address,
                    response.account.publicKey,
                    response.account.network,
                    appMetadata,
                    response.scopes,
                ),
            )
        }

    override fun extractAccounts(response: PermissionBeaconResponse): Result<List<Account>> =
        runCatching {
            if (response !is PermissionTezosResponse) failWithUnknownMessage(response)

            listOf(Account(response.account.accountId, response.account.address))
        }

    private fun failWithAppMetadataNotFound(): Nothing = failWithIllegalState("Permission could not be extracted, matching appMetadata not found.")
}