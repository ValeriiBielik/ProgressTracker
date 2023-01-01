package com.bielik.progresstracker.feature.add_ticket

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bielik.progresstracker.R
import com.bielik.progresstracker.base.BaseBindingFragment
import com.bielik.progresstracker.databinding.FragmentAddTicketBinding
import com.bielik.progresstracker.feature.select_repeat_option.SelectRepeatOptionDialog.Companion.KEY_REPEAT_OPTION
import com.bielik.progresstracker.model.RepeatOption
import com.bielik.progresstracker.model.TicketType
import com.bielik.progresstracker.model.parseTicketType
import com.bielik.progresstracker.utils.extensions.getNavigationResult
import com.bielik.progresstracker.utils.extensions.onClick
import com.bielik.progresstracker.utils.extensions.setVisibleOrGone
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTicketFragment : BaseBindingFragment<FragmentAddTicketBinding, AddTicketViewModel>() {

    override val viewModel: AddTicketViewModel by viewModels()

    override fun initUI() {
        implementListeners()
        setupContent()
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
        etTicketType.onItemClickListener = OnItemClickListener { _, _, position, _ ->
            viewModel.onTicketTypeSelected(parseTicketType(position))
            clRepeat.setVisibleOrGone(position == TicketType.REPEATABLE_TASK.ordinal)
        }
        clRepeat.onClick { viewModel.onRepeatOptionClick() }
    }

    private fun setupContent() = withBinding {
        etTicketType.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.array_ticket_types)
            )
        )
    }

    private fun subscribe() {
        viewModel.errorFlow.observeWhenResumed { showMessage(it.getString(requireContext())) }
        viewModel.successFlow.observeWhenResumed { onBackPressed() }
        viewModel.onRepeatOptionClickEvent.observeWhenResumed {
            findNavController().navigate(AddTicketFragmentDirections.openSelectDayOptionDialog(it))
        }
        getNavigationResult<RepeatOption>(R.id.navigationAddTicket, KEY_REPEAT_OPTION,
            onResult = {
                viewModel.onRepeatOptionSelected(it)
                binding?.tvRepeatOption?.text = when (it) {
                    RepeatOption.ONCE -> getString(R.string.repeat_option_once)
                    RepeatOption.EVERYDAY -> getString(R.string.repeat_option_everyday)
                    RepeatOption.ON_WORK_DAYS -> getString(R.string.repeat_option_on_work_days)
                    RepeatOption.SELECT_DAYS -> getString(R.string.repeat_option_select_days)
                }
            })
    }

    override fun attachBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) =
        FragmentAddTicketBinding.inflate(inflater, container, attachToRoot)
}