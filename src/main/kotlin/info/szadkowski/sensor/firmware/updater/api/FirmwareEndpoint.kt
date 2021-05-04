package info.szadkowski.sensor.firmware.updater.api

import info.szadkowski.sensor.firmware.updater.domain.FirmwareRepository
import info.szadkowski.sensor.firmware.updater.domain.model.FirmwareVersion
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.server.types.files.StreamedFile
import java.math.BigInteger
import javax.inject.Inject

@Controller
class FirmwareEndpoint(
    @Inject private val firmwareRepository: FirmwareRepository,
) {

    @Get(uri = "/firmware/{device}", produces = ["application/octet-stream"])
    fun firmware(
        device: String,
        @Header("x-ESP8266-version") version: String,
    ): HttpResponse<StreamedFile> {
        val newestFirmware = firmwareRepository.getNewestFirmwareFor(device)
        val newestVersion = newestFirmware?.version
        return when {
            newestVersion == null -> HttpResponse.notModified()
            newestVersion <= FirmwareVersion.of(version) -> HttpResponse.notModified()
            else -> HttpResponse.ok(
                StreamedFile(
                    newestFirmware.content.inputStream(),
                    MediaType.APPLICATION_OCTET_STREAM_TYPE
                ).attach("firmware.bin")
            ).header("x-MD5", newestFirmware.md5.serialize())
        }
    }

    private fun ByteArray.serialize() = BigInteger(1, this).toString(16).padStart(32, '0')
}
