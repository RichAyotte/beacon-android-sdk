# Migration Guide: Beacon Android SDK → Octez Connect Android SDK

## Overview

This guide helps you migrate from the **Beacon Android SDK** (maintained by AirGap/Papers) to the **Octez Connect Android SDK** (maintained by Trilitech). The migration is necessary because the Beacon Matrix nodes hosted by Papers will be shut down on **March 1**. The Octez Connect Android SDK uses the same Matrix infrastructure as the Octez Connect web SDK, ensuring consistent behavior across platforms.

The core functionality and API surface remain largely the same, with the main changes being:
- Updated Matrix node infrastructure (from `*.papers.tech` to `*.octez.io`)
- New package namespace and Maven coordinates
- Updated branding and naming

## Dependency Changes

### Old (Beacon Android SDK)

```groovy
dependencies {
    def beaconVersion = "x.y.z"
    
    // REQUIRED, core
    implementation "com.github.airgap-it.beacon-android-sdk:core:$beaconVersion"
    
    // Optional modules
    implementation "com.github.airgap-it.beacon-android-sdk:client-dapp:$beaconVersion"
    implementation "com.github.airgap-it.beacon-android-sdk:client-wallet:$beaconVersion"
    implementation "com.github.airgap-it.beacon-android-sdk:client-wallet-compat:$beaconVersion"
    implementation "com.github.airgap-it.beacon-android-sdk:blockchain-tezos:$beaconVersion"
    implementation "com.github.airgap-it.beacon-android-sdk:blockchain-substrate:$beaconVersion"
    implementation "com.github.airgap-it.beacon-android-sdk:transport-p2p-matrix:$beaconVersion"
}
```

### New (Octez Connect Android SDK)

```groovy
dependencies {
    def octezConnectVersion = "x.y.z"
    
    // REQUIRED, core
    implementation "com.github.trilitech.octez.connect-android-sdk:core:$octezConnectVersion"
    
    // Optional modules
    implementation "com.github.trilitech.octez.connect-android-sdk:client-dapp:$octezConnectVersion"
    implementation "com.github.trilitech.octez.connect-android-sdk:client-wallet:$octezConnectVersion"
    implementation "com.github.trilitech.octez.connect-android-sdk:client-wallet-compat:$octezConnectVersion"
    implementation "com.github.trilitech.octez.connect-android-sdk:blockchain-tezos:$octezConnectVersion"
    implementation "com.github.trilitech.octez.connect-android-sdk:blockchain-substrate:$octezConnectVersion"
    implementation "com.github.trilitech.octez.connect-android-sdk:transport-p2p-matrix:$octezConnectVersion"
}
```

**Note:** Make sure your `repositories` block includes JitPack:

```groovy
repositories {
    maven { url 'https://jitpack.io' }
    // ... other repositories
}
```

## Package & Import Mapping

All package names have changed from `it.airgap.beaconsdk.*` to `io.tezos.octezconnect.*`. Update your imports accordingly:

| Old Package/Import | New Package/Import |
|-------------------|-------------------|
| `it.airgap.beaconsdk.core.*` | `io.tezos.octezconnect.core.*` |
| `it.airgap.beaconsdk.client.dapp.*` | `io.tezos.octezconnect.client.dapp.*` |
| `it.airgap.beaconsdk.client.wallet.*` | `io.tezos.octezconnect.client.wallet.*` |
| `it.airgap.beaconsdk.client.wallet.compat.*` | `io.tezos.octezconnect.client.wallet.compat.*` |
| `it.airgap.beaconsdk.blockchain.tezos.*` | `io.tezos.octezconnect.blockchain.tezos.*` |
| `it.airgap.beaconsdk.blockchain.substrate.*` | `io.tezos.octezconnect.blockchain.substrate.*` |
| `it.airgap.beaconsdk.transport.p2p.matrix.*` | `io.tezos.octezconnect.transport.p2p.matrix.*` |

### Example Import Updates

**Before:**
```kotlin
import it.airgap.beaconsdk.client.wallet.BeaconWalletClient
import it.airgap.beaconsdk.blockchain.tezos.tezos
import it.airgap.beaconsdk.transport.p2p.matrix.p2pMatrix
```

**After:**
```kotlin
import io.tezos.octezconnect.client.wallet.BeaconWalletClient
import io.tezos.octezconnect.blockchain.tezos.tezos
import io.tezos.octezconnect.transport.p2p.matrix.p2pMatrix
```

## Behavior & API Notes

### Matrix Nodes

The SDK now uses **8 Trilitech-hosted Matrix nodes** with `*.octez.io` domains instead of the previous `*.papers.tech` nodes. These are the same nodes used by the Octez Connect web SDK, ensuring consistency across platforms.

**No code changes required** - the node URLs are configured internally. If you were previously customizing the Matrix nodes, you can continue to do so using the same API:

```kotlin
p2pMatrix(
    matrixNodes = listOf(
        "beacon-node-1.octez.io",
        "beacon-node-2.octez.io",
        // ... other nodes (beacon-node-1 through beacon-node-8.octez.io)
    )
)
```

### AndroidManifest Changes

If you have any custom AndroidManifest entries referencing Beacon providers, update them:

**Before:**
```xml
<provider
    android:name="it.airgap.beaconsdk.core.provider.BeaconInitProvider"
    android:authorities="${applicationId}.beaconinitprovider"
    android:exported="false" />
```

**After:**
```xml
<provider
    android:name="io.tezos.octezconnect.core.provider.BeaconInitProvider"
    android:authorities="${applicationId}.octezconnectinitprovider"
    android:exported="false" />
```

**Note:** The SDK's AndroidManifest will handle this automatically if you're using the library modules directly. Only update if you have explicit overrides.

### API Compatibility

The public API surface remains **largely unchanged**. All major classes and functions maintain the same names and signatures:

- `BeaconWalletClient` - Same API
- `BeaconDAppClient` - Same API
- `tezos()`, `substrate()` - Same factory functions
- `p2pMatrix()` - Same factory function
- Message types (requests/responses) - Same structure

### Breaking Changes

1. **Package names**: All imports must be updated (see mapping table above)
2. **Maven coordinates**: Dependency declarations must be updated (see dependency section)
3. **Matrix nodes**: Internal node URLs changed (no code changes needed unless you were customizing nodes)

## Migration Steps

1. **Update dependencies** in your `build.gradle` files (see Dependency Changes section)

2. **Update imports** throughout your codebase:
   - Use your IDE's "Find and Replace" feature
   - Search for: `it.airgap.beaconsdk`
   - Replace with: `io.tezos.octezconnect`

3. **Update AndroidManifest** (if you have custom provider entries)

4. **Test your integration**:
   - Verify wallet connections work
   - Test dApp communication
   - Ensure Matrix transport is functioning

5. **Clean and rebuild**:
   ```bash
   ./gradlew clean build
   ```

## Troubleshooting

### Build Errors

If you see import errors after migration:
- Ensure all dependencies are updated to the new coordinates
- Clean your build: `./gradlew clean`
- Invalidate caches in Android Studio if needed

### Runtime Issues

If connections fail:
- Verify you're using the latest SDK version
- Check that Matrix nodes are accessible from your network
- Review logs for connection errors

## Support

For issues or questions about the migration:
- Check the [Octez Connect documentation](https://github.com/trilitech/octez-connect-android-sdk)
- Open an issue on the repository
- Contact Trilitech support

## Additional Resources

- [Octez Connect Web SDK Migration Guide](../octez-connect-sdk/docs/migration.md) (for reference)
- [Beacon Protocol Documentation](https://docs.walletbeacon.io/) (protocol details remain the same)
