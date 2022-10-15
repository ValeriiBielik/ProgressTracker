package com.bielik.progresstracker.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    suspend fun <T> SharedFlow<T>.emit(value: T) {
        if (this is MutableSharedFlow<T>) {
            value?.let {
                emit(it)
            }
        } else {
            throw IllegalStateException("$this is not MutableSharedFlow. Cannot perform emit()")
        }
    }

    fun <T> SharedFlow<T>.emitViewModelScope(value: T) {
        viewModelScope.launch {
            emit(value)
        }
    }

    protected fun <T : Any> LiveData<T>.postOrThrow(value: T) {
        when (this) {
            is MutableLiveData<T> -> postValue(value)
            else -> throw UnsupportedOperationException("It’s working only with MutableLiveData")
        }
    }
}
