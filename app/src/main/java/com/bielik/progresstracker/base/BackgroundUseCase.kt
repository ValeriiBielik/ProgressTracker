package com.bielik.progresstracker.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

abstract class BackgroundUseCase<IN_TYPE, OUT_TYPE> : BaseUseCase<IN_TYPE, OUT_TYPE>() {
    override val executionContext: CoroutineContext
        get() = Dispatchers.IO
}

abstract class BackgroundNoParamsUseCase<OUT_TYPE> : BackgroundUseCase<Unit, OUT_TYPE>() {

    fun execute(callback: ((OUT_TYPE) -> Unit)? = null) {
        super.execute(Unit, callback)
    }

    fun execute(coroutineScope: CoroutineScope, callback: ((OUT_TYPE) -> Unit)? = null) {
        super.execute(coroutineScope, Unit, callback)
    }

    suspend fun executeSuspendable(): OUT_TYPE = super.executeSuspendable(Unit)

    suspend operator fun invoke(): OUT_TYPE = executeSuspendable()
}
