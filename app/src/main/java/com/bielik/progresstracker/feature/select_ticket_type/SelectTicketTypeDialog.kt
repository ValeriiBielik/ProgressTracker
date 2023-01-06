package com.bielik.progresstracker.feature.select_ticket_type

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bielik.progresstracker.base.BaseBottomSheetDialogFragment
import com.bielik.progresstracker.databinding.DialogSelectTicketTypeBinding
import com.bielik.progresstracker.model.common.TicketType
import com.bielik.progresstracker.utils.extensions.onClick
import com.bielik.progresstracker.utils.extensions.setNavigationResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SelectTicketTypeDialog : BaseBottomSheetDialogFragment<DialogSelectTicketTypeBinding, SelectTicketTypeViewModel>() {

    override val viewModel by viewModels<SelectTicketTypeViewModel>()
    private val safeArgs by navArgs<SelectTicketTypeDialogArgs>()

    override fun setupUI() = withBinding {
        implementListeners()
        displaySelection(safeArgs.ticketType)
    }

    private fun implementListeners() = withBinding {
        sivOptionTask.onClick { onOptionSelected(TicketType.TASK) }
        sivOptionProgressTask.onClick { onOptionSelected(TicketType.PROGRESS_TRACKED_TASK) }
    }

    private fun onOptionSelected(ticketType: TicketType) = withBinding {
        displaySelection(ticketType)
        lifecycleScope.launch {
            delay(100L)
            setNavigationResult(KEY_TICKET_TYPE, ticketType)
            findNavController().popBackStack()
        }
    }

    private fun displaySelection(ticketType: TicketType) = withBinding {
        when (ticketType) {
            TicketType.TASK -> {
                sivOptionProgressTask.removeSelection()
                sivOptionTask.setSelected()
            }
            TicketType.PROGRESS_TRACKED_TASK -> {
                sivOptionTask.removeSelection()
                sivOptionProgressTask.setSelected()
            }
        }
    }

    override fun attachBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) =
        DialogSelectTicketTypeBinding.inflate(inflater, container, attachToRoot)

    companion object {
        const val KEY_TICKET_TYPE = "key_ticket_type"
    }
}