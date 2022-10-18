package com.bielik.progresstracker.feature.on_boarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bielik.progresstracker.base.BaseBindingFragment
import com.bielik.progresstracker.databinding.FragmentOnBoardingBinding
import com.bielik.progresstracker.utils.extensions.onActionDone
import com.bielik.progresstracker.utils.extensions.textChanges
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment : BaseBindingFragment<FragmentOnBoardingBinding, OnBoardingViewModel>() {

    override val viewModel by viewModels<OnBoardingViewModel>()

    override fun initUI() {
        implementClickListener()
        subscribe()
    }

    private fun subscribe() = withBinding {
        viewModel.successFlow.observeWhenResumed { findNavController().navigate(OnBoardingFragmentDirections.navigateToMainContainerFragment()) }
        etUserName.textChanges().observeWhenResumed { btnAccept.isEnabled = !it.isNullOrBlank() }
    }

    private fun implementClickListener() = withBinding {
        btnAccept.setOnClickListener { viewModel.saveUserName(etUserName.text.toString()) }
        etUserName.onActionDone {
            viewModel.saveUserName(etUserName.text.toString())
            true
        }
    }

    override fun attachBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) =
        FragmentOnBoardingBinding.inflate(inflater, container, false)
}