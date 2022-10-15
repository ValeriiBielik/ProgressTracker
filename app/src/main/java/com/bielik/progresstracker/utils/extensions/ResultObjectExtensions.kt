package com.bielik.progresstracker.utils.extensions

import com.bielik.progresstracker.common.StringResource
import com.bielik.progresstracker.common.model.ResultObject

/**
 * Maps value using transformation and returns result if an object is Success. In other case - returns itself
 */
inline fun <T, P> ResultObject<T>.mapIfSuccess(transformation: (T) -> ResultObject<P>): ResultObject<P> =
    when (this) {
        is ResultObject.Success -> transformation(this.value)
        is ResultObject.Error -> this
    }

/**
 * Maps value using transformation and returns result if an object is Success. In other case - returns itself
 */
inline fun <T, P> ResultObject<T>.mapResultIfSuccess(transformation: (ResultObject.Success<T>) -> ResultObject<P>): ResultObject<P> =
    when (this) {
        is ResultObject.Success -> transformation(this)
        is ResultObject.Error -> this
    }

/**
 * Maps value using transformation and returns result if an object is Error. In other case - returns itself
 */
inline fun <T> ResultObject<T>.mapIfError(transformation: (StringResource?) -> ResultObject<T>): ResultObject<T> =
    when (this) {
        is ResultObject.Error -> transformation(this.errorCause)
        is ResultObject.Success -> this
    }

/**
 * Maps value using transformation and returns result if an object is Error and predicate was satisfied. In other case - returns itself
 */
inline fun <T> ResultObject<T>.mapIfError(
    predicate: (ResultObject.Error) -> Boolean = { true },
    transformation: (StringResource?) -> ResultObject<T>
): ResultObject<T> =
    when (this) {
        is ResultObject.Error -> if (predicate(this)) {
            transformation(this.errorCause)
        } else this
        is ResultObject.Success -> this
    }

/**
 * Runs action if an object is Success.
 */
inline fun <T> ResultObject<T>.runIfSuccess(action: (T) -> Unit) {
    if (this is ResultObject.Success) {
        action(this.value)
    }
}

/**
 * Runs action if an object is Error.
 */
inline fun <T> ResultObject<T>.runIfError(action: (ResultObject.Error) -> Unit) {
    if (this is ResultObject.Error) {
        action(this)
    }
}

/**
 * Return result of the action if object is Success else - NULL
 */
inline fun <T, P> ResultObject<T>.letSuccess(action: (T) -> P): P? =
    if (this is ResultObject.Success) {
        action(this.value)
    } else {
        null
    }

/**
 * Executes action if object is Error. Returns itself
 */
inline fun <T> ResultObject<T>.alsoIfError(action: (ResultObject.Error) -> Unit): ResultObject<T> {
    if (this is ResultObject.Error) action(this)
    return this
}

inline fun <T> ResultObject<T>.alsoIfSuccess(action: (T) -> Unit): ResultObject<T> {
    if (this is ResultObject.Success) action(this.value)
    return this
}

inline fun <T> ResultObject<T>.ifSuccessWithResult(action: (T) -> T): ResultObject<T> {
    if (this is ResultObject.Success) action(this.value)
    return this
}

inline fun <T> ResultObject<T>.ifErrorWithResult(action: (ResultObject.Error) -> Unit): T? {
    if (this is ResultObject.Error) action(this)
    return this.getValue()
}

/**
 * Returns value in case object is success. In other case returns NULL
 */
fun <T> ResultObject<T>.getValue(): T? = when (this) {
    is ResultObject.Success -> value
    else -> null
}

fun <T> resultCallback(
    onSuccess: (T) -> Unit,
    onError: (ResultObject.Error) -> Unit
): (ResultObject<T>) -> Unit = {
    when (it) {
        is ResultObject.Success -> onSuccess(it.value)
        is ResultObject.Error -> onError(it)
    }
}

inline fun <DATA_TYPE, RETURN_TYPE> ResultObject<DATA_TYPE>.map(
    ifError: (StringResource?) -> RETURN_TYPE,
    ifSuccess: (DATA_TYPE) -> RETURN_TYPE
): RETURN_TYPE {
    return when (this) {
        is ResultObject.Success -> ifSuccess(value)
        is ResultObject.Error -> ifError(errorCause)
    }
}

inline fun <DATA_TYPE> ResultObject<DATA_TYPE>.handle(
    ifError: (StringResource?) -> Unit,
    ifSuccess: (DATA_TYPE) -> Unit
) {
    when (this) {
        is ResultObject.Success -> ifSuccess(value)
        is ResultObject.Error -> ifError(errorCause)
    }
}

fun <T : Any> T.toSuccessResult(): ResultObject.Success<T> = ResultObject.Success(this)

fun <T : StringResource> T.toErrorResult(): ResultObject.Error = ResultObject.Error(this)

fun <T> ResultObject<T>.takeIfSuccess(): ResultObject.Success<T>? {
    return if (this is ResultObject.Success) this else null
}

fun <T> ResultObject<T>.takeIfError(): ResultObject.Error? {
    return if (this is ResultObject.Error) this else null
}
