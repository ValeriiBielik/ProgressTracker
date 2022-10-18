package com.bielik.progresstracker.feature.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bielik.progresstracker.base.BaseBindingFragment
import com.bielik.progresstracker.base.EmptyViewModel
import com.bielik.progresstracker.databinding.FragmentMainContainerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainContainerFragment : BaseBindingFragment<FragmentMainContainerBinding, EmptyViewModel>() {

    override val viewModel by viewModels<EmptyViewModel>()

    override fun initUI() {

    }

    override fun attachBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) =
        FragmentMainContainerBinding.inflate(inflater, container, false)
}