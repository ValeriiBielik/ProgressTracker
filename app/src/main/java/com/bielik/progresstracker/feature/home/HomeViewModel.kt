package com.bielik.progresstracker.feature.home

import androidx.lifecycle.viewModelScope
import com.bielik.progresstracker.base.BaseViewModel
import com.bielik.progresstracker.database.dao.TicketsDao
import com.bielik.progresstracker.feature.home.model.SetupUiData
import com.bielik.progresstracker.model.database.TicketModel
import com.bielik.progresstracker.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    preferencesRepository: PreferencesRepository,
    private val ticketsDao: TicketsDao
) : BaseViewModel() {

    val setupFlow: SharedFlow<SetupUiData> = MutableSharedFlow(replay = 1)
    val ticketsFlow: SharedFlow<List<TicketModel>> = MutableSharedFlow(replay = 1)
    val onDateSelectedEvent: SharedFlow<Pair<LocalDate, LocalDate>> = MutableSharedFlow()

    private var selectedDate: LocalDate = LocalDate.now()

    init {
        setupFlow.emitViewModelScope(SetupUiData(preferencesRepository.userName, selectedDate))
    }

    fun fetchTickets() {
        viewModelScope.launch { ticketsFlow.emit(ticketsDao.getTickets()) }
    }

    fun onCalendarDayClick(newDate: LocalDate) {
        val oldDate = selectedDate
        selectedDate = newDate
        onDateSelectedEvent.emitViewModelScope(oldDate to newDate)
    }
}