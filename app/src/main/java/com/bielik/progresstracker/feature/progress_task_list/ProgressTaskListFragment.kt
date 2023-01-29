package com.bielik.progresstracker.feature.progress_task_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bielik.progresstracker.base.BaseBindingFragment
import com.bielik.progresstracker.databinding.FragmentProgressTaskListBinding
import com.bielik.progresstracker.feature.progress_task_list.adapter.ProgressTasksAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProgressTaskListFragment : BaseBindingFragment<FragmentProgressTaskListBinding, ProgressTaskListViewModel>() {

    override val viewModel by viewModels<ProgressTaskListViewModel>()

    private val adapter by lazy { ProgressTasksAdapter() }

    override fun initUI() {
        setupRecyclerView()
        subscribe()
        viewModel.fetchProgressTasks()
    }

    private fun setupRecyclerView() = withBinding {
        rvProgressTickets.adapter = adapter
    }

    private fun subscribe() {
        viewModel.progressTasksFlow.observeInLifecycle { adapter.clearAndSetData(it) }
    }

    override fun attachBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) =
        FragmentProgressTaskListBinding.inflate(inflater, container, attachToRoot)
}