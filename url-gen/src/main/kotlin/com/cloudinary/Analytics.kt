package com.cloudinary

private const val ALGO_VERSION = 'A'
private const val SDK = 'H'
private const val ERROR_SIGNATURE = "E"
private const val NO_FEATURE_CHAR = '0'

internal fun generateAnalyticsSignature(
    sdkVersion: String = Cloudinary.SDK_VERSION,
    kotlinVersion: KotlinVersion = KotlinVersion.CURRENT
): String {
    return try {
        val kotlinVerString = with(kotlinVersion) {
            generateVersionString(major, minor) // ignore kotlin patch
        }

        "$ALGO_VERSION$SDK${generateVersionString(sdkVersion)}$kotlinVerString$NO_FEATURE_CHAR"
    } catch (e: Exception) {
        ERROR_SIGNATURE
    }
}

// Note: This generates the analytics string based on breaking the string into three integers, ignoring any characters
// after the last dash: "0.0.1-beta.6" is treated as [major:0, minor:0, patch:1] - the rest is ignored.
// This can also take a {major.minor} string without a patch.
private fun generateVersionString(version: String): String {
    version.split(".", "-").let { split ->
        return generateVersionString(split[0], split[1], if (split.size > 2) split[2] else null)
    }
}

private fun generateVersionString(major: Any, minor: Any, patch: Any? = null): String {
    val versionString = ((patch?.toString()?.padStart(2, '0') ?: "") +
            minor.toString().padStart(2, '0') +
            major.toString().padStart(2, '0'))
        .toLong().toString(2).padStart(18, '0')

    val patchStr = if (patch != null) versionString.substring(0..5).toAnalyticsVersionStr() else ""
    val minorStr = versionString.substring(6..11).toAnalyticsVersionStr()
    val majorStr = versionString.substring(12..17).toAnalyticsVersionStr()

    return patchStr + minorStr + majorStr
}

private fun String.toAnalyticsVersionStr(): String {
    return when (val num = this.toInt(2)) {
        in 0..25 -> {
            ('A' + num).toString()
        }
        in 26..51 -> {
            ('a' + num - 26).toString()
        }
        else -> ('0' + num - 52).toString()
    }
}
