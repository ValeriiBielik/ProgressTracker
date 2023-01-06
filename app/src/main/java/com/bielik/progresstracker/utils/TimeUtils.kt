package com.bielik.progresstracker.utils

import com.bielik.progresstracker.model.common.Day
import java.util.Calendar

fun getTimeOfDayStart(time: Long): Long {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = time
    calendar[Calendar.HOUR_OF_DAY] = 0
    calendar[Calendar.MINUTE] = 0
    calendar[Calendar.SECOND] = 0
    calendar[Calendar.MILLISECOND] = 0
    return calendar.timeInMillis
}

fun getTimeOfDayEnd(time: Long): Long {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = time
    calendar[Calendar.HOUR_OF_DAY] = 23
    calendar[Calendar.MINUTE] = 59
    calendar[Calendar.SECOND] = 59
    calendar[Calendar.MILLISECOND] = 999
    return calendar.timeInMillis
}

fun getCalendarDataForTemplate(repeatOnList: List<Day>): List<Long> {
    val data = mutableListOf<Long>()
    for (i in 0..6) {
        val calendar = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, i) }
        var shouldRepeat = false
        for (day in repeatOnList) {
            if (day.id == calendar.get(Calendar.DAY_OF_WEEK)) {
                shouldRepeat = true
                break
            }
        }
        if (shouldRepeat) {
            data.add(calendar.timeInMillis)
        }
    }
    return data
}