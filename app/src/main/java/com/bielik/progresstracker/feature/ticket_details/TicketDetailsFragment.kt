package com.bielik.progresstracker.feature.ticket_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bielik.progresstracker.base.BaseBindingFragment
import com.bielik.progresstracker.databinding.FragmentTicketDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketDetailsFragment : BaseBindingFragment<FragmentTicketDetailsBinding, TicketDetailsViewModel>() {

    override val viewModel by viewModels<TicketDetailsViewModel>()

    override fun initUI() {

    }

    override fun attachBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) =
        FragmentTicketDetailsBinding.inflate(inflater, container, attachToRoot)
}