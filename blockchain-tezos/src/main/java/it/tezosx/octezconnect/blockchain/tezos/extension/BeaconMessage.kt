package it.tezosx.octezconnect.blockchain.tezos.extension

import it.tezosx.octezconnect.blockchain.tezos.data.TezosError
import it.tezosx.octezconnect.core.message.BlockchainBeaconRequest
import it.tezosx.octezconnect.core.message.ErrorBeaconResponse
import it.tezosx.octezconnect.core.message.PermissionBeaconRequest

/**
 * Creates a new instance of [ErrorBeaconResponse] from the [request]
 * with the specified [errorType].
 *
 * The response will have an id matching the one of the [request].
 */
public fun ErrorBeaconResponse.Companion.from(request: PermissionBeaconRequest, errorType: TezosError, description: String? = null): ErrorBeaconResponse =
    ErrorBeaconResponse(request.id, request.version, request.origin, errorType, description)

/**
 * Creates a new instance of [ErrorBeaconResponse] from the [request]
 * with the specified [errorType].
 *
 * The response will have an id matching the one of the [request].
 */
public fun ErrorBeaconResponse.Companion.from(request: BlockchainBeaconRequest, errorType: TezosError, description: String? = null): ErrorBeaconResponse =
    ErrorBeaconResponse(request.id, request.version, request.origin, errorType, description)
