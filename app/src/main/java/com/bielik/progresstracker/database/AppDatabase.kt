package com.bielik.progresstracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bielik.progresstracker.database.dao.TicketTemplatesDao
import com.bielik.progresstracker.database.dao.TicketsDao
import com.bielik.progresstracker.model.database.TicketModel
import com.bielik.progresstracker.model.database.TicketTemplateModel

@Database(entities = [TicketModel::class, TicketTemplateModel::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ticketsDao(): TicketsDao
    abstract fun ticketTemplatesDao(): TicketTemplatesDao
}