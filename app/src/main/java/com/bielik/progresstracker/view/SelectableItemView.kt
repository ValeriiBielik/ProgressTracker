package com.bielik.progresstracker.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bielik.progresstracker.R
import com.bielik.progresstracker.databinding.LayoutItemSelectableBinding
import com.bielik.progresstracker.utils.extensions.gone
import com.bielik.progresstracker.utils.extensions.onClick
import com.bielik.progresstracker.utils.extensions.visible

class SelectableItemView @JvmOverloads constructor(
    ctx: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(ctx, attributeSet, defStyleAttr) {

    private val binding = LayoutItemSelectableBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        with(binding) {
            val attributes = root.context.obtainStyledAttributes(attributeSet, R.styleable.SelectableItemView)
            tvTitle.text = (root.context.getString(attributes.getResourceId(R.styleable.SelectableItemView_itemTitle, R.string.empty)))
            attributes.recycle()
        }
    }

    fun setSelected() {
        binding.container.setBackgroundResource(R.color.light_blue_200_50)
        binding.ivIsSelected.visible()
    }

    fun removeSelection() {
        binding.container.setBackgroundResource(R.color.white)
        binding.ivIsSelected.gone()
    }
}