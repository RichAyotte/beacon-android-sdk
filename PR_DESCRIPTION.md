# Migrate Android Beacon SDK to Octez Connect (nodes, packages, docs)

## Summary

This PR migrates the Android SDK from Beacon (AirGap/Papers) to Octez Connect (Trilitech). The migration includes:

- ✅ Replaced all `*.papers.tech` Matrix nodes with Trilitech `*.octez.io` nodes
- ✅ Renamed packages from `it.airgap.beaconsdk.*` to `it.tezosx.octezconnect.*`
- ✅ Updated Gradle `group`/artifact coordinates for Octez Connect branding
- ✅ Added comprehensive Android migration guide with dependency and import mappings
- ✅ Updated README and documentation to Octez Connect naming

## Details / Changes

### Matrix Nodes Replacement

**Files Modified:**
- `transport-p2p-matrix/src/main/java/it/tezosx/octezconnect/transport/p2p/matrix/internal/BeaconP2pMatrixConfiguration.kt`

**Changes:**
- Replaced 8 `*.papers.tech` Matrix nodes with 8 `*.octez.io` nodes
- Added TODO comment to verify exact node URLs match the Octez Connect web SDK

**⚠️ ACTION REQUIRED:** The Matrix node URLs currently use placeholder values (`matrix-node-1.octez.io` through `matrix-node-8.octez.io`). These must be replaced with the exact 8 node URLs from the Octez Connect web SDK before merging. See `OCTEZ_NODES_PLACEHOLDER.md` for details.

### Package & Namespace Renaming

**Scope:**
- All package declarations: `it.airgap.beaconsdk.*` → `it.tezosx.octezconnect.*`
- All imports across 375+ source files
- All AndroidManifest.xml files (8 files)
- Demo app package: `it.airgap.beaconsdkdemo` → `it.tezosx.octezconnectdemo`

**Key Files:**
- `buildSrc/src/main/java/GradleConfig.kt` - Updated namespace
- All module `build.gradle` files - Updated imports and artifactIds
- All source files - Updated package declarations and imports
- All AndroidManifest.xml files - Updated provider references

### Gradle Build Configuration

**Maven Coordinates:**
- **GroupId**: `it.airgap.beaconsdk` → `it.tezosx`
- **ArtifactIds**: Updated to use `octez-connect-` prefix:
  - `core` → `octez-connect-core`
  - `client-dapp` → `octez-connect-client-dapp`
  - `client-wallet` → `octez-connect-client-wallet`
  - `client-wallet-compat` → `octez-connect-client-wallet-compat`
  - `blockchain-tezos` → `octez-connect-blockchain-tezos`
  - `blockchain-substrate` → `octez-connect-blockchain-substrate`
  - `transport-p2p-matrix` → `octez-connect-transport-p2p-matrix`

**Files Modified:**
- All module `build.gradle` files
- Root `build.gradle`
- `settings.gradle` - Updated root project name

### Documentation

**New Files:**
- `docs/migration-android.md` - Comprehensive migration guide for Android developers

**Updated Files:**
- `README.md` - Complete rebrand to Octez Connect, updated installation instructions, added migration guide link

### AndroidManifest Updates

**Provider Changes:**
- Provider class: `it.airgap.beaconsdk.core.provider.BeaconInitProvider` → `it.tezosx.octezconnect.core.provider.BeaconInitProvider`
- Authorities: `${applicationId}.beaconinitprovider` → `${applicationId}.octezconnectinitprovider`

## Migration Notes for Integrators

### Dependency Changes

**Old:**
```groovy
implementation "com.github.airgap-it.beacon-android-sdk:core:$version"
```

**New:**
```groovy
implementation "it.tezosx:octez-connect-core:$version"
```

### Package/Import Changes

**Old:**
```kotlin
import it.airgap.beaconsdk.client.wallet.BeaconWalletClient
import it.airgap.beaconsdk.blockchain.tezos.tezos
```

**New:**
```kotlin
import it.tezosx.octezconnect.client.wallet.BeaconWalletClient
import it.tezosx.octezconnect.blockchain.tezos.tezos
```

### Breaking Changes

1. **Package names**: All imports must be updated from `it.airgap.beaconsdk.*` to `it.tezosx.octezconnect.*`
2. **Maven coordinates**: All dependency declarations must be updated
3. **Matrix nodes**: Internal node URLs changed (no code changes needed unless customizing nodes)

### API Compatibility

The public API surface remains **unchanged**. All major classes and functions maintain the same names and signatures:
- `BeaconWalletClient` - Same API
- `BeaconDAppClient` - Same API
- `tezos()`, `substrate()` - Same factory functions
- `p2pMatrix()` - Same factory function
- Message types (requests/responses) - Same structure

## Testing

### Commands Run

```bash
# Clean build
./gradlew clean

# Package renaming verification
find . -type f -name "*.kt" | xargs grep -l "it.airgap" | wc -l  # Should be 0 (except docs)

# Build verification (partial - full build may require Android SDK)
./gradlew :buildSrc:build
```

### Build Status

- ✅ Gradle configuration compiles
- ✅ Package renaming complete (375+ files)
- ✅ Directory structure updated
- ✅ AndroidManifest files updated
- ⚠️ Full build test pending (requires Android SDK setup)

### Sample App

The demo app has been updated with new package names and should build once the Matrix node URLs are finalized.

## Next Steps

1. **CRITICAL**: Replace placeholder Matrix node URLs with exact values from Octez Connect web SDK
2. Run full build test: `./gradlew clean assembleProd`
3. Run tests: `./gradlew testMockUnitTest`
4. Verify demo app builds and runs
5. Review migration guide for accuracy
6. Update version numbers for first release

## Related

- Migration guide: `docs/migration-android.md`
- Node placeholder info: `OCTEZ_NODES_PLACEHOLDER.md`
- Migration plan: `MIGRATION_PLAN.md`

## Commits

This PR includes the following logical commits:
1. `chore: replace Beacon Matrix nodes with Octez nodes`
2. `refactor: rename Android packages to Octez Connect namespace`
3. `docs: add Android migration guide from Beacon to Octez Connect`
4. `docs: update Android README and references to Octez Connect`
