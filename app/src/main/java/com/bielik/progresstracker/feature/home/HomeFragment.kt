package com.bielik.progresstracker.feature.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bielik.progresstracker.R
import com.bielik.progresstracker.base.BaseBindingFragment
import com.bielik.progresstracker.databinding.FragmentHomeBinding
import com.bielik.progresstracker.feature.home.adapter.TicketsAdapter
import com.bielik.progresstracker.utils.extensions.onClick
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseBindingFragment<FragmentHomeBinding, HomeViewModel>() {
    override val viewModel by viewModels<HomeViewModel>()

    private val adapter: TicketsAdapter by lazy { TicketsAdapter() }
    override fun initUI() {
        setupRecyclerView()
        implementListeners()
        subscribe()
    }

    private fun setupRecyclerView() = withBinding {
        rvTickets.adapter = adapter
    }

    private fun implementListeners() = withBinding {
        btnAdd.onClick { findNavController().navigate(HomeFragmentDirections.navigateToAddTicketFragment()) }
    }

    private fun subscribe() {
        viewModel.setupFlow.observeWhenResumed { setupUserName(it) }
        viewModel.ticketsFlow.observeWhenResumed { adapter.clearAndSetData(it) }
    }

    private fun setupUserName(userName: String) = withBinding {
        if (userName.isNotEmpty()) {
            headerView.setupView(getString(R.string.format_user_greeting, userName), false)
        }
    }

    override fun attachBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) =
        FragmentHomeBinding.inflate(inflater, container, false)
}