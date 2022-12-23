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
    var timestamp: Long? = null
) {
    val ticketType get() = parseTicketType(ticketTypeIndex)
}
