package com.bielik.progresstracker.utils.extensions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.TypedValue
import androidx.annotation.ArrayRes
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat

fun Context.getColorCompat(@ColorRes res: Int): Int {
    return ContextCompat.getColor(this, res)
}

fun Context.getDimension(@DimenRes res: Int): Float {
    return this.resources.getDimension(res)
}

fun Context.getArray(@ArrayRes res: Int): Array<String> {
    return this.resources.getStringArray(res)
}

fun Context.copyToClipboard(data: String) {
    val clipboard: ClipboardManager? = getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager?
    val clip = ClipData.newPlainText("", data)
    clipboard?.setPrimaryClip(clip)
}

@ColorInt
fun Context.getThemeColor(@AttrRes attrRes: Int): Int = TypedValue()
    .apply { theme.resolveAttribute(attrRes, this, true) }
    .data