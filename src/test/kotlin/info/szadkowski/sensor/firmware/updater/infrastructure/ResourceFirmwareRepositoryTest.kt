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
import javax.inject.Inject

@MicronautTest
@PropertySource(
    Property(name = "firmware.devices[0].id", value = "a-1"),
    Property(name = "firmware.devices[0].versions[0].version", value = "0.1"),
    Property(name = "firmware.devices[0].versions[0].content", value = "content"),
    Property(name = "firmware.devices[1].id", value = "a-2"),
    Property(name = "firmware.devices[1].versions[0].version", value = "0.1"),
    Property(name = "firmware.devices[1].versions[1].version", value = "0.2"),
    Property(name = "firmware.devices[1].versions[2].version", value = "1.0"),
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

        expectThat(firmware).isEqualTo(
            Firmware(
                version = FirmwareVersion(major = 0, minor = 1),
                content = "content".toByteArray(),
            )
        )
    }

    @Test
    fun `find newest version for device`() {
        val firmware = repository.getNewestFirmwareFor("a-2")

        expectThat(firmware?.version).isEqualTo(FirmwareVersion(1, 0))
    }
}
