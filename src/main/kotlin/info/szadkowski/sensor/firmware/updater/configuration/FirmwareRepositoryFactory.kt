package info.szadkowski.sensor.firmware.updater.configuration

import info.szadkowski.sensor.firmware.updater.domain.FirmwareRepository
import info.szadkowski.sensor.firmware.updater.infrastructure.ResourceFirmwareRepository
import io.micronaut.context.annotation.ConfigurationInject
import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.context.annotation.Context
import io.micronaut.context.annotation.Factory
import io.micronaut.core.io.scan.ClassPathResourceLoader
import javax.inject.Inject
import javax.inject.Singleton

@Factory
class FirmwareRepositoryFactory(
    @Inject val firmwareProperties: FirmwareProperties,
) {

    @Singleton
    fun firmwareRepository(resourceLoader: ClassPathResourceLoader): FirmwareRepository =
        ResourceFirmwareRepository(resourceLoader, firmwareProperties)
}

@Context
@ConfigurationProperties("firmware")
data class FirmwareProperties @ConfigurationInject constructor(
    val devices: List<Device>,
) {
    data class Device @ConfigurationInject constructor(
        val id: String,
        val versions: List<Version>,
    ) {
        data class Version @ConfigurationInject constructor(
            val version: String,
            val path: String,
        )
    }
}
