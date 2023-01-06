package com.bielik.progresstracker.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bielik.progresstracker.database.dao.TicketTemplatesDao
import com.bielik.progresstracker.database.dao.TicketsDao
import com.bielik.progresstracker.model.database.TicketModel
import com.bielik.progresstracker.model.database.TicketTemplateModel
import com.bielik.progresstracker.utils.getCalendarDataForTemplate
import com.bielik.progresstracker.utils.getTimeOfDayEnd
import com.bielik.progresstracker.utils.getTimeOfDayStart
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@AndroidEntryPoint
class TemplatesCheckTaskReceiver : BroadcastReceiver() {

    @Inject
    lateinit var ticketsDao: TicketsDao

    @Inject
    lateinit var ticketTemplatesDao: TicketTemplatesDao

    override fun onReceive(p0: Context?, p1: Intent?) {
        CoroutineScope(SupervisorJob()).launch {

            ticketTemplatesDao.getTemplates().forEach { template ->
                val calendarData = getCalendarDataForTemplate(template.repeatOn)
                template.id?.let { templateId ->
                    val tickets = ticketsDao.getTicketsByTemplateId(templateId)

                    calendarData.forEach { time ->
                        val startOfDayDate = getTimeOfDayStart(time)
                        val endOfDayDate = getTimeOfDayEnd(time)
                        var isTicketExist = false
                        for (ticket in tickets) {
                            if (ticket.timestamp * 1000 in startOfDayDate..endOfDayDate) {
                                isTicketExist = true
                                break
                            }
                        }
                        if (!isTicketExist) {
                            createTicket(template)
                        }
                    }
                }
            }
        }
    }

    private fun createTicket(template: TicketTemplateModel) {
        CoroutineScope(SupervisorJob()).launch {
            ticketsDao.insertTicket(
                TicketModel(
                    name = "${template.name} GENERATED",
                    description = template.description,
                    isDone = false,
                    ticketTypeIndex = template.ticketTypeIndex,
                    timestamp = Instant.now().epochSecond,
                    progress = null,
                    templateId = template.id
                )
            )
        }
    }
}