package info.szadkowski.sensor.firmware.updater.domain.model

data class FirmwareVersion(
    val major: Int,
    val minor: Int,
    val patch: Int,
) : Comparable<FirmwareVersion> {
    private val version = major.shl(16) + minor.shl(8) + patch

    override fun compareTo(other: FirmwareVersion): Int = this.version - other.version

    companion object {
        private val pattern = Regex("(\\d+).(\\d+).(\\d+)")

        fun of(serialized: String): FirmwareVersion {
            val (major, minor, patch) = pattern.find(serialized)!!.destructured
            return FirmwareVersion(major.toInt(), minor.toInt(), patch.toInt())
        }
    }
}
