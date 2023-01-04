package com.bielik.progresstracker.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.bielik.progresstracker.model.database.TicketTemplateModel

@Dao
interface TicketTemplatesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplate(templateModel: TicketTemplateModel) : Long
}