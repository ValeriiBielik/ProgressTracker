package com.bielik.progresstracker.feature.persistence_task_list.adapter

import android.content.res.ColorStateList
import androidx.core.view.ViewCompat
import com.bielik.progresstracker.R
import com.bielik.progresstracker.base.BaseBindingViewHolder
import com.bielik.progresstracker.databinding.ItemTicketBinding
import com.bielik.progresstracker.model.database.TicketTemplateModel
import com.bielik.progresstracker.utils.extensions.getThemeColor

class PersistenceTaskViewHolder(
    binding: ItemTicketBinding
) : BaseBindingViewHolder<TicketTemplateModel, ItemTicketBinding>(binding) {

    private val colorBackground by lazy {
        ColorStateList.valueOf(binding.root.context.getThemeColor(com.google.android.material.R.attr.colorSurfaceVariant))
    }

    override fun bindData(item: TicketTemplateModel?) {
        if (item == null) return
        binding.tvTitle.text = item.name
        binding.ivTicket.setImageResource(R.drawable.ic_repeatable_task)
        ViewCompat.setBackgroundTintList(binding.flIconContainer, colorBackground)
    }
}