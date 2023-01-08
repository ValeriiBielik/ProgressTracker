package com.bielik.progresstracker.feature.home

import android.view.View
import androidx.core.view.isVisible
import com.bielik.progresstracker.R
import com.bielik.progresstracker.databinding.ItemWeekDayBinding
import com.bielik.progresstracker.utils.extensions.displayText
import com.bielik.progresstracker.utils.extensions.getColorCompat
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

        binding.exSevenDateText.text = dateFormatter.format(day.date)
        binding.exSevenDayText.text = day.date.dayOfWeek.displayText()

        val colorRes = if (day.date == selectedDate) {
            R.color.example_7_yellow
        } else {
            R.color.example_7_white
        }
        binding.exSevenDateText.setTextColor(view.context.getColorCompat(colorRes))
        binding.exSevenSelectedView.isVisible = day.date == selectedDate
        binding.root.onClick { onClick.invoke(day.date) }
    }
}