package com.bielik.progresstracker.feature.progress_task_list

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
class ProgressTaskListViewModel @Inject constructor(
    private val ticketTemplatesDao: TicketTemplatesDao
) : BaseViewModel() {

    val progressTasksFlow: SharedFlow<List<TicketTemplateModel>> = MutableSharedFlow()

    fun fetchProgressTasks() {
        viewModelScope.launch {
            progressTasksFlow.emit(ticketTemplatesDao.getProgressTasks())
        }
    }
}