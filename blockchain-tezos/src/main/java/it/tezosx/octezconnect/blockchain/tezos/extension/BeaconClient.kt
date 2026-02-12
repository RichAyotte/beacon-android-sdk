package it.tezosx.octezconnect.blockchain.tezos.extension

import it.tezosx.octezconnect.blockchain.tezos.data.TezosAppMetadata
import it.tezosx.octezconnect.core.client.BeaconClient

// -- AppMetadata --

public fun <T> T.ownAppMetadata(): TezosAppMetadata where T : BeaconClient<*> =
    TezosAppMetadata(
        senderId = senderId,
        name = app.name,
        icon = app.icon,
    )