package com.bielik.progresstracker.feature.persistence_task_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bielik.progresstracker.base.BaseBindingFragment
import com.bielik.progresstracker.databinding.FragmentPersistenceTaskListBinding
import com.bielik.progresstracker.feature.persistence_task_list.adapter.PersistenceTasksAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersistenceTaskListFragment : BaseBindingFragment<FragmentPersistenceTaskListBinding, PersistenceTaskListViewModel>() {

    override val viewModel by viewModels<PersistenceTaskListViewModel>()

    private val adapter by lazy { PersistenceTasksAdapter() }

    override fun initUI() {
        setupRecyclerView()
        subscribe()
        viewModel.fetchPersistenceTasks()
    }

    private fun setupRecyclerView() = withBinding {
        rvPersistenceTickets.adapter = adapter
    }

    private fun subscribe() {
        viewModel.persistenceTasksFlow.observeInLifecycle { adapter.clearAndSetData(it) }
    }

    override fun attachBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) =
        FragmentPersistenceTaskListBinding.inflate(inflater, container, attachToRoot)
}