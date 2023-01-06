package com.bielik.progresstracker.feature.add_ticket

import com.bielik.progresstracker.common.StringResource
import com.bielik.progresstracker.model.common.Day
import com.bielik.progresstracker.model.common.RepeatOption
import com.bielik.progresstracker.model.common.TicketType

sealed class UiEvent

data class OnRepeatOptionClickEvent(val repeatOption: RepeatOption) : UiEvent()
data class OnTypeOptionClickEvent(val ticketType: TicketType) : UiEvent()
data class OpenSelectDaysDialogEvent(val days: List<Day>) : UiEvent()
data class OnRepeatOptionSelectedEvent(val repeatOption: RepeatOption) : UiEvent()
data class ErrorEvent(val stringResource: StringResource) : UiEvent()
object SuccessEvent : UiEvent()