package com.bielik.progresstracker.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bielik.progresstracker.databinding.LayoutHeaderBinding
import com.bielik.progresstracker.utils.extensions.setVisibleOrGone

class HeaderView @JvmOverloads constructor(
    private val ctx: Context,
    private val attributeSet: AttributeSet? = null,
    private val defStyleAttr: Int = 0
) : ConstraintLayout(ctx, attributeSet, defStyleAttr) {

    private val binding = LayoutHeaderBinding.inflate(LayoutInflater.from(context), this, true)

    fun setupView(title : String, showBackIcon: Boolean) {
        binding.tvTitle.text = title
        binding.ivBack.setVisibleOrGone(showBackIcon)
    }

}
