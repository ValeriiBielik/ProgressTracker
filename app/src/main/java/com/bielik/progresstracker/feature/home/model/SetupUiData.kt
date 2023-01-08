package com.bielik.progresstracker.feature.home.model

import java.time.LocalDate
import java.time.YearMonth

data class SetupUiData(
    val userName: String?,
    val selectedDate: LocalDate,
    val currentMonth: YearMonth = YearMonth.now()
)
