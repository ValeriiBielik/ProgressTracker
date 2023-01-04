package com.bielik.progresstracker.database.dao

import androidx.room.*
import com.bielik.progresstracker.model.database.TicketModel

@Dao
interface TicketsDao {

    @Query("SELECT * FROM tickets where tickets.id == :id")
    suspend fun getTicketById(id: Long): TicketModel

    @Query("SELECT * FROM tickets")
    suspend fun getTickets(): List<TicketModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicket(ticketModel: TicketModel)

    @Update
    suspend fun updateTicket(ticketModel: TicketModel)

    @Delete
    suspend fun deleteTicket(ticketModel: TicketModel): Int
}