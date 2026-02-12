package it.tezosx.octezconnect.blockchain.substrate.internal.creator

import it.tezosx.octezconnect.blockchain.substrate.data.SubstrateAppMetadata
import it.tezosx.octezconnect.blockchain.substrate.data.SubstratePermission
import it.tezosx.octezconnect.blockchain.substrate.internal.utils.failWithUnknownMessage
import it.tezosx.octezconnect.blockchain.substrate.message.request.PermissionSubstrateRequest
import it.tezosx.octezconnect.blockchain.substrate.message.response.PermissionSubstrateResponse
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

internal class DataSubstrateCreator(
    private val storageManager: StorageManager,
    private val identifierCreator: IdentifierCreator,
) : DataBlockchainCreator {

    override suspend fun extractIncomingPermission(request: PermissionBeaconRequest, response: PermissionBeaconResponse, origin: Connection.Id): Result<List<Permission>> =
        runCatching {
            if (request !is PermissionSubstrateRequest) failWithUnknownMessage(request)
            if (response !is PermissionSubstrateResponse) failWithUnknownMessage(response)

            response.accounts.map { account ->
                val accountId = identifierCreator.accountId(account.address, account.network).getOrThrow()

                SubstratePermission(
                    accountId,
                    identifierCreator.senderId(origin.id.asHexString().toByteArray()).getOrThrow(),
                    connectedAt = currentTimestamp(),
                    response.appMetadata,
                    response.scopes,
                    account,
                )
            }
        }

    override suspend fun extractOutgoingPermission(request: PermissionBeaconRequest, response: PermissionBeaconResponse): Result<List<Permission>> =
        runCatching {
            if (request !is PermissionSubstrateRequest) failWithUnknownMessage(request)
            if (response !is PermissionSubstrateResponse) failWithUnknownMessage(response)

            response.accounts.map { account ->
                val accountId = identifierCreator.accountId(account.address, account.network).getOrThrow()
                val appMetadata = storageManager.findAppMetadata<SubstrateAppMetadata> { it.senderId == request.senderId } ?: failWithAppMetadataNotFound()

                SubstratePermission(
                    accountId,
                    identifierCreator.senderId(request.origin.id.asHexString().toByteArray()).getOrThrow(),
                    connectedAt = currentTimestamp(),
                    appMetadata,
                    response.scopes,
                    account,
                )
            }
        }

    override fun extractAccounts(response: PermissionBeaconResponse): Result<List<Account>> =
        runCatching {
            if (response !is PermissionSubstrateResponse) failWithUnknownMessage(response)

            response.accounts.map {
                Account(identifierCreator.accountId(it.address, it.network).getOrThrow(), it.address)
            }
        }

    private fun failWithAppMetadataNotFound(): Nothing = failWithIllegalState("Permission could not be extracted, matching appMetadata not found.")
}