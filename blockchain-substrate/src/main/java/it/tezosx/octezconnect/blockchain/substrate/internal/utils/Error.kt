package it.tezosx.octezconnect.blockchain.substrate.internal.utils

import it.tezosx.octezconnect.core.internal.utils.failWithIllegalArgument
import it.tezosx.octezconnect.core.message.BeaconMessage

internal fun failWithUnknownMessage(message: BeaconMessage): Nothing =
    failWithIllegalArgument("Unknown Substrate message ${message::class}")