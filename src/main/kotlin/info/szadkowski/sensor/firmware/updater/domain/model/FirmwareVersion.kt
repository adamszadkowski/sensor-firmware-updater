package info.szadkowski.sensor.firmware.updater.domain.model

data class FirmwareVersion(
    val major: Int,
    val minor: Int,
) : Comparable<FirmwareVersion> {
    private val version = major.shl(16) + minor

    override fun compareTo(other: FirmwareVersion): Int = this.version - other.version

    companion object {
        private val pattern = Regex("(\\d+).(\\d+)")

        fun of(serialized: String): FirmwareVersion {
            val (major, minor) = pattern.find(serialized)!!.destructured
            return FirmwareVersion(major.toInt(), minor.toInt())
        }
    }
}
