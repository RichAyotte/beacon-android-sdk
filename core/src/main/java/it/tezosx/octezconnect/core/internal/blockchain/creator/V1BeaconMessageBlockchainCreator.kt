package it.tezosx.octezconnect.core.internal.blockchain.creator

import androidx.annotation.RestrictTo
import it.tezosx.octezconnect.core.internal.message.v1.V1BeaconMessage
import it.tezosx.octezconnect.core.message.BeaconMessage

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public interface V1BeaconMessageBlockchainCreator {
    public fun from(senderId: String, message: BeaconMessage): Result<V1BeaconMessage>
}