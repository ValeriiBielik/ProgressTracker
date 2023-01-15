package com.bielik.progresstracker.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bielik.progresstracker.common.ZONE_KIEV
import com.bielik.progresstracker.database.dao.TicketTemplatesDao
import com.bielik.progresstracker.database.dao.TicketsDao
import com.bielik.progresstracker.model.database.TicketModel
import com.bielik.progresstracker.model.database.TicketTemplateModel
import com.bielik.progresstracker.utils.getCalendarDataForTemplate
import com.bielik.progresstracker.utils.getDateTimeSeconds
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.ZoneId
import javax.inject.Inject

@AndroidEntryPoint
class TemplatesCheckTaskReceiver : BroadcastReceiver() {

    @Inject
    lateinit var ticketsDao: TicketsDao

    @Inject
    lateinit var ticketTemplatesDao: TicketTemplatesDao

    private val zoneId by lazy { ZoneId.of(ZONE_KIEV) }

    override fun onReceive(p0: Context?, p1: Intent?) {
        CoroutineScope(SupervisorJob()).launch {
            ticketTemplatesDao.getTemplates().forEach { template ->
                val calendarData = getCalendarDataForTemplate(template.repeatOn)
                template.id?.let { templateId ->
                    val tickets = ticketsDao.getTicketsByTemplateId(templateId)

                    calendarData.forEach { localDate ->
                        val startDate = getDateTimeSeconds(localDate, LocalTime.MIN, zoneId)
                        val endDate = getDateTimeSeconds(localDate, LocalTime.MAX, zoneId)
                        var isTicketExist = false
                        for (ticket in tickets) {
                            if (ticket.timestamp in startDate..endDate) {
                                isTicketExist = true
                                break
                            }
                        }
                        if (!isTicketExist) {
                            createTicket(template, startDate)
                        }
                    }
                }
            }
        }
    }

    private fun createTicket(template: TicketTemplateModel, startDate: Long) {
        CoroutineScope(SupervisorJob()).launch {
            ticketsDao.insertTicket(
                TicketModel(
                    name = template.name,
                    description = template.description,
                    isDone = false,
                    ticketTypeIndex = template.ticketTypeIndex,
                    timestamp = startDate,
                    progress = null,
                    templateId = template.id
                )
            )
        }
    }
}