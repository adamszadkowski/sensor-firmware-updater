package info.szadkowski.sensor.firmware.updater.api

import info.szadkowski.sensor.firmware.updater.domain.FirmwareRepository
import info.szadkowski.sensor.firmware.updater.domain.model.FirmwareVersion
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import javax.inject.Inject

@Controller
class FirmwareEndpoint(
    @Inject private val firmwareRepository: FirmwareRepository,
) {

    @Get(uri = "/firmware/{device}", produces = ["text/plain"])
    fun firmware(
        device: String,
        @Header(name = "x-ESP8266-version") version: String,
    ): HttpResponse<Any> {
        val newestVersionFor = firmwareRepository.getNewestVersionFor(device)
        return when {
            newestVersionFor == null -> HttpResponse.notModified()
            newestVersionFor <= FirmwareVersion.of(version) -> HttpResponse.notModified()
            else -> HttpResponse.ok()
        }
    }
}
