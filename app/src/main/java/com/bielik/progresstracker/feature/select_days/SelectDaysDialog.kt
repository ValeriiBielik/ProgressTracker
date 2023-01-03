package com.bielik.progresstracker.feature.select_days

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bielik.progresstracker.base.BaseBottomSheetDialogFragment
import com.bielik.progresstracker.databinding.DialogSelectDaysBinding
import com.bielik.progresstracker.utils.extensions.onClick
import com.bielik.progresstracker.utils.extensions.setNavigationResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectDaysDialog : BaseBottomSheetDialogFragment<DialogSelectDaysBinding, SelectDaysViewModel>() {

    override val viewModel by viewModels<SelectDaysViewModel>()
    private val safeArgs by navArgs<SelectDaysDialogArgs>()

    override fun setupUI() {
        implementListeners()
        binding?.selectDaysView?.setupView(safeArgs.selectedDays)
    }

    private fun implementListeners() = withBinding {
        btnCancel.onClick { findNavController().popBackStack() }
        btnOk.onClick {
            setNavigationResult(KEY_SELECTED_DAYS, selectDaysView.getSelectedDays())
            findNavController().popBackStack()
        }
    }

    override fun attachBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) =
        DialogSelectDaysBinding.inflate(inflater, container, attachToRoot)

    companion object {
        const val KEY_SELECTED_DAYS = "key_selected_days"
    }
}