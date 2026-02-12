package it.tezosx.octezconnect.core.internal.blockchain.creator

import androidx.annotation.RestrictTo
import it.tezosx.octezconnect.core.data.Account
import it.tezosx.octezconnect.core.data.Connection
import it.tezosx.octezconnect.core.data.Permission
import it.tezosx.octezconnect.core.message.PermissionBeaconRequest
import it.tezosx.octezconnect.core.message.PermissionBeaconResponse

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public interface DataBlockchainCreator {
    public suspend fun extractIncomingPermission(request: PermissionBeaconRequest, response: PermissionBeaconResponse, origin: Connection.Id): Result<List<Permission>>
    public suspend fun extractOutgoingPermission(request: PermissionBeaconRequest, response: PermissionBeaconResponse): Result<List<Permission>>

    public fun extractAccounts(response: PermissionBeaconResponse): Result<List<Account>>
}