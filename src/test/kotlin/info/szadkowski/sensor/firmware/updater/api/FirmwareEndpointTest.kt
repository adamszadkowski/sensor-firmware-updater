package info.szadkowski.sensor.firmware.updater.api

import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import javax.inject.Inject

@MicronautTest
class FirmwareEndpointTest(
    @Inject private val client: FirmwareClient,
) {

    @Test
    fun `not found version is marked as not modified`() {
        val firmware = client.firmware("a-1")

        expectThat(firmware).isEqualTo(HttpStatus.NOT_MODIFIED)
    }

    @Client("/")
    interface FirmwareClient {

        @Get(uri = "/firmware/{device}", consumes = ["text/plain"])
        fun firmware(device: String): HttpStatus
    }
}
