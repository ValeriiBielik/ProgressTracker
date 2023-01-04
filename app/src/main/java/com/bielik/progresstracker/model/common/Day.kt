package com.bielik.progresstracker.model.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Day(val id: Int) : Parcelable {
    SUNDAY(1),
    MONDAY(2),
    TUESDAY(3),
    WEDNESDAY(4),
    THURSDAY(5),
    FRIDAY(6),
    SATURDAY(7);

    override fun toString(): String {
        return id.toString()
    }
}

fun getWorkDays() = listOf(Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.THURSDAY, Day.FRIDAY)
fun getAllDays() = listOf(Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.THURSDAY, Day.FRIDAY, Day.SATURDAY, Day.SUNDAY)