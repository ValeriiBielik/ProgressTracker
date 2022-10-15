package com.bielik.progresstracker.base

import android.annotation.SuppressLint
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.viewbinding.ViewBinding

@SuppressLint("CustomSplashScreen")
abstract class BaseSplashActivity<VB : ViewBinding, VM : BaseViewModel> : BaseBindingActivity<VB, VM>() {

    override fun setUpView() {
        binding = attachBinding()
        installSplashScreen()
        binding?.let { setContentView(it.root) }
    }
}