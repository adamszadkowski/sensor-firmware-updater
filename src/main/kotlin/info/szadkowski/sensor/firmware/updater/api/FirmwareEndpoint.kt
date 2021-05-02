package info.szadkowski.sensor.firmware.updater.api

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller
class FirmwareEndpoint {

    @Get(uri = "/firmware/{device}", produces = ["text/plain"])
    fun firmware(device: String): HttpResponse<Any> = HttpResponse.notModified()
}
