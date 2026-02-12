package it.tezosx.octezconnect.blockchain.substrate.internal.creator

import it.tezosx.octezconnect.core.internal.blockchain.creator.V3BeaconMessageBlockchainCreator
import it.tezosx.octezconnect.core.internal.message.v3.*
import it.tezosx.octezconnect.core.internal.utils.failWithIllegalState
import it.tezosx.octezconnect.core.message.BeaconMessage
import it.tezosx.octezconnect.blockchain.substrate.internal.message.v3.BlockchainV3SubstrateRequest
import it.tezosx.octezconnect.blockchain.substrate.internal.message.v3.BlockchainV3SubstrateResponse
import it.tezosx.octezconnect.blockchain.substrate.internal.message.v3.PermissionV3SubstrateRequest
import it.tezosx.octezconnect.blockchain.substrate.internal.message.v3.PermissionV3SubstrateResponse
import it.tezosx.octezconnect.blockchain.substrate.internal.utils.failWithUnknownMessage
import it.tezosx.octezconnect.blockchain.substrate.message.request.BlockchainSubstrateRequest
import it.tezosx.octezconnect.blockchain.substrate.message.request.PermissionSubstrateRequest
import it.tezosx.octezconnect.blockchain.substrate.message.response.BlockchainSubstrateResponse
import it.tezosx.octezconnect.blockchain.substrate.message.response.PermissionSubstrateResponse

internal class V3BeaconMessageSubstrateCreator : V3BeaconMessageBlockchainCreator {
    override fun contentFrom(message: BeaconMessage): Result<V3BeaconMessage.Content> =
        runCatching {
            with(message) {
                when (this) {
                    is PermissionSubstrateRequest -> PermissionV3BeaconRequestContent(
                        blockchainIdentifier,
                        PermissionV3SubstrateRequest.from(this),
                    )
                    is BlockchainSubstrateRequest -> BlockchainV3BeaconRequestContent(
                        blockchainIdentifier,
                        accountId ?: failWithMissingAccountId(),
                        BlockchainV3SubstrateRequest.from(this),
                    )
                    is PermissionSubstrateResponse -> PermissionV3BeaconResponseContent(
                        blockchainIdentifier,
                        PermissionV3SubstrateResponse.from(this),
                    )
                    is BlockchainSubstrateResponse -> BlockchainV3BeaconResponseContent(
                        blockchainIdentifier,
                        BlockchainV3SubstrateResponse.from(this),
                    )
                    else -> failWithUnknownMessage(message)
                }
            }
        }

    private fun failWithMissingAccountId(): Nothing = failWithIllegalState("Value `accountId` is missing in Substrate v3 request.")
}