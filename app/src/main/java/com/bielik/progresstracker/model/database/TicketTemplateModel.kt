package com.bielik.progresstracker.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bielik.progresstracker.model.common.Day

@Entity(tableName = "templates")
data class TicketTemplateModel(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    var name: String,
    var description: String?,
    var ticketTypeIndex: Int,
    var repeatOn: List<Day>
)