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
import com.bielik.progresstracker.utils.extensions.setVisibleOrGone
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
        viewModel.errorFlow.observeWhenResumed { showMessage(it.getString(requireContext())) }
        viewModel.successFlow.observeWhenResumed { onBackPressed() }
        viewModel.onRepeatOptionClickEvent.observeWhenResumed {
            findNavController().navigate(AddTicketFragmentDirections.openSelectRepeatOptionDialog(it))
        }
        viewModel.onTypeOptionClickEvent.observeWhenResumed {
            findNavController().navigate(AddTicketFragmentDirections.openSelectTicketTypeDialog(it))
        }
        getNavigationResult<RepeatOption>(R.id.navigationAddTicket, KEY_REPEAT_OPTION, onResult = { onRepeatOptionSelected(it) })
        getNavigationResult<List<Day>>(R.id.navigationAddTicket, KEY_SELECTED_DAYS, onResult = { onDaysSelected(it) })
        getNavigationResult<TicketType>(R.id.navigationAddTicket, KEY_TICKET_TYPE, onResult = { onTicketTypeSelected(it) })
    }

    private fun onRepeatOptionSelected(repeatOption: RepeatOption) = withBinding {
        if (repeatOption == RepeatOption.SELECT_DAYS) {
            findNavController().navigate(AddTicketFragmentDirections.openSelectDaysDialog(viewModel.getSelectedDays().toTypedArray()))
        } else {
            viewModel.onRepeatOptionSelected(repeatOption)
            tvRepeatOption.text = when (repeatOption) {
                RepeatOption.ONCE -> getString(R.string.repeat_option_once)
                RepeatOption.EVERYDAY -> getString(R.string.repeat_option_everyday)
                RepeatOption.ON_WORK_DAYS -> getString(R.string.abbreviation_mon_fri)
                else -> throw IllegalStateException()
            }
        }
    }

    private fun onDaysSelected(days: List<Day>) = withBinding {
        viewModel.onDaysSelected(days)
        tvRepeatOption.text = getDaysAbbreviateString(requireContext(), days)
    }

    private fun onTicketTypeSelected(ticketType: TicketType) = withBinding {
        viewModel.onTicketTypeSelected(ticketType)
        clRepeat.setVisibleOrGone(ticketType == TicketType.TASK)
        when (ticketType) {
            TicketType.TASK -> optionViewType.setValue(getString(R.string.ticket_type_task))
            TicketType.PROGRESS_TRACKED_TASK -> optionViewType.setValue(getString(R.string.ticket_type_progress_task))
        }
    }

    override fun attachBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) =
        FragmentAddTicketBinding.inflate(inflater, container, attachToRoot)
}