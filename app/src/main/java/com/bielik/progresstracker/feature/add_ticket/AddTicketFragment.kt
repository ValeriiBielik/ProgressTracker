package com.bielik.progresstracker.feature.add_ticket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bielik.progresstracker.R
import com.bielik.progresstracker.base.BaseBindingFragment
import com.bielik.progresstracker.databinding.FragmentAddTicketBinding
import com.bielik.progresstracker.feature.select_days.SelectDaysDialog.Companion.KEY_SELECTED_DAYS
import com.bielik.progresstracker.feature.select_repeat_option.SelectRepeatOptionDialog.Companion.KEY_REPEAT_OPTION
import com.bielik.progresstracker.feature.select_ticket_type.SelectTicketTypeDialog.Companion.KEY_TICKET_TYPE
import com.bielik.progresstracker.model.common.Day
import com.bielik.progresstracker.model.common.RepeatOption
import com.bielik.progresstracker.model.common.TicketType
import com.bielik.progresstracker.utils.extensions.getNavigationResult
import com.bielik.progresstracker.utils.extensions.onClick
import com.bielik.progresstracker.utils.getDaysAbbreviateString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTicketFragment : BaseBindingFragment<FragmentAddTicketBinding, AddTicketViewModel>() {

    override val viewModel: AddTicketViewModel by viewModels()

    override fun initUI() {
        implementListeners()
        subscribe()
    }

    private fun implementListeners() = withBinding {
        headerView.apply {
            setupView(getString(R.string.label_create_ticket), showBackIcon = true, showSaveIcon = true)
            setOnBackClickListener { onBackPressed() }
            setOnSaveClickListener {
                viewModel.onSaveClick(
                    name = etTicketName.text?.toString(),
                    description = etTicketDescription.text?.toString()
                )
            }

        }
        clRepeat.onClick { viewModel.onRepeatOptionClick() }
        optionViewType.onClick { viewModel.onOptionViewTypeClick() }
    }

    private fun subscribe() {
        viewModel.uiEventFlow.observeWhenResumed { handleEvent(it) }
        getNavigationResult<RepeatOption>(R.id.navigationAddTicket, KEY_REPEAT_OPTION, onResult = { viewModel.onRepeatOptionSelected(it) })
        getNavigationResult<List<Day>>(R.id.navigationAddTicket, KEY_SELECTED_DAYS, onResult = { onDaysSelected(it) })
        getNavigationResult<TicketType>(R.id.navigationAddTicket, KEY_TICKET_TYPE, onResult = { onTicketTypeSelected(it) })
    }

    private fun handleEvent(event: UiEvent) {
        when (event) {
            is ErrorEvent -> showMessage(event.stringResource.getString(requireContext()))
            is OnRepeatOptionClickEvent -> openSelectRepeatOptionDialog(event.repeatOption)
            is OnRepeatOptionSelectedEvent -> onRepeatOptionSelected(event.repeatOption)
            is OnTypeOptionClickEvent -> openSelectTicketTypeDialog(event.ticketType)
            is OpenSelectDaysDialogEvent -> openSelectDaysDialog(event.days)
            SuccessEvent -> onBackPressed()
        }
    }

    private fun openSelectRepeatOptionDialog(repeatOption: RepeatOption) {
        findNavController().navigate(AddTicketFragmentDirections.openSelectRepeatOptionDialog(repeatOption))
    }

    private fun openSelectTicketTypeDialog(ticketType: TicketType) {
        findNavController().navigate(AddTicketFragmentDirections.openSelectTicketTypeDialog(ticketType))
    }

    private fun openSelectDaysDialog(days: List<Day>) {
        findNavController().navigate(AddTicketFragmentDirections.openSelectDaysDialog(days.toTypedArray()))
    }

    private fun onRepeatOptionSelected(repeatOption: RepeatOption) = withBinding {
        tvRepeatOption.text = when (repeatOption) {
            RepeatOption.ONCE -> getString(R.string.repeat_option_once)
            RepeatOption.EVERYDAY -> getString(R.string.repeat_option_everyday)
            RepeatOption.ON_WORK_DAYS -> getString(R.string.abbreviation_mon_fri)
            else -> throw IllegalStateException()
        }
    }

    private fun onDaysSelected(days: List<Day>) = withBinding {
        viewModel.onDaysSelected(days)
        tvRepeatOption.text = getDaysAbbreviateString(requireContext(), days)
    }

    private fun onTicketTypeSelected(ticketType: TicketType) = withBinding {
        viewModel.onTicketTypeSelected(ticketType)
        when (ticketType) {
            TicketType.TASK -> optionViewType.setValue(getString(R.string.ticket_type_task))
            TicketType.PROGRESS_TRACKED_TASK -> optionViewType.setValue(getString(R.string.ticket_type_progress_task))
        }
    }

    override fun attachBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) =
        FragmentAddTicketBinding.inflate(inflater, container, attachToRoot)
}