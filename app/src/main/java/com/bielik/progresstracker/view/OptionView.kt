package com.bielik.progresstracker.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bielik.progresstracker.R
import com.bielik.progresstracker.databinding.ViewOptionBinding

class OptionView @JvmOverloads constructor(
    ctx: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(ctx, attributeSet, defStyleAttr) {
    private val binding = ViewOptionBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        with(binding) {
            val attributes = root.context.obtainStyledAttributes(attributeSet, R.styleable.OptionView)
            tvOptionTitle.text = (root.context.getString(attributes.getResourceId(R.styleable.OptionView_optionTitle, R.string.empty)))
            tvOptionValue.text = (root.context.getString(attributes.getResourceId(R.styleable.OptionView_optionValue, R.string.empty)))
            attributes.recycle()
        }
    }

    fun setValue(optionValue: String) {
        binding.tvOptionValue.text = optionValue
    }
}
