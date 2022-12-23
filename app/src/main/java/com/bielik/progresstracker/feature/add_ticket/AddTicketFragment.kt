package com.bielik.progresstracker.feature.add_ticket

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.bielik.progresstracker.R
import com.bielik.progresstracker.base.BaseBindingFragment
import com.bielik.progresstracker.databinding.FragmentAddTicketBinding
import com.bielik.progresstracker.model.TicketType
import com.bielik.progresstracker.model.parseTicketType
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
            llRepeatOn.setVisibleOrGone(position == TicketType.REPEATABLE_TASK.ordinal)
        }
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
    }

    override fun attachBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) =
        FragmentAddTicketBinding.inflate(inflater, container, attachToRoot)
}