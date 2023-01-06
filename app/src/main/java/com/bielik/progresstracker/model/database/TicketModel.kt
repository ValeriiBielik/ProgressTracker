package com.bielik.progresstracker.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bielik.progresstracker.model.common.parseTicketType

@Entity(tableName = "tickets")
data class TicketModel(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    var name: String,
    var description: String?,
    var isDone: Boolean,
    var ticketTypeIndex: Int,
    var timestamp: Long,
    var progress: Int?,
    var templateId: Long?
) {
    val ticketType get() = parseTicketType(ticketTypeIndex)
}
