package com.bielik.progresstracker.utils.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

inline fun <T> Flow<T>.observeInScope(scope: CoroutineScope, crossinline observer: suspend (T) -> Unit) {
    this.onEach {
        observer(it)
    }.launchIn(scope)
}

fun Job?.safeCancel() {
    this?.takeIf { it.isActive }?.cancel()
}