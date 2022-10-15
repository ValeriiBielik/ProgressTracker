package com.bielik.progresstracker.utils.extensions

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import android.view.inputmethod.InputMethodManager

const val MIN_CLICK_INTERVAL: Long = 300
const val MIN_LONG_CLICK_INTERVAL: Long = 1000
const val ANIMATE_DURATION_MILLIS: Long = 300

fun View.onClick(body: () -> Unit) {
    clickWithDebounce { body() }
}

fun View.onLongClick(body: () -> Unit) {
    longClickWithDebounce { body() }
}

fun View.clickWithDebounce(
    debounceTime: Long = MIN_CLICK_INTERVAL,
    action: () -> Unit,
) {
    var lastClickTime: Long = 0
    setOnClickListener {
        if (System.currentTimeMillis() - lastClickTime < debounceTime) return@setOnClickListener
        else action()
        lastClickTime = System.currentTimeMillis()
    }
}

fun View.longClickWithDebounce(
    debounceTime: Long = MIN_LONG_CLICK_INTERVAL,
    action: () -> Unit
) {
    var lastClickTime: Long = 0
    setOnLongClickListener {
        if (System.currentTimeMillis() - lastClickTime < debounceTime) {
            return@setOnLongClickListener true
        } else {
            action()
            lastClickTime = System.currentTimeMillis()
            return@setOnLongClickListener true
        }
    }
}

fun View.setVisibleOrGone(condition: Boolean) {
    visibility = if (condition) View.VISIBLE else View.GONE
}

fun View.setVisibleOrInvisible(condition: Boolean) {
    visibility = if (condition) View.VISIBLE else View.INVISIBLE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

operator fun ViewGroup.get(position: Int): View? = getChildAt(position)

fun View.animateContentInFromTop(duration: Long = ANIMATE_DURATION_MILLIS) {
    apply {
        visibility = View.VISIBLE
        translationY = -height.toFloat()
        alpha = 0f
    }
    animate().translationY(0f)
        .alpha(1f)
        .setInterpolator(LinearInterpolator())
        .setDuration(duration)
        .start()
}

fun View.animateContentOutToTop() {
    animate().translationY(-height.toFloat())
        .setDuration(ANIMATE_DURATION_MILLIS)
        .setInterpolator(OvershootInterpolator())
        .start()
}
