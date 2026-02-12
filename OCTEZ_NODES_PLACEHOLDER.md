# Octez Matrix Node URLs - ACTION REQUIRED

## Status: ⚠️ PLACEHOLDER VALUES IN USE

The Matrix node URLs in `BeaconP2pMatrixConfiguration.kt` currently use placeholder values:
- `matrix-node-1.octez.io` through `matrix-node-8.octez.io`

## Required Action

**You MUST replace these with the exact 8 Matrix node URLs from the Octez Connect web SDK.**

The nodes should be the same ones used in:
- `trilitech/octez-connect` or `trilitech/octez-connect-sdk` repository

## Current Placeholder Structure

```kotlin
val defaultNodes: List<String> = listOf(
    "matrix-node-1.octez.io",  // TODO: Replace with actual node from web SDK
    "matrix-node-2.octez.io",  // TODO: Replace with actual node from web SDK
    "matrix-node-3.octez.io",  // TODO: Replace with actual node from web SDK
    "matrix-node-4.octez.io",  // TODO: Replace with actual node from web SDK
    "matrix-node-5.octez.io",  // TODO: Replace with actual node from web SDK
    "matrix-node-6.octez.io",  // TODO: Replace with actual node from web SDK
    "matrix-node-7.octez.io",  // TODO: Replace with actual node from web SDK
    "matrix-node-8.octez.io"   // TODO: Replace with actual node from web SDK
)
```

## Location

File: `transport-p2p-matrix/src/main/java/it/airgap/beaconsdk/transport/p2p/matrix/internal/BeaconP2pMatrixConfiguration.kt`

## Next Steps

1. Locate the Matrix node configuration in the Octez Connect web SDK
2. Copy the exact 8 node URLs
3. Replace the placeholder values in the Android SDK
4. Remove this placeholder file
