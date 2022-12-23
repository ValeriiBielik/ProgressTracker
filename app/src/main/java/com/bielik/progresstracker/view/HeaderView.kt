package com.bielik.progresstracker.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bielik.progresstracker.databinding.LayoutHeaderBinding
import com.bielik.progresstracker.utils.extensions.onClick
import com.bielik.progresstracker.utils.extensions.setVisibleOrGone

class HeaderView @JvmOverloads constructor(
    ctx: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(ctx, attributeSet, defStyleAttr) {

    private val binding = LayoutHeaderBinding.inflate(LayoutInflater.from(context), this, true)

    fun setupView(title: String, showBackIcon: Boolean = false, showSaveIcon: Boolean = false) {
        binding.tvTitle.text = title
        binding.ivBack.setVisibleOrGone(showBackIcon)
        binding.btnSave.setVisibleOrGone(showSaveIcon)
    }

    fun setOnBackClickListener(onClick: () -> Unit) {
        binding.ivBack.onClick { onClick.invoke() }
    }

    fun setOnSaveClickListener(onClick: () -> Unit) {
        binding.btnSave.onClick { onClick.invoke() }
    }
}
