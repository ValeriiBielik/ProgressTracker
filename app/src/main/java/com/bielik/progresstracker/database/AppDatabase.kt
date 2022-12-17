package com.bielik.progresstracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bielik.progresstracker.database.dao.TicketsDao
import com.bielik.progresstracker.model.TicketModel

@Database(entities = [TicketModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ticketsDao(): TicketsDao
}