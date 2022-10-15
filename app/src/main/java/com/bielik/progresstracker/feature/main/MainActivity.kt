package com.bielik.progresstracker.feature.main

import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bielik.progresstracker.R
import com.bielik.progresstracker.base.BaseBindingActivity
import com.bielik.progresstracker.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseBindingActivity<ActivityMainBinding, MainViewModel>() {

    override val viewModel by viewModels<MainViewModel>()

    override fun initUI() {
        setUpBottomMenu()
    }

    private fun setUpBottomMenu() = withBinding {
        bottomNavView.itemIconTintList = null
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostMainFragment) as NavHostFragment
        bottomNavView.setupWithNavController(navHostFragment.navController)
    }

    override fun attachBinding() = ActivityMainBinding.inflate(layoutInflater)
}