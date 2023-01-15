package com.bielik.progresstracker.utils

import com.bielik.progresstracker.model.common.Day
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Calendar

fun getCalendarDataForTemplate(repeatOnList: List<Day>): List<LocalDate> {
    val data = mutableListOf<LocalDate>()
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
            data.add(LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)))
        }
    }
    return data
}

fun getDateTimeSeconds(localDate: LocalDate, localTime: LocalTime, zoneId: ZoneId): Long {
    return ZonedDateTime.of(LocalDateTime.of(localDate, localTime), zoneId).toInstant().epochSecond
}