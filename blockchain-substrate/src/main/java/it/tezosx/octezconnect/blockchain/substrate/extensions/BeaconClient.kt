package it.tezosx.octezconnect.blockchain.substrate.extensions

import it.tezosx.octezconnect.blockchain.substrate.data.SubstrateAppMetadata
import it.tezosx.octezconnect.core.client.BeaconClient

// -- AppMetadata --

public fun <T> T.ownAppMetadata(): SubstrateAppMetadata where T : BeaconClient<*> =
    SubstrateAppMetadata(
        senderId = senderId,
        name = app.name,
        icon = app.icon,
    )