package info.szadkowski.sensor.firmware.updater.infrastructure

import info.szadkowski.sensor.firmware.updater.configuration.FirmwareProperties
import info.szadkowski.sensor.firmware.updater.domain.Firmware
import info.szadkowski.sensor.firmware.updater.domain.FirmwareRepository
import info.szadkowski.sensor.firmware.updater.domain.model.FirmwareVersion

class ResourceFirmwareRepository(
    firmwareProperties: FirmwareProperties,
) : FirmwareRepository {
    private val versionsByDevice = firmwareProperties.devices
        .associateBy { it.id }
        .mapValues { (_, v) ->
            v.versions.map { it.toDomain() }.sortedByDescending { it.version }
        }

    override fun getNewestFirmwareFor(device: String): Firmware? =
        versionsByDevice[device]?.first()

    private fun FirmwareProperties.Device.Version.toDomain() =
        Firmware(
            version = FirmwareVersion.of(version),
            content = content.toByteArray(),
        )
}
