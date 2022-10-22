package com.bielik.progresstracker.feature.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bielik.progresstracker.R
import com.bielik.progresstracker.base.BaseBindingFragment
import com.bielik.progresstracker.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseBindingFragment<FragmentHomeBinding, HomeViewModel>() {
    override val viewModel by viewModels<HomeViewModel>()
    override fun initUI() {
        subscribe()
    }

    private fun subscribe(){
        viewModel.setupFlow.observeWhenResumed {
            binding?.tvGreetingUser?.text = getString(R.string.format_user_greeting, it)
        }
    }

    override fun attachBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) =
        FragmentHomeBinding.inflate(inflater, container, false)
}