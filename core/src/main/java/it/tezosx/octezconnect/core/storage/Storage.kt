package it.tezosx.octezconnect.core.storage

import it.tezosx.octezconnect.core.data.AppMetadata
import it.tezosx.octezconnect.core.data.Maybe
import it.tezosx.octezconnect.core.data.Peer
import it.tezosx.octezconnect.core.data.Permission
import it.tezosx.octezconnect.core.internal.BeaconConfiguration
import it.tezosx.octezconnect.core.internal.storage.decorator.DecoratedStorage
import it.tezosx.octezconnect.core.scope.BeaconScope

public interface Storage {

    // -- Beacon --

    public suspend fun getMaybePeers(): List<Maybe<Peer>>
    public suspend fun setPeers(p2pPeers: List<Peer>)

    public suspend fun getMaybeAppMetadata(): List<Maybe<AppMetadata>>
    public suspend fun setAppMetadata(appMetadata: List<AppMetadata>)

    public suspend fun getMaybePermissions(): List<Maybe<Permission>>
    public suspend fun setPermissions(permissions: List<Permission>)

    // -- SDK --

    public suspend fun getSdkVersion(): String?
    public suspend fun setSdkVersion(sdkVersion: String)

    public suspend fun getMigrations(): Set<String>
    public suspend fun setMigrations(migrations: Set<String>)

    public fun scoped(beaconScope: BeaconScope): Storage
    public fun extend(beaconConfiguration: BeaconConfiguration): ExtendedStorage = DecoratedStorage(this, beaconConfiguration)
}