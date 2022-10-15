package com.bielik.progresstracker.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.bielik.progresstracker.common.StringResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

abstract class BaseBindingFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment() {

    protected var binding: VB? = null

    abstract val viewModel: VM

    abstract fun initUI()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = getInflatedView(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    fun onBackPressed() {
        activity?.onBackPressed()
    }

    abstract fun attachBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): VB

    fun withBinding(block: (VB.() -> Unit)) {
        binding?.apply {
            block.invoke(this)
        }
            ?: run {
                error("Accessing binding outside of lifecycle: ${this::class.java.simpleName}")
            }
    }

    private fun showMessage(stringRes: StringResource) {
        Toast.makeText(requireContext(), stringRes.getString(requireContext()), Toast.LENGTH_LONG).show()
    }

    protected open fun showMessage(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
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

    protected fun <T> MutableSharedFlow<T>.emitInLifecycle(value: T) {
        lifecycleScope.launchWhenResumed {
            emit(value)
        }
    }

    private fun getInflatedView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): View {
        binding = attachBinding(inflater, container, attachToRoot)
        return binding?.root
            ?: error("Provide binding")
    }
}
