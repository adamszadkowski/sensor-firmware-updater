package info.szadkowski.sensor.firmware.updater.api

import info.szadkowski.sensor.firmware.updater.domain.FirmwareRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import javax.inject.Inject

@Controller
class FirmwareEndpoint(
    @Inject private val firmwareRepository: FirmwareRepository,
) {

    @Get(uri = "/firmware/{device}", produces = ["text/plain"])
    fun firmware(device: String): HttpResponse<Any> = when(firmwareRepository.getNewestVersionFor(device)) {
        null -> HttpResponse.notModified()
        else -> HttpResponse.ok()
    }
}
