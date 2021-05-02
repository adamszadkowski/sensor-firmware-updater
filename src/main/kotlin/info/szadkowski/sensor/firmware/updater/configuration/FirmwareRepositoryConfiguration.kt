package info.szadkowski.sensor.firmware.updater.configuration

import info.szadkowski.sensor.firmware.updater.domain.FirmwareRepository
import info.szadkowski.sensor.firmware.updater.infrastructure.ResourceFirmwareRepository
import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.context.annotation.Factory
import javax.inject.Inject
import javax.inject.Singleton

@Factory
class FirmwareRepositoryConfiguration(
    @Inject val firmwareProperties: FirmwareProperties,
) {

    @Singleton
    fun firmwareRepository(): FirmwareRepository = ResourceFirmwareRepository(firmwareProperties)
}

@ConfigurationProperties("firmware")
class FirmwareProperties {
    var devices: List<Device> = emptyList()

    class Device {
        val id: String = ""
        val versions: List<String> = emptyList()
    }
}
