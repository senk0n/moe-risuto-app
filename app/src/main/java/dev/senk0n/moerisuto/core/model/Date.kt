package dev.senk0n.moerisuto.core.model

/**
 * uses [standard date format](https://en.wikipedia.org/wiki/ISO_8601) yyyy-MM-dd
 * could be constructed from [String] and mapped [toSeason]
 */
data class Date(
    val year: Int?,
    val month: Int?,
    val day: Int?
)

fun Date(str: String): Date {
    val subStrings = str.split("-", limit = 3)

    var year: Int? = null
    var month: Int? = null
    var day: Int? = null

    for (index in subStrings.indices) {
        when (index) {
            0 -> year = subStrings[index].toIntOrNull()?.takeIf { it in 1..9999 }
            1 -> month = subStrings[index].toIntOrNull()?.takeIf { it in 1..12 } ?: break
            else -> day = subStrings[index].toIntOrNull()?.takeIf { it in 1..31 } ?: break
        }
    }

    return Date(year, month, day)
}