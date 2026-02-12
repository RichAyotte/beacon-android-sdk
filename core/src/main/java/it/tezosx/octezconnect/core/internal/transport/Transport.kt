package it.tezosx.octezconnect.core.internal.transport

import androidx.annotation.RestrictTo
import it.tezosx.octezconnect.core.data.Connection
import it.tezosx.octezconnect.core.internal.message.IncomingConnectionTransportMessage
import it.tezosx.octezconnect.core.internal.message.OutgoingConnectionTransportMessage
import it.tezosx.octezconnect.core.internal.utils.Logger
import it.tezosx.octezconnect.core.internal.utils.logDebug
import it.tezosx.octezconnect.core.transport.data.PairingMessage
import it.tezosx.octezconnect.core.transport.data.PairingRequest
import it.tezosx.octezconnect.core.transport.data.PairingResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public abstract class Transport(protected val logger: Logger?) {
    public abstract val type: Connection.Type

    protected abstract val incomingConnectionMessages: Flow<Result<IncomingConnectionTransportMessage>>

    public abstract suspend fun pair(): Flow<Result<PairingMessage>>
    public abstract suspend fun pair(request: PairingRequest): Result<PairingResponse>

    public abstract fun supportsPairing(request: PairingRequest): Boolean

    protected abstract suspend fun sendMessage(message: OutgoingConnectionTransportMessage): Result<Unit>

    public fun subscribe(): Flow<Result<IncomingConnectionTransportMessage>> =
        incomingConnectionMessages.onStart { logger?.debug("subscribed") }

    public suspend fun send(message: OutgoingConnectionTransportMessage): Result<Unit> {
        logger?.debug("sending ${message.content} to ${message.destination?.id}")
        return sendMessage(message)
    }

    public companion object {
        internal const val TAG = "Transport"
    }
}