package com.bielik.progresstracker.utils.extensions

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

val WindowInsetsCompat.keyboardInsets get() = getInsets(WindowInsetsCompat.Type.ime())

val WindowInsetsCompat.systemBarsInsets get() = getInsets(WindowInsetsCompat.Type.systemBars())

private val WindowInsetsCompat.systemIndents: SystemIndents
    get() = SystemIndents(
        top = systemBarsInsets.top,
        bottom = systemBarsInsets.bottom,
        keyboard = keyboardInsets.bottom
    )

inline fun View.doOnWindowInsetsApplied(crossinline block: (View, WindowInsetsCompat) -> Unit) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        block(view, insets)
        insets
    }
    requestApplyInsetsWhenAttached()
}

/**
 * On each inset change applies insets according to adopt parameters in requests.
 */
fun adoptSystemBarsInsets(vararg adoptInsetRequests: AdoptInsetRequest) {
    val viewToListenUpon = adoptInsetRequests.firstOrNull()?.view
        ?: run {
            Log.e("ViewInsetExtensions", "No adopt inset requests to handle. Skipping")
            return
        }

    adoptSystemBarsInsets(viewToListenUpon, *adoptInsetRequests)
}

/**
 * On each inset change applies insets according to adopt parameters in requests.
 *
 * @return live data which is updated each time window insets are changed
 */
fun adoptSystemBarsInsets(
    viewToListenUpon: View,
    vararg adoptInsetRequests: AdoptInsetRequest
): LiveData<WindowInsetsCompat> {
    val insetLiveData = MutableLiveData<WindowInsetsCompat>()
    viewToListenUpon.doOnWindowInsetsApplied { _, windowInsetsCompat ->
        val systemIndents = windowInsetsCompat.systemIndents
        adoptInsetRequests.forEach { adoptRequest ->
            adoptInsets(adoptRequest, systemIndents)
        }
        insetLiveData.value = windowInsetsCompat
    }

    return insetLiveData
}

private fun adoptInsets(request: AdoptInsetRequest, systemIndents: SystemIndents) {
    val view = request.view
    val bottomWindowIndent = when {
        request.updateForKeyboard -> {
            systemIndents.keyboard.takeIf { it != 0 } ?: systemIndents.bottom
        }
        request.updateBottom -> systemIndents.bottom
        else -> 0
    }

    val topWindowIndent = if (request.updateTop) systemIndents.top else 0

    when (request.parameters) {
        is AdoptInsetAsPaddingParam -> view.updatePadding(
            top = topWindowIndent + request.originalTopPadding,
            bottom = bottomWindowIndent + request.originalBottomPadding
        )
        is AdoptInsetAsMarginParam -> view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            topMargin = topWindowIndent + request.originalTopMargin
            bottomMargin = bottomWindowIndent + request.originalBottomMargin
        }
    }.exhaustive
}

fun View.adoptSystemBarInsetsAsMargin(
    top: Boolean = false,
    bottom: Boolean = false,
    keyboard: Boolean = false
) {
    adoptSystemBarsInsets(this, this.insetAsMargin(top, bottom, keyboard))
}

fun View.adoptSystemBarInsetsAsPadding(
    top: Boolean = false,
    bottom: Boolean = false,
    keyboard: Boolean = false
) {
    adoptSystemBarsInsets(this, this.insetAsPadding(top, bottom, keyboard))
}

/**
 * If a view calls requestApplyInsets() while it is not attached to the view hierarchy, the call is dropped on the floor and ignored.
 * Read more: https://medium.com/androiddevelopers/windowinsets-listeners-to-layouts-8f9ccc8fa4d1
 */
fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        requestApplyInsets()
    } else {
        addOnAttachStateChangeListener(
            object : View.OnAttachStateChangeListener {
                override fun onViewAttachedToWindow(v: View) {
                    v.removeOnAttachStateChangeListener(this)
                    v.requestApplyInsets()
                }

                override fun onViewDetachedFromWindow(v: View) = Unit
            }
        )
    }
}

fun View.insetAsPadding(
    top: Boolean = false,
    bottom: Boolean = false,
    keyboard: Boolean = false
) = AdoptInsetRequest(this, AdoptInsetAsPaddingParam(top, bottom, keyboard))

fun View.insetAsMargin(
    top: Boolean = false,
    bottom: Boolean = false,
    keyboard: Boolean = false
) = AdoptInsetRequest(this, AdoptInsetAsMarginParam(top, bottom, keyboard))

sealed class AdoptInsetParam(
    val top: Boolean,
    val bottom: Boolean,
    val keyboard: Boolean
)

class AdoptInsetAsPaddingParam(
    top: Boolean = false,
    bottom: Boolean = false,
    keyboard: Boolean = false
) : AdoptInsetParam(top, bottom, keyboard)

class AdoptInsetAsMarginParam(
    top: Boolean = false,
    bottom: Boolean = false,
    keyboard: Boolean = false
) : AdoptInsetParam(top, bottom, keyboard)

class AdoptInsetRequest(
    val view: View,
    val parameters: AdoptInsetParam
) {
    val updateTop get() = parameters.top
    val updateBottom get() = parameters.bottom
    val updateForKeyboard get() = parameters.keyboard

    val originalTopPadding: Int = view.paddingTop
    val originalBottomPadding: Int = view.paddingBottom

    val originalTopMargin: Int = view.marginTop
    val originalBottomMargin: Int = view.marginBottom
}

class SystemIndents(
    val top: Int,
    val bottom: Int,
    val keyboard: Int
)
