package info.szadkowski.sensor.firmware.updater.infrastructure

import info.szadkowski.sensor.firmware.updater.domain.Firmware
import info.szadkowski.sensor.firmware.updater.domain.FirmwareRepository
import info.szadkowski.sensor.firmware.updater.domain.model.FirmwareVersion
import io.micronaut.context.annotation.Property
import io.micronaut.context.annotation.PropertySource
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNull
import java.security.MessageDigest
import javax.inject.Inject

@MicronautTest
@PropertySource(
    Property(name = "firmware.devices.a-1.version-to-path[0.1.0]", value = "firmwares/a-1-resource/0.1.txt"),
    Property(name = "firmware.devices.a-2.version-to-path[0.1.0]", value = "may-not-exist.txt"),
    Property(name = "firmware.devices.a-2.version-to-path[0.2.0]", value = "may-not-exist.txt"),
    Property(name = "firmware.devices.a-2.version-to-path[1.0.0]", value = "firmwares/a-1-resource/0.1.txt"),
)
class ResourceFirmwareRepositoryTest(
    @Inject val repository: FirmwareRepository,
) {

    @Test
    fun `missing device`() {
        val version = repository.getNewestFirmwareFor("a-0")

        expectThat(version).isNull()
    }

    @Test
    fun `find single version for device`() {
        val firmware = repository.getNewestFirmwareFor("a-1")

        val content = "firmware binary".toByteArray()
        expectThat(firmware).isEqualTo(
            Firmware(
                version = FirmwareVersion(major = 0, minor = 1, patch = 0),
                content = content,
                md5 = content.md5(),
            )
        )
    }

    @Test
    fun `find newest version for device`() {
        val firmware = repository.getNewestFirmwareFor("a-2")

        expectThat(firmware?.version).isEqualTo(FirmwareVersion(1, 0, 0))
    }

    private fun ByteArray.md5() = MessageDigest.getInstance("MD5").digest(this)
}
