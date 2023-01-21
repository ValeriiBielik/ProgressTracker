package com.bielik.progresstracker.feature.home

import android.graphics.Typeface
import android.view.View
import androidx.core.view.isVisible
import com.bielik.progresstracker.databinding.ItemWeekDayBinding
import com.bielik.progresstracker.utils.extensions.displayText
import com.bielik.progresstracker.utils.extensions.onClick
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.view.ViewContainer
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DayViewContainer(
    view: View,
    private val dateFormatter: DateTimeFormatter,
    val onClick: (LocalDate) -> Unit
) : ViewContainer(view) {

    private val binding = ItemWeekDayBinding.bind(view)

    fun bind(day: WeekDay, selectedDate: LocalDate?) {
        if (selectedDate == null) return

        binding.tvMonthDay.text = dateFormatter.format(day.date)
        binding.tvWeekDay.text = day.date.dayOfWeek.displayText()

        binding.tvMonthDay.setTypeface(null, if (day.date == selectedDate) Typeface.BOLD else Typeface.NORMAL)
        binding.tvWeekDay.setTypeface(null, if (day.date == selectedDate) Typeface.BOLD else Typeface.NORMAL)
        binding.selectionView.isVisible = day.date == selectedDate
        binding.root.onClick { onClick.invoke(day.date) }
    }
}