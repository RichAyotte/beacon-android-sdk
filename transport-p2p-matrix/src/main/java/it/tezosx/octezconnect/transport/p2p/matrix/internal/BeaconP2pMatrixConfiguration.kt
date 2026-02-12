package it.tezosx.octezconnect.transport.p2p.matrix.internal

internal object BeaconP2pMatrixConfiguration {
    const val MATRIX_API_BASE: String = "/_matrix/client"
    const val MATRIX_API_VERSION: String = "r0"

    const val MATRIX_ROOM_VERSION: String = "5"

    const val MATRIX_MAX_SYNC_RETRIES: Int = 3

    const val JOIN_DELAY_MS: Long = 200
    const val MAX_JOIN_RETRIES: Int = 10

    // These are the same 8 nodes used in the Octez Connect web SDK for consistency
    // Source: P2PCommunicationClient.REGIONS_AND_SERVERS[Regions.EUROPE_WEST]
    val defaultNodes: List<String> = listOf(
        "beacon-node-1.octez.io",
        "beacon-node-2.octez.io",
        "beacon-node-3.octez.io",
        "beacon-node-4.octez.io",
        "beacon-node-5.octez.io",
        "beacon-node-6.octez.io",
        "beacon-node-7.octez.io",
        "beacon-node-8.octez.io"
    )
}