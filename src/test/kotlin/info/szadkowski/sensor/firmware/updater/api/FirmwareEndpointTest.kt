package info.szadkowski.sensor.firmware.updater.api

import io.micronaut.context.annotation.Property
import io.micronaut.context.annotation.PropertySource
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.isEqualTo
import javax.inject.Inject

@MicronautTest
@PropertySource(
    Property(name = "firmware.devices.a-1.version-to-path[0.2.0]", value = "firmwares/a-1-resource/0.2.txt"),
)
class FirmwareEndpointTest(
    @Inject private val client: FirmwareClient,
) {

    @Test
    fun `not found version is marked as not modified`() {
        val firmware = client.firmware(device = "a-0", "0.1.0")

        expectThat(firmware.status()).isEqualTo(HttpStatus.NOT_MODIFIED)
    }

    @Test
    fun `same version is marked as not modified`() {
        val firmware = client.firmware(device = "a-1", version = "0.2.0")

        expectThat(firmware.status()).isEqualTo(HttpStatus.NOT_MODIFIED)
    }

    @Test
    fun `return newer firmware`() {
        val firmware = client.firmware(device = "a-1", version = "0.1.0")

        expectThat(firmware.status()).isEqualTo(HttpStatus.OK)
        expectThat(firmware.body()).isEqualTo("content".toByteArray())
        expectThat(firmware.header("Content-Disposition")).contains("""filename="firmware.bin"""")
        expectThat(firmware.header("Content-Length")).isEqualTo("7")
        expectThat(firmware.header("x-MD5")).isEqualTo("9a0364b9e99bb480dd25e1f0284c8555")
    }

    @Client("/")
    interface FirmwareClient {

        @Get(uri = "/firmware/{device}", consumes = ["application/octet-stream"])
        fun firmware(
            device: String,
            @Header(name = "x-ESP8266-version") version: String,
        ): HttpResponse<ByteArray>
    }
}
