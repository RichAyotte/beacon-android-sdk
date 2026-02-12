package it.tezosx.octezconnectdemo.dapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.tezosx.octezconnect.blockchain.substrate.substrate
import it.tezosx.octezconnect.blockchain.tezos.extension.requestTezosPermission
import it.tezosx.octezconnect.blockchain.tezos.tezos
import it.tezosx.octezconnect.client.dapp.BeaconDAppClient
import it.tezosx.octezconnect.core.message.BeaconResponse
import it.tezosx.octezconnect.transport.p2p.matrix.p2pMatrix
import it.tezosx.octezconnectdemo.utils.emit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DAppFragmentViewModel : ViewModel() {
    private val _state: MutableStateFlow<DAppFragment.State> = MutableStateFlow(DAppFragment.State())
    val state: Flow<DAppFragment.State>
        get() = _state

    init {
        viewModelScope.launch {
            val client = BeaconDAppClient("Beacon SDK Demo (DApp)", clientId = "__dapp__") {
                support(tezos(), substrate())
                use(p2pMatrix())

                ignoreUnsupportedBlockchains = true
            }.also { beaconClient = it }

            client.connect()
                .onStart { checkForActiveAccount() }
                .collect { result ->
                    result.getOrNull()?.let { onBeaconResponse(it) }
                    result.exceptionOrNull()?.let { onError(it) }
                }
        }
    }

    private var beaconClient: BeaconDAppClient? = null
    private var awaitingResponse: BeaconResponse? = null

    fun pair() {
        viewModelScope.launch {
            val beaconClient = beaconClient ?: return@launch

            try {
                val pairingRequest = beaconClient.pair()
                val serializerPairingRequest = beaconClient.serializePairingData(pairingRequest)

                _state.emit { copy(pairingRequest = serializerPairingRequest) }
            } catch (e: Throwable) {
                onError(e)
            }
        }
    }

    fun requestPermission() {
        viewModelScope.launch {
            try {
                beaconClient?.requestTezosPermission()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    fun clearResponse() {
        awaitingResponse = null
        _state.emit { copy(beaconResponse = null) }
    }

    fun reset() {
        viewModelScope.launch {
            beaconClient?.reset()
            checkForActiveAccount()
        }
    }

    private suspend fun onBeaconResponse(response: BeaconResponse) {
        awaitingResponse = response
        checkForActiveAccount()
        _state.emit { copy(beaconResponse = response) }
    }

    private fun onError(error: Throwable) {
        _state.emit { copy(error = error) }
    }

    private suspend fun checkForActiveAccount() {
        val activeAccount = beaconClient?.getActiveAccount()
        _state.emit { copy(activeAccount = activeAccount?.accountId) }
    }

    private fun MutableStateFlow<DAppFragment.State>.emit(update: DAppFragment.State.() -> DAppFragment.State) {
        emit(DAppFragment.State(), update)
    }
}