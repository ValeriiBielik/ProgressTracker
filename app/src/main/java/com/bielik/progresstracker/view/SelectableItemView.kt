package com.bielik.progresstracker.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bielik.progresstracker.R
import com.bielik.progresstracker.databinding.ViewSelectableItemBinding
import com.bielik.progresstracker.utils.extensions.getThemeColor
import com.bielik.progresstracker.utils.extensions.gone
import com.bielik.progresstracker.utils.extensions.visible

class SelectableItemView @JvmOverloads constructor(
    ctx: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(ctx, attributeSet, defStyleAttr) {

    private val binding = ViewSelectableItemBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        with(binding) {
            val attributes = root.context.obtainStyledAttributes(attributeSet, R.styleable.SelectableItemView)
            tvTitle.text = (root.context.getString(attributes.getResourceId(R.styleable.SelectableItemView_selectableItemTitle, R.string.empty)))
            attributes.recycle()
        }
    }

    fun setSelected() {
        binding.container.setBackgroundColor(context.getThemeColor(com.google.android.material.R.attr.colorSurfaceVariant))
        binding.tvTitle.setTextColor(context.getThemeColor(com.google.android.material.R.attr.colorOnSurfaceVariant))
        binding.ivIsSelected.visible()
    }

    fun removeSelection() {
        binding.container.setBackgroundColor(context.getThemeColor(com.google.android.material.R.attr.backgroundColor))
        binding.tvTitle.setTextColor(context.getThemeColor(com.google.android.material.R.attr.colorOnBackground))
        binding.ivIsSelected.gone()
    }
}