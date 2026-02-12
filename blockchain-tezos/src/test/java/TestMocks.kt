import android.content.Context
import io.mockk.*
import it.tezosx.octezconnect.blockchain.tezos.Tezos
import it.tezosx.octezconnect.blockchain.tezos.internal.creator.*
import it.tezosx.octezconnect.blockchain.tezos.internal.di.ExtendedDependencyRegistry
import it.tezosx.octezconnect.blockchain.tezos.internal.di.extend
import it.tezosx.octezconnect.blockchain.tezos.internal.serializer.*
import it.tezosx.octezconnect.core.internal.BeaconSdk
import it.tezosx.octezconnect.core.internal.blockchain.BlockchainRegistry
import it.tezosx.octezconnect.core.internal.data.BeaconApplication
import it.tezosx.octezconnect.core.internal.di.DependencyRegistry
import it.tezosx.octezconnect.core.internal.utils.currentTimestamp

// -- class --

internal fun mockBeaconSdk(
    beaconId: String = "beaconId",
    app: BeaconApplication = mockkClass(BeaconApplication::class),
    dependencyRegistry: DependencyRegistry = mockk(relaxed = true),
): BeaconSdk =
    mockkClass(BeaconSdk::class).also {
        mockkObject(BeaconSdk)
        every { BeaconSdk.instance } returns it

        val contextMock = mockk<Context>(relaxed = true)

        coEvery { it.add(any(), any(), any(), any(), any(), any()) } returns Unit

        every { it.applicationContext } returns contextMock
        every { it.app(any()) } returns app
        every { it.beaconId(any()) } returns beaconId
        every { it.dependencyRegistry(any()) } returns dependencyRegistry
    }

// -- static --

internal fun mockTime(currentTimeMillis: Long = 1) {
    mockkStatic("it.tezosx.octezconnect.core.internal.utils.TimeKt")
    every { currentTimestamp() } returns currentTimeMillis
}

internal fun mockDependencyRegistry(tezos: Tezos? = null): DependencyRegistry =
    mockkClass(DependencyRegistry::class).also {
        mockkStatic("it.tezosx.octezconnect.blockchain.tezos.internal.di.ExtendedDependencyRegistryKt")
        val extendedDependencyRegistry = mockkClass(ExtendedDependencyRegistry::class)
        every { it.extend() } returns extendedDependencyRegistry

        if (tezos != null) {
            val blockchainRegistry = mockkClass(BlockchainRegistry::class)

            every { blockchainRegistry.get(any()) } returns tezos
            every { blockchainRegistry.getOrNull(any()) } returns tezos
            every { it.blockchainRegistry } returns blockchainRegistry

            every { extendedDependencyRegistry.tezosWallet } returns tezos.wallet
            every { extendedDependencyRegistry.tezosCreator } returns tezos.creator as TezosCreator
            every { extendedDependencyRegistry.tezosSerializer } returns tezos.serializer as TezosSerializer
        }

        mockBeaconSdk(dependencyRegistry = it)
    }
