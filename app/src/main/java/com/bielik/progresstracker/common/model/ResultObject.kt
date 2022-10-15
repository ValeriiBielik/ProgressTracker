package com.bielik.progresstracker.common.model

import com.bielik.progresstracker.common.StringResource

sealed class ResultObject<out T> {
    data class Success<T>(val value: T) : ResultObject<T>()
    class Error(val errorCause: StringResource? = null) : ResultObject<Nothing>()

    companion object {
        fun success() = Success(Unit)
    }
}
