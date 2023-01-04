package com.bielik.progresstracker.feature.select_repeat_option

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bielik.progresstracker.base.BaseBottomSheetDialogFragment
import com.bielik.progresstracker.databinding.DialogSelectRepeatOptionBinding
import com.bielik.progresstracker.feature.select_repeat_option.model.RepeatOptionsUiData
import com.bielik.progresstracker.model.common.RepeatOption
import com.bielik.progresstracker.utils.extensions.onClick
import com.bielik.progresstracker.utils.extensions.setNavigationResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectRepeatOptionDialog : BaseBottomSheetDialogFragment<DialogSelectRepeatOptionBinding, SelectRepeatOptionViewModel>() {

    override val viewModel by viewModels<SelectRepeatOptionViewModel>()
    private val safeArgs: SelectRepeatOptionDialogArgs by navArgs()

    override fun setupUI() = withBinding {
        implementListeners()
        subscribe()
        viewModel.init(safeArgs.repeatOption)
    }

    private fun implementListeners() = withBinding {
        sivOptionOnce.onClick { viewModel.onOptionSelected(RepeatOption.ONCE) }
        sivOptionEveryday.onClick { viewModel.onOptionSelected(RepeatOption.EVERYDAY) }
        sivOptionOnWorkDays.onClick { viewModel.onOptionSelected(RepeatOption.ON_WORK_DAYS) }
        sivOptionSelectDays.onClick { viewModel.onOptionSelected(RepeatOption.SELECT_DAYS) }
    }

    private fun subscribe() {
        viewModel.selectedOptionFlow.observeWhenResumed { onOptionSelected(it) }
    }

    private fun onOptionSelected(optionsData: RepeatOptionsUiData) = withBinding {
        optionsData.previousOption?.let {
            when (it) {
                RepeatOption.ONCE -> sivOptionOnce.removeSelection()
                RepeatOption.EVERYDAY -> sivOptionEveryday.removeSelection()
                RepeatOption.ON_WORK_DAYS -> sivOptionOnWorkDays.removeSelection()
                RepeatOption.SELECT_DAYS -> sivOptionSelectDays.removeSelection()
            }
        }
        when (optionsData.selectedOption) {
            RepeatOption.ONCE -> sivOptionOnce.setSelected()
            RepeatOption.EVERYDAY -> sivOptionEveryday.setSelected()
            RepeatOption.ON_WORK_DAYS -> sivOptionOnWorkDays.setSelected()
            RepeatOption.SELECT_DAYS -> sivOptionSelectDays.setSelected()
        }
        if (!optionsData.isInitialData) {
            lifecycleScope.launch {
                delay(100L)
                setNavigationResult(KEY_REPEAT_OPTION, optionsData.selectedOption)
                findNavController().popBackStack()
            }
        }
    }

    override fun attachBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) =
        DialogSelectRepeatOptionBinding.inflate(inflater, container, attachToRoot)

    companion object {
        const val KEY_REPEAT_OPTION = "key_repeat_option"
    }
}