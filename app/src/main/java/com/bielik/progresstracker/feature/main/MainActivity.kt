package com.bielik.progresstracker.feature.main

import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bielik.progresstracker.R
import com.bielik.progresstracker.base.BaseSplashActivity
import com.bielik.progresstracker.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseSplashActivity<ActivityMainBinding, MainViewModel>() {

    override val viewModel by viewModels<MainViewModel>()

    override fun initUI() {
        subscribe()
    }

    private fun subscribe() {
        viewModel.navigationSetupFlow.observeInLifecycle { setupNavigationGraph(it) }
    }

    private fun setupNavigationGraph(shouldLaunchOnBoarding: Boolean) {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerMain) as NavHostFragment
        navHostFragment.navController.setGraph(if (shouldLaunchOnBoarding) R.navigation.nav_on_boarding else R.navigation.nav_main_container)
    }

    // todo remove
    private fun setUpBottomMenu() = withBinding {
        bottomNavView.itemIconTintList = null
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerMain) as NavHostFragment
        bottomNavView.setupWithNavController(navHostFragment.navController)
    }

    override fun attachBinding() = ActivityMainBinding.inflate(layoutInflater)
}