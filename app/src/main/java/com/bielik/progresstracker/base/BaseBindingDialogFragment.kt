package com.bielik.progresstracker.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.bielik.progresstracker.common.dialog.AlertDialog
import com.bielik.progresstracker.common.dialog.DialogType

abstract class BaseBindingDialogFragment<VB : ViewBinding> : DialogFragment() {

    protected var binding: VB? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return getInflatedView(inflater, container)
    }

    abstract fun bindToBinding(view: View): VB

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        dialog?.setTitle(null)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        activity?.let {
            return object : Dialog(it, theme) {
                override fun onBackPressed() {
                    if (isCanCancel())
                        dismiss()
                }
            }.apply<Dialog> {
                setCancelable(isCanCancel())
                window?.requestFeature(Window.FEATURE_NO_TITLE)
                window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }

        return dialog ?: Dialog(requireContext(), theme)
    }

    protected open fun isCanCancel() = true

    protected abstract fun setupUI()

    protected fun showDialog(
        type: DialogType,
        onNegativeClick: () -> Unit = {},
        onPositiveClick: () -> Unit = {},
        onDialogClick: () -> Unit = {},
    ) {
        AlertDialog.newInstance(type).apply {
            setOnNegativeClick(onNegativeClick)
            setOnPositiveClick(onPositiveClick)
            setOnDialogClick(onDialogClick)
        }.show(childFragmentManager, AlertDialog.TAG)
    }

    fun withBinding(block: (VB.() -> Unit)) {
        binding?.apply {
            block.invoke(this)
        }
            ?: run {
                error("Accessing binding outside of lifecycle: ${this::class.java.simpleName}")
            }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun getInflatedView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View {
        val view = View.inflate(inflater.context, getContentViewId(), container?.parent as ViewGroup?)
        binding = bindToBinding(view)
        return binding?.root
            ?: error("Provide binding")
    }

    abstract fun getContentViewId(): Int
}
