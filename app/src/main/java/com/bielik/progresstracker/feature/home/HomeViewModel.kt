package com.bielik.progresstracker.feature.home

import androidx.lifecycle.viewModelScope
import com.bielik.progresstracker.base.BaseViewModel
import com.bielik.progresstracker.database.dao.TicketsDao
import com.bielik.progresstracker.model.TicketModel
import com.bielik.progresstracker.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    preferencesRepository: PreferencesRepository,
    private val ticketsDao: TicketsDao
) : BaseViewModel() {

    val setupFlow: SharedFlow<String> = MutableSharedFlow(replay = 1)
    val ticketsFlow: SharedFlow<List<TicketModel>> = MutableSharedFlow(replay = 1)

    init {
        setupFlow.emitViewModelScope(preferencesRepository.userName)
    }

    fun fetchTickets() {
        viewModelScope.launch { ticketsFlow.emit(ticketsDao.getTickets()) }
    }
}