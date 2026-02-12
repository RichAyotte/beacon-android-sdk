package it.tezosx.octezconnect.blockchain.tezos.internal.utils

import it.tezosx.octezconnect.core.internal.utils.failWithIllegalArgument
import it.tezosx.octezconnect.core.message.BeaconMessage

internal fun failWithUnknownMessage(message: BeaconMessage): Nothing =
    failWithIllegalArgument("Unknown Tezos message ${message::class}")
