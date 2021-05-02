package info.szadkowski.sensor.firmware.updater.domain

import info.szadkowski.sensor.firmware.updater.domain.model.FirmwareVersion

interface FirmwareRepository {
    fun getNewestVersionFor(device: String): FirmwareVersion?
}
