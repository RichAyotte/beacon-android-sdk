package it.tezosx.octezconnect.core.internal.storage.sharedpreferences

import it.tezosx.octezconnect.core.data.AppMetadata
import it.tezosx.octezconnect.core.data.Maybe
import it.tezosx.octezconnect.core.data.Peer
import it.tezosx.octezconnect.core.data.Permission
import it.tezosx.octezconnect.core.exception.BlockchainNotFoundException
import it.tezosx.octezconnect.core.internal.BeaconConfiguration
import it.tezosx.octezconnect.core.storage.Storage

public suspend fun Storage.getPeers(beaconConfiguration: BeaconConfiguration): List<Peer> = getMaybePeers().values(beaconConfiguration)
public suspend fun Storage.getAppMetadata(beaconConfiguration: BeaconConfiguration): List<AppMetadata> = getMaybeAppMetadata().values(beaconConfiguration)
public suspend fun Storage.getPermissions(beaconConfiguration: BeaconConfiguration): List<Permission> = getMaybePermissions().values(beaconConfiguration)

private fun <T> List<Maybe<T>>.values(beaconConfiguration: BeaconConfiguration): List<T> =
    mapNotNull {
        when (it) {
            is Maybe.Some -> it.value
            is Maybe.None -> null
            is Maybe.NoneWithError -> it.discardOrThrow(beaconConfiguration)
        }
    }

private fun <T> Maybe.NoneWithError<T>.discardOrThrow(beaconConfiguration: BeaconConfiguration): T? = with (beaconConfiguration) {
    when {
        exception is BlockchainNotFoundException && ignoreUnsupportedBlockchains -> null
        else -> throw exception
    }
}