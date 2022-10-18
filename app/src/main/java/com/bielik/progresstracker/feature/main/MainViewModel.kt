package com.bielik.progresstracker.feature.main

import com.bielik.progresstracker.base.BaseViewModel
import com.bielik.progresstracker.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    preferencesRepository: PreferencesRepository
) : BaseViewModel() {

    val navigationSetupFlow: SharedFlow<Boolean> = MutableSharedFlow(replay = 1)

    init {
        navigationSetupFlow.emitViewModelScope(!preferencesRepository.isUserLoggedIn())
    }
}