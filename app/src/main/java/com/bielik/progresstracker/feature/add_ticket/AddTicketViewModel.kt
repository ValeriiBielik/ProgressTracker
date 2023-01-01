package com.bielik.progresstracker.feature.add_ticket

import androidx.lifecycle.viewModelScope
import com.bielik.progresstracker.R
import com.bielik.progresstracker.base.BaseViewModel
import com.bielik.progresstracker.common.StringResource
import com.bielik.progresstracker.database.dao.TicketsDao
import com.bielik.progresstracker.model.RepeatOption
import com.bielik.progresstracker.model.TicketModel
import com.bielik.progresstracker.model.TicketType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class AddTicketViewModel @Inject constructor(
    private val ticketsDao: TicketsDao
) : BaseViewModel() {

    private var selectedTicketType: TicketType? = null
    private var repeatOption: RepeatOption = RepeatOption.ONCE

    val errorFlow: SharedFlow<StringResource> = MutableSharedFlow()
    val successFlow: SharedFlow<Unit> = MutableSharedFlow()
    val onRepeatOptionClickEvent: SharedFlow<RepeatOption> = MutableSharedFlow()

    fun onTicketTypeSelected(type: TicketType) {
        selectedTicketType = type
    }

    // todo if should work for different time zones -> convert to gmt date and then convert to necessary timezone
    fun onSaveClick(name: String?, description: String?) {
        if (!isDataValid(name)) return

        viewModelScope.launch {
            val ticket = TicketModel(
                name = name!!,
                description = description,
                isDone = false,
                ticketTypeIndex = selectedTicketType!!.ordinal,
                timestamp = Instant.now().epochSecond
            )
            ticketsDao.insertTicket(ticket)
            successFlow.emit(Unit)
        }
    }

    private fun isDataValid(name: String?): Boolean {
        val error = when {
            name.isNullOrBlank() -> StringResource(R.string.error_title_is_empty)
            else -> null
        }

        error?.let {
            errorFlow.emitViewModelScope(it)
            return false
        } ?: run {
            return true
        }
    }

    fun onRepeatOptionSelected(option: RepeatOption) {
        repeatOption = option
    }

    fun onRepeatOptionClick() {
        onRepeatOptionClickEvent.emitViewModelScope(repeatOption)
    }
}