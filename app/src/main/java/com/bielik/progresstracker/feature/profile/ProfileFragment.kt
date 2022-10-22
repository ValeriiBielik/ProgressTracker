package com.bielik.progresstracker.feature.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bielik.progresstracker.base.BaseBindingFragment
import com.bielik.progresstracker.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseBindingFragment<FragmentProfileBinding, ProfileViewModel>() {
    override val viewModel by viewModels<ProfileViewModel>()

    override fun initUI() {
        subscribe()
    }

    private fun subscribe() {
        viewModel.setupFlow.observeWhenResumed {
            binding?.tvUserName?.text = it
        }
    }

    override fun attachBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) =
        FragmentProfileBinding.inflate(inflater, container, false)

}