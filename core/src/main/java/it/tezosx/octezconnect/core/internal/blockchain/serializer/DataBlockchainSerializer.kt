package it.tezosx.octezconnect.core.internal.blockchain.serializer

import androidx.annotation.RestrictTo
import it.tezosx.octezconnect.core.data.AppMetadata
import it.tezosx.octezconnect.core.data.BeaconError
import it.tezosx.octezconnect.core.data.Network
import it.tezosx.octezconnect.core.data.Permission
import kotlinx.serialization.KSerializer

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public interface DataBlockchainSerializer {
    public val network: KSerializer<Network>
    public val permission: KSerializer<Permission>
    public val appMetadata: KSerializer<AppMetadata>
    public val error: KSerializer<BeaconError>
}