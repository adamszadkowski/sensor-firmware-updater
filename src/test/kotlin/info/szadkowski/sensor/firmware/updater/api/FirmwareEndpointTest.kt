package info.szadkowski.sensor.firmware.updater.api

import io.micronaut.context.annotation.Property
import io.micronaut.context.annotation.PropertySource
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import javax.inject.Inject

@MicronautTest
@PropertySource(
    Property(name = "firmware.devices[0].id", value = "a-1"),
    Property(name = "firmware.devices[0].versions[0]", value = "0.1"),
)
class FirmwareEndpointTest(
    @Inject private val client: FirmwareClient,
) {

    @Test
    fun `not found version is marked as not modified`() {
        val firmware = client.firmware("a-0")

        expectThat(firmware).isEqualTo(HttpStatus.NOT_MODIFIED)
    }

    @Test
    fun `return newer firmware`() {
        val firmware = client.firmware("a-1")

        expectThat(firmware).isEqualTo(HttpStatus.OK)
    }

    @Client("/")
    interface FirmwareClient {

        @Get(uri = "/firmware/{device}", consumes = ["text/plain"])
        fun firmware(device: String): HttpStatus
    }
}
