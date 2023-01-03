package com.bielik.progresstracker.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bielik.progresstracker.R
import com.bielik.progresstracker.databinding.LayoutItemCheckableBinding
import com.bielik.progresstracker.utils.extensions.onClick

class CheckableItemView @JvmOverloads constructor(
    ctx: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(ctx, attributeSet, defStyleAttr) {

    private val binding = LayoutItemCheckableBinding.inflate(LayoutInflater.from(context), this, true)

    val isChecked get() = binding.cbChecked.isChecked

    init {
        with(binding) {
            val attributes = root.context.obtainStyledAttributes(attributeSet, R.styleable.CheckableItemView)
            tvTitle.text = (root.context.getString(attributes.getResourceId(R.styleable.CheckableItemView_checkableItemTitle, R.string.empty)))
            attributes.recycle()
            container.onClick { setChecked(!isChecked) }
        }
    }

    fun setChecked(isChecked: Boolean) {
        binding.cbChecked.isChecked = isChecked
    }

}