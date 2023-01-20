package com.bielik.progresstracker.feature.home

import android.view.View
import androidx.core.view.isVisible
import com.bielik.progresstracker.R
import com.bielik.progresstracker.databinding.ItemWeekDayBinding
import com.bielik.progresstracker.utils.extensions.displayText
import com.bielik.progresstracker.utils.extensions.getColorCompat
import com.bielik.progresstracker.utils.extensions.getThemeColor
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

    private val defaultTextColor by lazy { view.context.getThemeColor(com.google.android.material.R.attr.colorOnPrimary) }
    private val selectedTextColor by lazy { view.context.getColorCompat(R.color.green_200) }

    private val binding = ItemWeekDayBinding.bind(view)

    fun bind(day: WeekDay, selectedDate: LocalDate?) {
        if (selectedDate == null) return

        binding.tvMonthDay.text = dateFormatter.format(day.date)
        binding.tvWeekDay.text = day.date.dayOfWeek.displayText()

        binding.tvMonthDay.setTextColor(if (day.date == selectedDate) selectedTextColor else defaultTextColor)
        binding.selectionView.isVisible = day.date == selectedDate
        binding.root.onClick { onClick.invoke(day.date) }
    }
}