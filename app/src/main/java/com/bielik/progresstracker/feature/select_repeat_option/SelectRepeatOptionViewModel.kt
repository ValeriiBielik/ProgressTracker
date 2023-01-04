package com.bielik.progresstracker.feature.select_repeat_option

import com.bielik.progresstracker.base.BaseViewModel
import com.bielik.progresstracker.feature.select_repeat_option.model.RepeatOptionsUiData
import com.bielik.progresstracker.model.common.RepeatOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

@HiltViewModel
class SelectRepeatOptionViewModel @Inject constructor() : BaseViewModel() {

    val selectedOptionFlow: SharedFlow<RepeatOptionsUiData> = MutableSharedFlow(replay = 1)

    fun init(repeatOption: RepeatOption) {
        selectedOptionFlow.emitViewModelScope(RepeatOptionsUiData(selectedOption = repeatOption))
    }

    fun onOptionSelected(option: RepeatOption) {
        val optionsData = selectedOptionFlow.replayCache.firstOrNull()
        selectedOptionFlow.emitViewModelScope(
            RepeatOptionsUiData(
                previousOption = optionsData?.selectedOption,
                selectedOption = option,
                isInitialData = false
            )
        )
    }
}