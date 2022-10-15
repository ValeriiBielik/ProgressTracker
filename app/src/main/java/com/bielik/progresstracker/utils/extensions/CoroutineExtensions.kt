package com.bielik.progresstracker.utils.extensions

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.resume

@Throws(CancellationException::class)
suspend fun checkCancellation() {
    if (!coroutineContext.isActive) {
        throw CancellationException()
    }
}

suspend inline fun <T> inBackground(crossinline block: suspend () -> T): T =
    withContext(Dispatchers.Default) { block() }

suspend inline fun <T> iOWork(crossinline block: suspend () -> T): T =
    withContext(Dispatchers.IO) { block() }

suspend inline fun <T> onUiThread(crossinline block: suspend () -> T): T =
    withContext(Dispatchers.Main) { block() }

suspend fun <T> Deferred<T>.awaitAndAlso(block: suspend (T?) -> Unit): T {
    return try {
        await().also {
            block(it)
        }
    } catch (e: CancellationException) {
        block(null)
        throw e
    }
}

fun CancellableContinuation<Unit>.resumeIfActive() {
    if (this.isActive) resume(Unit)
}
