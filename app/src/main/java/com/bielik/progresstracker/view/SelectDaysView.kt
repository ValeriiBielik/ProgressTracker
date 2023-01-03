package com.bielik.progresstracker.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bielik.progresstracker.databinding.ViewSelectDaysBinding
import com.bielik.progresstracker.model.Day

class SelectDaysView @JvmOverloads constructor(
    ctx: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(ctx, attributeSet, defStyleAttr) {

    private val binding = ViewSelectDaysBinding.inflate(LayoutInflater.from(context), this, true)

    fun getSelectedDays(): List<Day> {
        val daysList = mutableListOf<Day>()
        if (binding.civMonday.isChecked) {
            daysList.add(Day.MONDAY)
        }
        if (binding.civTuesday.isChecked) {
            daysList.add(Day.TUESDAY)
        }
        if (binding.civWednesday.isChecked) {
            daysList.add(Day.WEDNESDAY)
        }
        if (binding.civThursday.isChecked) {
            daysList.add(Day.THURSDAY)
        }
        if (binding.civFriday.isChecked) {
            daysList.add(Day.FRIDAY)
        }
        if (binding.civSaturday.isChecked) {
            daysList.add(Day.SATURDAY)
        }
        if (binding.civSunday.isChecked) {
            daysList.add(Day.SUNDAY)
        }
        return daysList
    }

    fun setupView(selectedDays: Array<Day>?) {
        if (selectedDays.isNullOrEmpty()) return

        binding.civMonday.setChecked(selectedDays.contains(Day.MONDAY))
        binding.civTuesday.setChecked(selectedDays.contains(Day.TUESDAY))
        binding.civWednesday.setChecked(selectedDays.contains(Day.WEDNESDAY))
        binding.civThursday.setChecked(selectedDays.contains(Day.THURSDAY))
        binding.civFriday.setChecked(selectedDays.contains(Day.FRIDAY))
        binding.civSaturday.setChecked(selectedDays.contains(Day.SATURDAY))
        binding.civSunday.setChecked(selectedDays.contains(Day.SUNDAY))
    }
}