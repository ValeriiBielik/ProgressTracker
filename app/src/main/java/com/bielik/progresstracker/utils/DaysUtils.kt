package com.bielik.progresstracker.utils

import android.content.Context
import com.bielik.progresstracker.R
import com.bielik.progresstracker.model.Day

fun getDaysAbbreviateString(context: Context, days: List<Day>): String {
    val daysStringBuilder = StringBuilder()

    if (days.size == 7) return context.getString(R.string.abbreviation_mon_sun)
    if (days.size == 5 && !days.contains(Day.SATURDAY) && !days.contains(Day.SUNDAY))
        return context.getString(R.string.abbreviation_mon_fri)

    days.forEachIndexed { index, day ->
        if (index > 0) {
            daysStringBuilder.append(context.getString(R.string.comma))
        }
        daysStringBuilder.append(day.getAbbreviateString(context))
    }
    return daysStringBuilder.toString()
}

fun Day.getAbbreviateString(context: Context): String {
    return when (this) {
        Day.MONDAY -> context.getString(R.string.abbreviation_monday)
        Day.TUESDAY -> context.getString(R.string.abbreviation_tuesday)
        Day.WEDNESDAY -> context.getString(R.string.abbreviation_wednesday)
        Day.THURSDAY -> context.getString(R.string.abbreviation_thursday)
        Day.FRIDAY -> context.getString(R.string.abbreviation_friday)
        Day.SATURDAY -> context.getString(R.string.abbreviation_saturday)
        Day.SUNDAY -> context.getString(R.string.abbreviation_sunday)
    }
}
