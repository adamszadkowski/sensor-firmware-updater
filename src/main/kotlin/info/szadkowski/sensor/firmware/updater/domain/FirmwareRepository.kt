package info.szadkowski.sensor.firmware.updater.domain

import info.szadkowski.sensor.firmware.updater.domain.model.FirmwareVersion

interface FirmwareRepository {
    fun getNewestFirmwareFor(device: String): Firmware?
}

data class Firmware(
    val version: FirmwareVersion,
    val content: ByteArray,
    val md5: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Firmware

        if (version != other.version) return false
        if (!content.contentEquals(other.content)) return false
        if (!md5.contentEquals(other.md5)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = version.hashCode()
        result = 31 * result + content.contentHashCode()
        result = 31 * result + md5.contentHashCode()
        return result
    }
}
