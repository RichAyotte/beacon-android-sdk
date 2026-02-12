package it.tezosx.octezconnect.transport.p2p.matrix.internal.matrix.network.user

import it.tezosx.octezconnect.core.internal.network.HttpClient
import it.tezosx.octezconnect.core.internal.network.HttpClient.Companion.post
import it.tezosx.octezconnect.core.internal.network.data.ApplicationJson
import it.tezosx.octezconnect.transport.p2p.matrix.internal.matrix.data.api.MatrixError
import it.tezosx.octezconnect.transport.p2p.matrix.internal.matrix.data.api.login.MatrixLoginRequest
import it.tezosx.octezconnect.transport.p2p.matrix.internal.matrix.data.api.login.MatrixLoginResponse
import it.tezosx.octezconnect.transport.p2p.matrix.internal.matrix.network.MatrixService
import kotlinx.coroutines.flow.single

internal class MatrixUserService(httpClient: HttpClient) : MatrixService(httpClient) {

    suspend fun login(
        node: String,
        user: String,
        password: String,
        deviceId: String,
    ): Result<MatrixLoginResponse> =
        withApi(node) { baseUrl ->
            httpClient.post<MatrixLoginRequest, MatrixLoginResponse, MatrixError>(
                baseUrl,
                "/login",
                body = MatrixLoginRequest.Password(
                    MatrixLoginRequest.UserIdentifier.User(user),
                    password,
                    deviceId
                ),
                headers = listOf(ApplicationJson()),
            ).single()
        }
}