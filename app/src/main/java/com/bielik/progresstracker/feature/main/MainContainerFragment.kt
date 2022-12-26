package com.bielik.progresstracker.feature.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bielik.progresstracker.R
import com.bielik.progresstracker.base.BaseBindingFragment
import com.bielik.progresstracker.base.EmptyViewModel
import com.bielik.progresstracker.databinding.FragmentMainContainerBinding
import com.bielik.progresstracker.utils.extensions.setVisibleOrGone
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainContainerFragment : BaseBindingFragment<FragmentMainContainerBinding, EmptyViewModel>() {

    override val viewModel by viewModels<EmptyViewModel>()

    override fun initUI() {
        setUpBottomMenu()
    }

    private fun setUpBottomMenu() = withBinding {
        bottomNavView.itemIconTintList = null
        val navHostFragment = childFragmentManager.findFragmentById(R.id.fragmentContainerSubMain) as NavHostFragment
        bottomNavView.setupWithNavController(navHostFragment.navController)
        navHostFragment.navController.addOnDestinationChangedListener { _, dest, _ ->
            bottomNavView.setVisibleOrGone(
                dest.id == R.id.navigationHome || dest.id == R.id.navigationProgress
                        || dest.id == R.id.navigationAlerts || dest.id == R.id.navigationProfile
            )
        }
    }

    override fun attachBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) =
        FragmentMainContainerBinding.inflate(inflater, container, false)
}