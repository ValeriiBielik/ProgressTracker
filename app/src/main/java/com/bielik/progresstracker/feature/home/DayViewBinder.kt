package com.bielik.progresstracker.feature.home

import android.view.View
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.view.WeekDayBinder
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DayViewBinder(
    private var selectedDate: LocalDate,
    private val onClick: (LocalDate) -> Unit
) : WeekDayBinder<DayViewContainer> {

    private val dateFormatter = DateTimeFormatter.ofPattern("dd")

    override fun create(view: View) = DayViewContainer(view, dateFormatter, ::onItemClick)

    override fun bind(container: DayViewContainer, data: WeekDay) {
        container.bind(data, selectedDate)
    }

    private fun onItemClick(date: LocalDate) {
        onClick.invoke(date)
    }

    fun setSelectedDate(date: LocalDate) {
        selectedDate = date
    }
}