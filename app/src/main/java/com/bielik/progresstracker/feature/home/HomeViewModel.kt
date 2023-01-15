package com.bielik.progresstracker.feature.home

import androidx.lifecycle.viewModelScope
import com.bielik.progresstracker.base.BaseViewModel
import com.bielik.progresstracker.common.ZONE_KIEV
import com.bielik.progresstracker.database.dao.TicketsDao
import com.bielik.progresstracker.feature.home.model.SetupUiData
import com.bielik.progresstracker.model.database.TicketModel
import com.bielik.progresstracker.repository.PreferencesRepository
import com.bielik.progresstracker.utils.getDateTimeSeconds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
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
    private val zoneId by lazy { ZoneId.of(ZONE_KIEV) }

    init {
        setupFlow.emitViewModelScope(SetupUiData(preferencesRepository.userName, selectedDate))
    }

    fun fetchTickets() {
        val startDate = getDateTimeSeconds(selectedDate, LocalTime.MIN, zoneId)
        val endDate = getDateTimeSeconds(selectedDate, LocalTime.MAX, zoneId)
        viewModelScope.launch { ticketsFlow.emit(ticketsDao.getTicketsForDay(startDate, endDate)) }
    }

    fun onCalendarDayClick(newDate: LocalDate) {
        val oldDate = selectedDate
        selectedDate = newDate
        onDateSelectedEvent.emitViewModelScope(oldDate to newDate)
        fetchTickets()
    }
}