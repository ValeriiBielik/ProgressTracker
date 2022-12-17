package com.bielik.progresstracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tickets")
data class TicketModel(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    var name: String,
    var description: String?,
    var isDone: Boolean,
    var ticketTypeIndex: Int,
    var hashNumber: Int?, // number supposed to be used when we need to get all repeatable tasks.
    var timestamp: Long,
    ) {
    val ticketType get() = parseTicketType(ticketTypeIndex)
}
