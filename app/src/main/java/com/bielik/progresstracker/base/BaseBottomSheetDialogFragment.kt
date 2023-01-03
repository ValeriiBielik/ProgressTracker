package com.bielik.progresstracker.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

abstract class BaseBottomSheetDialogFragment<VB : ViewBinding, VM : BaseViewModel> : BottomSheetDialogFragment() {

    protected var binding: VB? = null
    abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = attachBinding(inflater, container, false)
        return binding?.root ?: error("Provide binding")
    }

    abstract fun attachBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): VB

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        dialog?.setTitle(null)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            val bottomSheet = dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                BottomSheetBehavior.from(it).apply {
                    skipCollapsed = false
                    state = BottomSheetBehavior.STATE_EXPANDED
                    isFitToContents = true
                }
            }
        }
        return dialog
    }

    protected abstract fun setupUI()

    fun withBinding(block: (VB.() -> Unit)) {
        binding?.apply {
            block.invoke(this)
        } ?: run {
            error("Accessing binding outside of lifecycle: ${this::class.java.simpleName}")
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    protected inline fun <T> Flow<T>.observeWhenResumed(crossinline observer: suspend (T) -> Unit) {
        lifecycleScope.launchWhenResumed {
            observeInLifecycle(observer)
        }
    }

    protected inline fun <T> Flow<T>.observeInLifecycle(crossinline observer: suspend (T) -> Unit) {
        this.onEach {
            observer(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    protected inline fun <T> Flow<T>.dispatchIOLifecycle(crossinline observer: suspend (T) -> Unit) {
        lifecycleScope.launch(Dispatchers.IO) {
            onEach {
                observer(it)
            }.launchIn(this)
        }
    }
}