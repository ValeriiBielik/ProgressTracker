package com.bielik.progresstracker.feature.ticket_details

import androidx.lifecycle.viewModelScope
import com.bielik.progresstracker.base.BaseViewModel
import com.bielik.progresstracker.database.dao.TicketsDao
import com.bielik.progresstracker.model.database.TicketModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketDetailsViewModel @Inject constructor(
    private val ticketsDao: TicketsDao
) : BaseViewModel() {

    val ticketFlow: SharedFlow<TicketModel> = MutableSharedFlow(replay = 1)
    val ticketUpdatedFlow: SharedFlow<Unit> = MutableSharedFlow()

    fun init(ticketId: Long) {
        viewModelScope.launch {
            ticketFlow.emit(ticketsDao.getTicketById(ticketId))
        }
    }

    fun onMarkAsDoneClick() {
        viewModelScope.launch {
            val ticket = ticketFlow.replayCache.firstOrNull()
            ticket?.let {
                it.isDone = true
                ticketsDao.updateTicket(it)
                ticketUpdatedFlow.emit(Unit)
            }
        }
    }

}
