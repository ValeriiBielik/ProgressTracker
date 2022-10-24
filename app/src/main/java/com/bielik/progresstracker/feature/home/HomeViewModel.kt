package com.bielik.progresstracker.feature.home

import com.bielik.progresstracker.base.BaseViewModel
import com.bielik.progresstracker.model.UserDetails
import com.bielik.progresstracker.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    preferencesRepository: PreferencesRepository
) : BaseViewModel() {
    val setupFlow: SharedFlow<UserDetails?> = MutableSharedFlow(replay = 1)

    init {
        setupFlow.emitViewModelScope(preferencesRepository.userDetails)
    }
}