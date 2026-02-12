package it.tezosx.octezconnect.core.internal.blockchain.message

import androidx.annotation.RestrictTo
import it.tezosx.octezconnect.core.data.AppMetadata
import it.tezosx.octezconnect.core.data.BeaconError
import it.tezosx.octezconnect.core.data.Connection
import it.tezosx.octezconnect.core.data.MockAppMetadata
import it.tezosx.octezconnect.core.internal.message.v1.V1BeaconMessage
import it.tezosx.octezconnect.core.internal.message.v2.V2BeaconMessage
import it.tezosx.octezconnect.core.internal.message.v3.*
import it.tezosx.octezconnect.core.message.BlockchainBeaconRequest
import it.tezosx.octezconnect.core.message.BlockchainBeaconResponse
import it.tezosx.octezconnect.core.message.PermissionBeaconRequest
import it.tezosx.octezconnect.core.message.PermissionBeaconResponse
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

internal enum class MockBeaconMessageType(val value: String) {
    Request("request"),
    Response("response");

    companion object {
        fun from(string: String): MockBeaconMessageType? = values().firstOrNull { string.contains(it.value) }
    }
}

@Serializable
public data class PermissionMockRequest(
    val type: String,
    override val id: String,
    override val version: String,
    override val blockchainIdentifier: String,
    override val senderId: String,
    override val origin: Connection.Id,
    override val destination: Connection.Id?,
    override val appMetadata: MockAppMetadata,
    val rest: Map<String, JsonElement> = emptyMap(),
) : PermissionBeaconRequest() {
    public fun toV1(): V1BeaconMessage =
        V1MockPermissionBeaconRequest(
            type,
            version,
            id,
            senderId,
            appMetadata,
            rest,
        )

    public fun toV2(): V2BeaconMessage =
        V2MockPermissionBeaconRequest(
            type,
            version,
            id,
            senderId,
            appMetadata,
            rest,
        )

    public fun toV3(): V3BeaconMessage.Content =
        PermissionV3BeaconRequestContent(
            blockchainIdentifier,
            V3MockPermissionBeaconRequestData(
                appMetadata,
                rest,
            ),
        )
}

@Serializable
public data class BlockchainMockRequest(
    val type: String,
    override val id: String,
    override val version: String,
    override val blockchainIdentifier: String,
    override val senderId: String,
    override val appMetadata: @Contextual AppMetadata?,
    override val origin: Connection.Id,
    override val destination: Connection.Id?,
    override val accountId: String?,
    val rest: Map<String, JsonElement> = emptyMap(),
) : BlockchainBeaconRequest() {
    public fun toV1(): V1BeaconMessage =
        V1MockBlockchainBeaconMessage(
            type,
            version,
            id,
            senderId,
            rest,
            MockBeaconMessageType.Request,
        )

    public fun toV2(): V2BeaconMessage =
        V2MockBlockchainBeaconMessage(
            type,
            version,
            id,
            senderId,
            rest,
            MockBeaconMessageType.Request,
        )

    public fun toV3(): V3BeaconMessage.Content =
        BlockchainV3BeaconRequestContent(
            blockchainIdentifier,
            accountId ?: "",
            V3MockBlockchainBeaconRequestData(rest),
        )
}

@Serializable
public data class PermissionMockResponse(
    val type: String,
    override val id: String,
    override val version: String,
    override val destination: Connection.Id,
    override val blockchainIdentifier: String,
    val rest: Map<String, JsonElement> = emptyMap(),
) : PermissionBeaconResponse() {
    public fun toV1(senderId: String): V1BeaconMessage =
        V1MockPermissionBeaconResponse(
            type,
            version,
            id,
            senderId,
            rest,
        )

    public fun toV2(senderId: String): V2BeaconMessage =
        V2MockPermissionBeaconResponse(
            type,
            version,
            id,
            senderId,
            rest,
        )

    public fun toV3(): V3BeaconMessage.Content =
        PermissionV3BeaconResponseContent(
            blockchainIdentifier,
            V3MockPermissionBeaconResponseData(rest),
        )
}

@Serializable
public data class BlockchainMockResponse(
    val type: String,
    override val id: String,
    override val version: String,
    override val destination: Connection.Id,
    override val blockchainIdentifier: String,
    val rest: Map<String, JsonElement> = emptyMap(),
) : BlockchainBeaconResponse() {
    public fun toV1(senderId: String): V1BeaconMessage =
        V1MockBlockchainBeaconMessage(
            type,
            version,
            id,
            senderId,
            rest,
            MockBeaconMessageType.Response,
        )

    public fun toV2(senderId: String): V2BeaconMessage =
        V2MockBlockchainBeaconMessage(
            type,
            version,
            id,
            senderId,
            rest,
            MockBeaconMessageType.Response,
        )

    public fun toV3(): V3BeaconMessage.Content =
        BlockchainV3BeaconResponseContent(
            blockchainIdentifier,
            V3MockBlockchainBeaconResponseData(rest),
        )
}

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
@Serializable
public sealed class MockError : BeaconError()