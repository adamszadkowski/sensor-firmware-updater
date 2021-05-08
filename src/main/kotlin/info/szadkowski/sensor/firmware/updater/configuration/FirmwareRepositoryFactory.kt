package info.szadkowski.sensor.firmware.updater.configuration

import info.szadkowski.sensor.firmware.updater.domain.FirmwareRepository
import info.szadkowski.sensor.firmware.updater.infrastructure.ResourceFirmwareRepository
import io.micronaut.context.annotation.*
import io.micronaut.core.convert.format.MapFormat
import io.micronaut.core.io.scan.ClassPathResourceLoader
import javax.inject.Inject
import javax.inject.Singleton

@Factory
class FirmwareRepositoryFactory(
    @Inject val devices: List<Device>,
) {

    @Singleton
    fun firmwareRepository(resourceLoader: ClassPathResourceLoader): FirmwareRepository =
        ResourceFirmwareRepository(resourceLoader, devices)
}

@Context
@EachProperty("firmware.devices")
data class Device @ConfigurationInject constructor(
    @Parameter val id: String,
    @MapFormat(transformation = MapFormat.MapTransformation.FLAT)
    val versionToPath: Map<String, String>,
)
