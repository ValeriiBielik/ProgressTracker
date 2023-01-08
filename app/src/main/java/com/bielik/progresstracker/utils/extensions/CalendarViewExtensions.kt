package com.bielik.progresstracker.utils.extensions

import com.kizitonwose.calendar.core.Week
import com.kizitonwose.calendar.core.yearMonth
import java.time.DayOfWeek
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

fun DayOfWeek.displayText(uppercase: Boolean = false): String {
    return getDisplayName(TextStyle.SHORT, Locale.getDefault()).let { value ->
        if (uppercase) value.uppercase(Locale.getDefault()) else value
    }
}

fun YearMonth.displayText(short: Boolean = false): String {
    return this.month.displayText(short = short)
}

fun Month.displayText(short: Boolean = true): String {
    val style = if (short) TextStyle.SHORT else TextStyle.FULL
    return getDisplayName(style, Locale.getDefault())
}

fun getWeekPageTitle(week: Week): String {
    val firstDate = week.days.first().date
    return firstDate.yearMonth.displayText()
}