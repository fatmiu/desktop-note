package util

fun parseDate(year: String, month: String, day: String): String {
    val dateList = listOf(year, month, day)
    return dateList.joinToString(separator = "-") { it }
}