package com.bielik.progresstracker.model

enum class TicketType {
    TASK,
    REPEATABLE_TASK,
    PROGRESS_TRACKED_TASK
}

fun parseTicketType(type: Int): TicketType {
    return when (type) {
        0 -> TicketType.TASK
        1 -> TicketType.REPEATABLE_TASK
        2 -> TicketType.PROGRESS_TRACKED_TASK
        else -> throw java.lang.ClassCastException("Illegal TicketType index")
    }
}