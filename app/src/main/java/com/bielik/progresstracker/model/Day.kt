package com.bielik.progresstracker.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Day : Parcelable {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

fun getWorkDays() = listOf(Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.THURSDAY, Day.FRIDAY)
fun getAllDays() = listOf(Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.THURSDAY, Day.FRIDAY, Day.SATURDAY, Day.SUNDAY)