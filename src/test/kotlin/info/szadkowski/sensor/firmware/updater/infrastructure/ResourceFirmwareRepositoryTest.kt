package info.szadkowski.sensor.firmware.updater.infrastructure

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
    Property(name = "firmware.devices[0].versions[0]", value = "0.1"),
)
class ResourceFirmwareRepositoryTest(
    @Inject val repository: FirmwareRepository,
) {

    @Test
    fun `missing device`() {
        val version = repository.getNewestVersionFor("a-0")

        expectThat(version).isNull()
    }

    @Test
    fun `find single version for device`() {
        val version = repository.getNewestVersionFor("a-1")

        expectThat(version).isEqualTo(FirmwareVersion(0, 1))
    }
}
