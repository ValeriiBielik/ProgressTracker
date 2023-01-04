package com.bielik.progresstracker.feature.select_repeat_option.model

import com.bielik.progresstracker.model.common.RepeatOption

data class RepeatOptionsUiData(
    val previousOption: RepeatOption? = null,
    val selectedOption: RepeatOption = RepeatOption.ONCE,
    val isInitialData: Boolean = true
)