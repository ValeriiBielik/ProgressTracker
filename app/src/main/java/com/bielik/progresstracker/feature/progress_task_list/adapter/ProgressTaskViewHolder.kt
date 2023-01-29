package com.bielik.progresstracker.feature.progress_task_list.adapter

import com.bielik.progresstracker.base.BaseBindingViewHolder
import com.bielik.progresstracker.databinding.ItemTemplateBinding
import com.bielik.progresstracker.model.database.TicketTemplateModel

class ProgressTaskViewHolder(
    binding: ItemTemplateBinding
) : BaseBindingViewHolder<TicketTemplateModel, ItemTemplateBinding>(binding) {

    override fun bindData(item: TicketTemplateModel?) {
        if (item == null) return
        binding.tvTitle.text = item.name
    }
}