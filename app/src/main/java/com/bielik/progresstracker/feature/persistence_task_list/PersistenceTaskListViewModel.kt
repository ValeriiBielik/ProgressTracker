package com.bielik.progresstracker.feature.persistence_task_list

import androidx.lifecycle.viewModelScope
import com.bielik.progresstracker.base.BaseViewModel
import com.bielik.progresstracker.database.dao.TicketTemplatesDao
import com.bielik.progresstracker.model.database.TicketTemplateModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersistenceTaskListViewModel @Inject constructor(
    private val ticketTemplatesDao: TicketTemplatesDao
) : BaseViewModel() {

    val persistenceTasksFlow: SharedFlow<List<TicketTemplateModel>> = MutableSharedFlow()

    fun fetchPersistenceTasks() {
        viewModelScope.launch {
            persistenceTasksFlow.emit(ticketTemplatesDao.getPersistenceTasks())
        }
    }
}