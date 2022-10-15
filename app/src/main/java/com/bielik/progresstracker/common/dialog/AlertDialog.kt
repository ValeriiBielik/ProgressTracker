package com.bielik.progresstracker.common.dialog

import android.os.Bundle
import android.view.View
import com.bielik.progresstracker.R
import com.bielik.progresstracker.base.BaseBindingDialogFragment
import com.bielik.progresstracker.databinding.LayoutDialogBinding
import com.bielik.progresstracker.utils.extensions.onClick

private const val KEY_DIALOG_TYPE: String = "key_dialog_type"

class AlertDialog : BaseBindingDialogFragment<LayoutDialogBinding>() {

    private val type: DialogType? by lazy { arguments?.getParcelable(KEY_DIALOG_TYPE) as? DialogType }
    private var negativeClick = {}
    private var positiveClick = {}
    private var dialogClick = {}

    override fun getContentViewId(): Int = R.layout.layout_dialog

    override fun setupUI() = withBinding {
        buttonNegativeButton.onClick {
            negativeClick()
            dialog?.dismiss()
        }
        buttonPositiveButton.onClick {
            positiveClick()
            dialog?.dismiss()
        }
        when (val dialogType = type) {
            else -> {}
        }
    }

    fun setOnPositiveClick(onPositiveClick: () -> Unit = {}) {
        positiveClick = onPositiveClick
    }

    fun setOnNegativeClick(onNegativeClick: () -> Unit = {}) {
        negativeClick = onNegativeClick
    }

    fun setOnDialogClick(onDialogClick: () -> Unit = {}) {
        dialogClick = onDialogClick
    }

    override fun bindToBinding(view: View) = LayoutDialogBinding.bind(view)

    companion object {
        val TAG: String = AlertDialog::class.java.name

        fun newInstance(type: DialogType) = AlertDialog().apply {
            arguments = Bundle().apply {
                putParcelable(KEY_DIALOG_TYPE, type)
            }
        }
    }
}
