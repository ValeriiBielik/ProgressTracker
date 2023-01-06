package com.bielik.progresstracker.feature.add_ticket

import androidx.lifecycle.viewModelScope
import com.bielik.progresstracker.R
import com.bielik.progresstracker.base.BaseViewModel
import com.bielik.progresstracker.common.StringResource
import com.bielik.progresstracker.database.dao.TicketTemplatesDao
import com.bielik.progresstracker.database.dao.TicketsDao
import com.bielik.progresstracker.model.common.Day
import com.bielik.progresstracker.model.common.RepeatOption
import com.bielik.progresstracker.model.common.TicketType
import com.bielik.progresstracker.model.common.getAllDays
import com.bielik.progresstracker.model.common.getWorkDays
import com.bielik.progresstracker.model.database.TicketModel
import com.bielik.progresstracker.model.database.TicketTemplateModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AddTicketViewModel @Inject constructor(
    private val ticketsDao: TicketsDao,
    private val templatesDao: TicketTemplatesDao
) : BaseViewModel() {

    val uiEventFlow: SharedFlow<UiEvent> = MutableSharedFlow()

    private var ticketType: TicketType = TicketType.TASK
    private var repeatOption: RepeatOption = RepeatOption.ONCE
    private val selectedDays: MutableList<Day> = mutableListOf()

    fun onTicketTypeSelected(type: TicketType) {
        ticketType = type
        when (ticketType) {
            TicketType.TASK -> onRepeatOptionSelected(RepeatOption.ONCE)
            TicketType.PROGRESS_TRACKED_TASK -> onRepeatOptionSelected(RepeatOption.EVERYDAY)
        }
    }

    // todo if should work for different time zones -> convert to gmt date and then convert to necessary timezone
    fun onSaveClick(name: String?, description: String?) {
        if (!isDataValid(name)) return
        proceedTaskSave(name!!, description)
    }

    private fun proceedTaskSave(name: String, description: String?) {
        when (repeatOption) {
            RepeatOption.ONCE -> createAndSaveTicket(name, description, ticketType)
            else -> createAndSaveTemplate(name, description, ticketType)
        }
    }

    // todo use case
    private fun createAndSaveTicket(name: String, description: String?, ticketType: TicketType, templateId: Long? = null) {
        viewModelScope.launch {
            val ticket = TicketModel(
                name = name,
                description = description,
                isDone = false,
                ticketTypeIndex = ticketType.ordinal,
                timestamp = Instant.now().epochSecond,
                progress = null,
                templateId = templateId
            )
            ticketsDao.insertTicket(ticket)
            uiEventFlow.emit(SuccessEvent)
        }
    }

    private fun createAndSaveTemplate(name: String, description: String?, ticketType: TicketType) {
        viewModelScope.launch {
            val template = TicketTemplateModel(
                name = name,
                description = description,
                ticketTypeIndex = ticketType.ordinal,
                repeatOn = selectedDays
            )
            val id = templatesDao.insertTemplate(template)
            if (isForCurrentDay()) {
                createAndSaveTicket(name, description, ticketType, id)
                return@launch
            }
            uiEventFlow.emit(SuccessEvent)
        }
    }

    private fun isForCurrentDay(): Boolean {
        val currentDay = when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> Day.SUNDAY
            Calendar.MONDAY -> Day.MONDAY
            Calendar.TUESDAY -> Day.TUESDAY
            Calendar.WEDNESDAY -> Day.WEDNESDAY
            Calendar.THURSDAY -> Day.THURSDAY
            Calendar.FRIDAY -> Day.FRIDAY
            Calendar.SATURDAY -> Day.SATURDAY
            else -> throw IllegalStateException()
        }
        return selectedDays.contains(currentDay)
    }

    private fun isDataValid(name: String?): Boolean {
        val error = when {
            name.isNullOrBlank() -> StringResource(R.string.error_title_is_empty)
            else -> null
        }

        error?.let {
            uiEventFlow.emitViewModelScope(ErrorEvent(it))
            return false
        } ?: run {
            return true
        }
    }

    fun onRepeatOptionSelected(option: RepeatOption) {
        if (option == RepeatOption.SELECT_DAYS) {
            uiEventFlow.emitViewModelScope(OpenSelectDaysDialogEvent(selectedDays))
            return
        }

        repeatOption = option
        selectedDays.clear()
        when (option) {
            RepeatOption.ONCE -> {}
            RepeatOption.EVERYDAY -> selectedDays.addAll(getAllDays())
            RepeatOption.ON_WORK_DAYS -> selectedDays.addAll(getWorkDays())
            else -> throw IllegalStateException()
        }
        uiEventFlow.emitViewModelScope(OnRepeatOptionSelectedEvent(repeatOption))
    }

    fun onRepeatOptionClick() {
        uiEventFlow.emitViewModelScope(OnRepeatOptionClickEvent(repeatOption))
    }

    fun onOptionViewTypeClick() {
        uiEventFlow.emitViewModelScope(OnTypeOptionClickEvent(ticketType))
    }

    fun onDaysSelected(days: List<Day>) {
        repeatOption = RepeatOption.SELECT_DAYS
        selectedDays.apply {
            clear()
            addAll(days)
        }
    }
}