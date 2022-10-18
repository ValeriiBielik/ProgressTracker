package com.bielik.progresstracker.feature.on_boarding

import com.bielik.progresstracker.base.BaseViewModel
import com.bielik.progresstracker.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : BaseViewModel() {

    val successFlow: SharedFlow<Unit> = MutableSharedFlow()

    fun saveUserName(userName: String) {
        preferencesRepository.userName = userName
        successFlow.emitViewModelScope(Unit)
    }
}