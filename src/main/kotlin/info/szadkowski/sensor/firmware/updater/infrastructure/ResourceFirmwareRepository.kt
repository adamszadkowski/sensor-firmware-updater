package info.szadkowski.sensor.firmware.updater.infrastructure

import info.szadkowski.sensor.firmware.updater.configuration.Device
import info.szadkowski.sensor.firmware.updater.domain.Firmware
import info.szadkowski.sensor.firmware.updater.domain.FirmwareRepository
import info.szadkowski.sensor.firmware.updater.domain.model.FirmwareVersion
import io.micronaut.core.io.scan.ClassPathResourceLoader
import java.security.MessageDigest

class ResourceFirmwareRepository(
    private val resourceLoader: ClassPathResourceLoader,
    devices: List<Device>,
) : FirmwareRepository {
    private val md5MessageDigest = MessageDigest.getInstance("MD5")

    private val pathsByDevices = devices
        .associateBy { it.id.toLowerCase() }
        .mapValues { (_, v) ->
            v.versionToPath
                .map { (version, path) -> FirmwareMetadata(FirmwareVersion.of(version), path) }
                .maxByOrNull { it.version }!!
        }

    init {
        val missingPaths = pathsByDevices
            .map { (_, m) -> m.path }
            .filter { resourceLoader.getResource(it).isEmpty }
        if (missingPaths.isNotEmpty())
            throw FirmwareRepository.MissingPathException(missingPaths.joinToString(", "))
    }

    override fun getNewestFirmwareFor(device: String): Firmware? = pathsByDevices[device.toLowerCase()]?.toDomain()

    private fun FirmwareMetadata.toDomain(): Firmware {
        val content = resourceLoader.getResourceAsStream(path).map { it.readAllBytes() }.orElse(ByteArray(0))
        return Firmware(
            version = version,
            content = content,
            md5 = md5MessageDigest.digest(content),
        )
    }

    private data class FirmwareMetadata(
        val version: FirmwareVersion,
        val path: String,
    )
}
