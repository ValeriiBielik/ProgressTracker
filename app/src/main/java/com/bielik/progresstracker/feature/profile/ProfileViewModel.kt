package com.bielik.progresstracker.feature.profile

import android.net.Uri
import com.bielik.progresstracker.base.BaseViewModel
import com.bielik.progresstracker.model.UserDetails
import com.bielik.progresstracker.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : BaseViewModel() {

    val setupFlow: SharedFlow<UserDetails?> = MutableSharedFlow(replay = 1)

    init {
        setupFlow.emitViewModelScope(preferencesRepository.userDetails)
    }

    fun onImagePicked(uri: Uri) {
        preferencesRepository.saveUserImage(uri.toString())
    }
}