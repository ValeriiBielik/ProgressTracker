package com.bielik.progresstracker.model.common

enum class TicketType {
    TASK,
    PROGRESS_TRACKED_TASK
}

fun parseTicketType(type: Int): TicketType {
    return when (type) {
        0 -> TicketType.TASK
        1 -> TicketType.PROGRESS_TRACKED_TASK
        else -> throw java.lang.ClassCastException("Illegal TicketType index")
    }
}