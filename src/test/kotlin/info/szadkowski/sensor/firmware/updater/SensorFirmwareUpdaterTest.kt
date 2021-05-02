package info.szadkowski.sensor.firmware.updater

import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isTrue
import javax.inject.Inject

@MicronautTest
class SensorFirmwareUpdaterTest(
    @Inject private val application: EmbeddedApplication<*>,
) {

    @Test
    fun `application is running`() {
        expectThat(application.isRunning).isTrue()
    }
}
