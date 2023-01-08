package com.bielik.progresstracker.feature.ticket_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bielik.progresstracker.base.BaseBindingFragment
import com.bielik.progresstracker.databinding.FragmentTicketDetailsBinding
import com.bielik.progresstracker.model.common.TicketType
import com.bielik.progresstracker.model.database.TicketModel
import com.bielik.progresstracker.utils.extensions.gone
import com.bielik.progresstracker.utils.extensions.onClick
import com.bielik.progresstracker.utils.extensions.setVisibleOrGone
import com.bielik.progresstracker.utils.extensions.textChanges
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketDetailsFragment : BaseBindingFragment<FragmentTicketDetailsBinding, TicketDetailsViewModel>() {

    override val viewModel by viewModels<TicketDetailsViewModel>()
    private val safeArgs: TicketDetailsFragmentArgs by navArgs()

    override fun initUI() {
        viewModel.init(safeArgs.ticketId)
        implementListeners()
        subscribe()
    }

    private fun implementListeners() = withBinding {
        headerView.setOnBackClickListener { onBackPressed() }
        btnMarkAsDone.onClick {
            viewModel.onMarkAsDoneClick(etProgress.text.toString())
        }
        etProgress.textChanges().observeWhenResumed {
            btnMarkAsDone.isEnabled = !it.isNullOrBlank()
        }
    }

    private fun subscribe() {
        viewModel.ticketFlow.observeWhenResumed { onTicketDataLoaded(it) }
        viewModel.ticketUpdatedFlow.observeWhenResumed { onTicketUpdated() }
    }

    private fun onTicketDataLoaded(ticket: TicketModel) = withBinding {
        headerView.setupView(ticket.name, showBackIcon = true)
        tvDescriptionLabel.setVisibleOrGone(!ticket.description.isNullOrBlank())
        tvDescription.setVisibleOrGone(!ticket.description.isNullOrBlank())
        ticket.description?.let { tvDescription.text = ticket.description }
        btnMarkAsDone.setVisibleOrGone(!ticket.isDone)
        tvProgressLabel.setVisibleOrGone(ticket.ticketType == TicketType.PROGRESS_TRACKED_TASK)
        tiLayoutProgress.setVisibleOrGone(ticket.ticketType == TicketType.PROGRESS_TRACKED_TASK)
        ticket.progress?.let { etProgress.setText(it.toString()) }
        etProgress.isEnabled = !ticket.isDone
        btnMarkAsDone.isEnabled = ticket.ticketType == TicketType.TASK
    }

    private fun onTicketUpdated() = withBinding {
        btnMarkAsDone.gone()
    }

    override fun attachBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) =
        FragmentTicketDetailsBinding.inflate(inflater, container, attachToRoot)
}