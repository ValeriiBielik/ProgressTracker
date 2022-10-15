package com.bielik.progresstracker.base

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class BaseUseCase<IN_TYPE, OUT_TYPE> {
    private val job = Job()

    protected val uiScope = CoroutineScope(Dispatchers.Main + job)
    protected val ioScope by lazy { CoroutineScope(Dispatchers.IO + job) }
    protected val defaultScope by lazy { CoroutineScope(Dispatchers.Default + job) }

    abstract val executionContext: CoroutineContext

    fun execute(value: IN_TYPE, callback: ((OUT_TYPE) -> Unit)? = null) {
        execute(uiScope, value, callback)
    }

    fun execute(scope: CoroutineScope, value: IN_TYPE, callback: ((OUT_TYPE) -> Unit)? = null) {
        scope.launch {
            withContext(executionContext) {
                executeRequest(value, scope)
            }.let { callback?.invoke(it) }
        }
    }

    suspend fun executeSuspendable(value: IN_TYPE): OUT_TYPE = withContext(executionContext) {
        executeRequest(value, uiScope)
    }

    suspend operator fun invoke(value: IN_TYPE): OUT_TYPE = executeSuspendable(value)

    protected abstract suspend fun executeRequest(
        request: IN_TYPE,
        coroutineScope: CoroutineScope
    ): OUT_TYPE
}
