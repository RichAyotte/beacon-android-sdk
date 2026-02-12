package it.tezosx.octezconnect.core.blockchain

import androidx.annotation.RestrictTo
import it.tezosx.octezconnect.core.data.Permission
import it.tezosx.octezconnect.core.internal.blockchain.creator.DataBlockchainCreator
import it.tezosx.octezconnect.core.internal.blockchain.creator.V1BeaconMessageBlockchainCreator
import it.tezosx.octezconnect.core.internal.blockchain.creator.V2BeaconMessageBlockchainCreator
import it.tezosx.octezconnect.core.internal.blockchain.creator.V3BeaconMessageBlockchainCreator
import it.tezosx.octezconnect.core.internal.blockchain.serializer.DataBlockchainSerializer
import it.tezosx.octezconnect.core.internal.blockchain.serializer.V1BeaconMessageBlockchainSerializer
import it.tezosx.octezconnect.core.internal.blockchain.serializer.V2BeaconMessageBlockchainSerializer
import it.tezosx.octezconnect.core.internal.blockchain.serializer.V3BeaconMessageBlockchainSerializer
import it.tezosx.octezconnect.core.internal.di.DependencyRegistry
import it.tezosx.octezconnect.core.internal.message.v1.V1BeaconMessage
import it.tezosx.octezconnect.core.internal.message.v2.V2BeaconMessage
import it.tezosx.octezconnect.core.internal.message.v3.V3BeaconMessage
import it.tezosx.octezconnect.core.message.BeaconMessage
import it.tezosx.octezconnect.core.message.PermissionBeaconRequest
import it.tezosx.octezconnect.core.message.PermissionBeaconResponse
import kotlinx.serialization.KSerializer

public interface Blockchain {
    @get:RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public val identifier: String

    @get:RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public val creator: Creator

    @get:RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public val serializer: Serializer

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public interface Creator {

        // -- data --

        public val data: DataBlockchainCreator

        // -- VersionedBeaconMessage --

        public val v1: V1BeaconMessageBlockchainCreator
        public val v2: V2BeaconMessageBlockchainCreator
        public val v3: V3BeaconMessageBlockchainCreator
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public interface Serializer {

        // -- data --

        public val data: DataBlockchainSerializer

        // -- VersionedBeaconMessage --

        public val v1: V1BeaconMessageBlockchainSerializer
        public val v2: V2BeaconMessageBlockchainSerializer
        public val v3: V3BeaconMessageBlockchainSerializer
    }

    public interface Factory<T : Blockchain> {
        public val identifier: String

        @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
        public fun create(dependencyRegistry: DependencyRegistry): T
    }
}
