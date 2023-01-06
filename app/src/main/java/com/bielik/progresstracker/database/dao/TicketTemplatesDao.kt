package com.bielik.progresstracker.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bielik.progresstracker.model.database.TicketTemplateModel

@Dao
interface TicketTemplatesDao {

    @Query("SELECT * FROM templates")
    suspend fun getTemplates(): List<TicketTemplateModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplate(templateModel: TicketTemplateModel): Long
}