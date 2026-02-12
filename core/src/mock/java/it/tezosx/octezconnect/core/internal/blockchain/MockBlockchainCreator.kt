package it.tezosx.octezconnect.core.internal.blockchain

import androidx.annotation.RestrictTo
import it.tezosx.octezconnect.core.blockchain.Blockchain
import it.tezosx.octezconnect.core.data.Account
import it.tezosx.octezconnect.core.data.Connection
import it.tezosx.octezconnect.core.data.MockPermission
import it.tezosx.octezconnect.core.data.Permission
import it.tezosx.octezconnect.core.internal.blockchain.creator.DataBlockchainCreator
import it.tezosx.octezconnect.core.internal.blockchain.creator.V1BeaconMessageBlockchainCreator
import it.tezosx.octezconnect.core.internal.blockchain.creator.V2BeaconMessageBlockchainCreator
import it.tezosx.octezconnect.core.internal.blockchain.creator.V3BeaconMessageBlockchainCreator
import it.tezosx.octezconnect.core.internal.blockchain.message.BlockchainMockRequest
import it.tezosx.octezconnect.core.internal.blockchain.message.BlockchainMockResponse
import it.tezosx.octezconnect.core.internal.blockchain.message.PermissionMockRequest
import it.tezosx.octezconnect.core.internal.blockchain.message.PermissionMockResponse
import it.tezosx.octezconnect.core.internal.message.v1.V1BeaconMessage
import it.tezosx.octezconnect.core.internal.message.v2.V2BeaconMessage
import it.tezosx.octezconnect.core.internal.message.v3.V3BeaconMessage
import it.tezosx.octezconnect.core.internal.utils.currentTimestamp
import it.tezosx.octezconnect.core.internal.utils.failWithUnsupportedMessage
import it.tezosx.octezconnect.core.message.BeaconMessage
import it.tezosx.octezconnect.core.message.PermissionBeaconRequest
import it.tezosx.octezconnect.core.message.PermissionBeaconResponse

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class MockBlockchainCreator : Blockchain.Creator {
    override val data: DataBlockchainCreator = object : DataBlockchainCreator {
        override suspend fun extractIncomingPermission(
            request: PermissionBeaconRequest,
            response: PermissionBeaconResponse,
            origin: Connection.Id,
        ): Result<List<Permission>> = runCatching {
            listOf(
                MockPermission(
                    response.blockchainIdentifier,
                    "accountId",
                    origin.id,
                    currentTimestamp(),
                    if (response is PermissionMockResponse) response.rest else emptyMap(),
                )
            )
        }

        override suspend fun extractOutgoingPermission(
            request: PermissionBeaconRequest,
            response: PermissionBeaconResponse,
        ): Result<List<Permission>> = runCatching {
            listOf(
                MockPermission(
                    response.blockchainIdentifier,
                    "accountId",
                    request.senderId,
                    currentTimestamp(),
                    if (response is PermissionMockResponse) response.rest else emptyMap(),
                )
            )
        }

        override fun extractAccounts(response: PermissionBeaconResponse): Result<List<Account>> = runCatching {
            listOf(Account("accountId", "address"))
        }
    }

    override val v1: V1BeaconMessageBlockchainCreator = object : V1BeaconMessageBlockchainCreator {
        override fun from(senderId: String, message: BeaconMessage): Result<V1BeaconMessage> =
            runCatching {
                when (message) {
                    is PermissionMockRequest -> message.toV1()
                    is BlockchainMockRequest -> message.toV1()
                    is PermissionMockResponse -> message.toV1(senderId)
                    is BlockchainMockResponse -> message.toV1(senderId)
                    else -> failWithUnsupportedMessage(message, message.version)
                }
            }
    }

    override val v2: V2BeaconMessageBlockchainCreator = object : V2BeaconMessageBlockchainCreator {
        override fun from(senderId: String, message: BeaconMessage): Result<V2BeaconMessage> =
            runCatching {
                when (message) {
                    is PermissionMockRequest -> message.toV2()
                    is BlockchainMockRequest -> message.toV2()
                    is PermissionMockResponse -> message.toV2(senderId)
                    is BlockchainMockResponse -> message.toV2(senderId)
                    else -> failWithUnsupportedMessage(message, message.version)
                }
            }
    }

    override val v3: V3BeaconMessageBlockchainCreator = object : V3BeaconMessageBlockchainCreator {
        override fun contentFrom(message: BeaconMessage): Result<V3BeaconMessage.Content> =
            runCatching {
                when (message) {
                    is PermissionMockRequest -> message.toV3()
                    is BlockchainMockRequest -> message.toV3()
                    is PermissionMockResponse -> message.toV3()
                    is BlockchainMockResponse -> message.toV3()
                    else -> failWithUnsupportedMessage(message, message.version)
                }
            }
    }
}