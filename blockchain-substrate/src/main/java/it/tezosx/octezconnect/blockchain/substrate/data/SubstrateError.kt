package it.tezosx.octezconnect.blockchain.substrate.data

import it.tezosx.octezconnect.core.data.BeaconError
import kotlinx.serialization.Serializable

/**
 * Types of Substrate errors supported in Beacon
 */
@Serializable
public sealed class SubstrateError : BeaconError() {

    public companion object {}
}