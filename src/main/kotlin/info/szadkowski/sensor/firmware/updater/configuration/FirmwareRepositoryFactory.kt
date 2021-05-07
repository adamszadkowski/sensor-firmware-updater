package info.szadkowski.sensor.firmware.updater.configuration

import info.szadkowski.sensor.firmware.updater.domain.FirmwareRepository
import info.szadkowski.sensor.firmware.updater.infrastructure.ResourceFirmwareRepository
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
class FirmwareProperties {
    var devices: List<Device> = emptyList()

    class Device {
        var id: String = ""
        var versions: List<Version> = emptyList()

        class Version {
            var version: String = ""
            var path: String = ""
        }
    }
}
