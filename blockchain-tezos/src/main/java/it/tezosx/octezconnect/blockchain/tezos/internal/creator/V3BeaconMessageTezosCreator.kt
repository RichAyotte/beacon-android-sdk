package it.tezosx.octezconnect.blockchain.tezos.internal.creator

import it.tezosx.octezconnect.blockchain.tezos.internal.message.v3.*
import it.tezosx.octezconnect.blockchain.tezos.internal.message.v3.PermissionV3TezosRequest
import it.tezosx.octezconnect.blockchain.tezos.internal.utils.failWithUnknownMessage
import it.tezosx.octezconnect.blockchain.tezos.message.request.*
import it.tezosx.octezconnect.blockchain.tezos.message.response.*
import it.tezosx.octezconnect.core.internal.blockchain.creator.V3BeaconMessageBlockchainCreator
import it.tezosx.octezconnect.core.internal.message.v3.*
import it.tezosx.octezconnect.core.internal.utils.failWithIllegalState
import it.tezosx.octezconnect.core.message.BeaconMessage

internal class V3BeaconMessageTezosCreator : V3BeaconMessageBlockchainCreator {
    override fun contentFrom(message: BeaconMessage): Result<V3BeaconMessage.Content> =
        runCatching {
            with(message) {
                when (this) {
                    is PermissionTezosRequest -> PermissionV3BeaconRequestContent(
                        blockchainIdentifier,
                        PermissionV3TezosRequest.from(this),
                    )
                    is BlockchainTezosRequest -> BlockchainV3BeaconRequestContent(
                        blockchainIdentifier,
                        accountId ?: failWithMissingAccountId(),
                        BlockchainV3TezosRequest.from(this),
                    )
                    is PermissionTezosResponse -> PermissionV3BeaconResponseContent(
                        blockchainIdentifier,
                        PermissionV3TezosResponse.from(this),
                    )
                    is BlockchainTezosResponse -> BlockchainV3BeaconResponseContent(
                        blockchainIdentifier,
                        BlockchainV3TezosResponse.from(this),
                    )
                    else -> failWithUnknownMessage(message)
                }
            }
        }

    private fun failWithMissingAccountId(): Nothing = failWithIllegalState("Value `accountId` is missing in Tezos v3 request.")
}