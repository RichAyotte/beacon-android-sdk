package it.tezosx.octezconnect.core.internal

import it.tezosx.octezconnect.core.internal.data.BeaconApplication
import it.tezosx.octezconnect.core.internal.di.DependencyRegistry

internal class BeaconContext(val app: BeaconApplication, val dependencyRegistry: DependencyRegistry)