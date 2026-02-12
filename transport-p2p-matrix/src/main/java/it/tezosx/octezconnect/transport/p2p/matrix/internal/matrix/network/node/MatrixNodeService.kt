package it.tezosx.octezconnect.transport.p2p.matrix.internal.matrix.network.node

import it.tezosx.octezconnect.core.internal.network.HttpClient
import it.tezosx.octezconnect.core.internal.network.HttpClient.Companion.get
import it.tezosx.octezconnect.transport.p2p.matrix.internal.matrix.data.api.MatrixError
import it.tezosx.octezconnect.transport.p2p.matrix.internal.matrix.data.api.node.MatrixVersionsResponse
import it.tezosx.octezconnect.transport.p2p.matrix.internal.matrix.network.MatrixService
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single

internal class MatrixNodeService(httpClient: HttpClient) : MatrixService(httpClient) {

    suspend fun isUp(node: String): Boolean =
        withApiBase(node) { baseUrl ->
            httpClient.get<MatrixVersionsResponse, MatrixError>(baseUrl, "/versions")
                .map { it.isSuccess }
                .single()
        }
}