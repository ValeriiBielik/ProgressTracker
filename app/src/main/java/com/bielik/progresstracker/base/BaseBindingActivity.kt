package com.bielik.progresstracker.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.bielik.progresstracker.common.StringResource
import com.bielik.progresstracker.common.dialog.AlertDialog
import com.bielik.progresstracker.common.dialog.DialogType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseBindingActivity<VB : ViewBinding, VM : BaseViewModel> : AppCompatActivity() {

    abstract val viewModel: VM
    protected var binding: VB? = null

    abstract fun initUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpView()
        initUI()
    }

    open fun setUpView() {
        binding = attachBinding()
        binding?.let { setContentView(it.root) }
    }

    abstract fun attachBinding(): VB

    fun withBinding(block: (VB.() -> Unit)) {
        binding?.apply { block.invoke(this) }
    }

    protected fun showMessage(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show()
    }

    protected fun showMessage(stringRes: StringResource) {
        Toast.makeText(this, stringRes.getString(this), Toast.LENGTH_LONG).show()
    }

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
        }.show(supportFragmentManager, AlertDialog.TAG)
    }

    protected inline fun <T> Flow<T>.observeInLifecycle(crossinline observer: (T) -> Unit) {
        this.onEach {
            observer(it)
        }.launchIn(lifecycleScope)
    }

    protected inline fun <T> Flow<T>.observeWhenResumed(crossinline observer: (T) -> Unit) {
        lifecycleScope.launchWhenResumed {
            observeInLifecycle(observer)
        }
    }
}
