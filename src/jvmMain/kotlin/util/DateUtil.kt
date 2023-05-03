package util

import java.util.*

object DateUtil {
    fun parseDate(year: String, month: String, day: String): String {
        val dateList = listOf(year, month, day)
        return dateList.joinToString(separator = "-") { it }
    }

    fun parseDate(calendar: Calendar): String {
        return parseDate(
            calendar.get(Calendar.YEAR).toString(),
            "%02d".format(calendar.get(Calendar.MONTH) + 1),
            "%02d".format(calendar.get(Calendar.DAY_OF_MONTH))
        )
    }


    fun getDate(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.DAY_OF_MONTH)
    }

    fun generateDatesForMonth(calendar: Calendar): List<Calendar> {
        val dates = mutableListOf<Calendar>()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK)
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        // Add dates from previous month to fill out the first row of the grid
        val prevMonth = calendar.clone() as Calendar
        prevMonth.add(Calendar.MONTH, -1)
        val daysInPrevMonth = prevMonth.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (i in (daysInPrevMonth - firstDayOfMonth + 2)..daysInPrevMonth) {
            val date = Calendar.getInstance().apply {
                set(Calendar.YEAR, prevMonth.get(Calendar.YEAR))
                set(Calendar.MONTH, prevMonth.get(Calendar.MONTH))
                set(Calendar.DAY_OF_MONTH, i)
            }
            dates.add(date)
        }

        // Add dates from current month
        for (i in 1..daysInMonth) {
            val date = Calendar.getInstance().apply {
                set(Calendar.YEAR, calendar.get(Calendar.YEAR))
                set(Calendar.MONTH, calendar.get(Calendar.MONTH))
                set(Calendar.DAY_OF_MONTH, i)
            }
            dates.add(date)
        }

        // Add dates from next month to fill out the last row of the grid
        val nextMonth = calendar.clone() as Calendar
        nextMonth.add(Calendar.MONTH, 1)
        val numDaysToAdd = 7 - (dates.size % 7)
        for (i in 1..numDaysToAdd) {
            val date = Calendar.getInstance().apply {
                set(Calendar.YEAR, nextMonth.get(Calendar.YEAR))
                set(Calendar.MONTH, nextMonth.get(Calendar.MONTH))
                set(Calendar.DAY_OF_MONTH, i)
            }
            dates.add(date)
        }

        return dates
    }

    fun getFirstDayOfMonth(calendar: Calendar): Calendar {
        return Calendar.getInstance().apply {
            set(Calendar.YEAR, calendar.get(Calendar.YEAR))
            set(Calendar.MONTH, calendar.get(Calendar.MONTH))
            set(Calendar.DAY_OF_MONTH, 1)
        }
    }

    fun getLastDayOfMonth(calendar: Calendar): Calendar {
        return Calendar.getInstance().apply {
            set(Calendar.YEAR, calendar.get(Calendar.YEAR))
            set(Calendar.MONTH, calendar.get(Calendar.MONTH))
            set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        }
    }

    fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
    }

}