package com.bielik.progresstracker.utils.extensions

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

internal const val NO_FLAGS = 0

fun Activity.hideKeyboard() {
    currentFocus?.let {
        val iim = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        iim.hideSoftInputFromWindow(it.windowToken, NO_FLAGS)
    }
}

fun Fragment.hideKeyboard() {
    activity?.hideKeyboard()
}

fun AppCompatActivity.getDisplayedFragment(@IdRes containerViewId: Int): Fragment? =
    supportFragmentManager.findFragmentById(containerViewId)

fun AppCompatActivity.getDisplayedFragmentFromNavHost(@IdRes containerViewId: Int): Fragment? =
    getDisplayedFragment(containerViewId)?.childFragmentManager?.fragments?.lastOrNull()
