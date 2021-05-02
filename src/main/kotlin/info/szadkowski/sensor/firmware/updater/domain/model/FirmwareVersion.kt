package info.szadkowski.sensor.firmware.updater.domain.model

data class FirmwareVersion(
    val major: Int,
    val minor: Int,
) {
    companion object {
        private val pattern = Regex("(\\d+).(\\d+)")

        fun of(serialized: String): FirmwareVersion {
            val (major, minor) = pattern.find(serialized)!!.destructured
            return FirmwareVersion(major.toInt(), minor.toInt())
        }
    }
}
