package com.bielik.progresstracker.utils.extensions

inline fun <R> String?.notNullOrEmpty(body: (string: String) -> R): R? {
    return if (!this.isNullOrEmpty())
        body(this)
    else
        null
}

fun String?.notNullOrEmpty(): String? {
    return if (!this.isNullOrEmpty())
        this
    else
        null
}

fun String.isNotBlankAndNotEmpty(): Boolean = isNotBlank() && isNotEmpty()

fun String.shortenName(): String = substring(0, 1).plus(".")
