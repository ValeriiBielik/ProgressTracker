package com.bielik.progresstracker.feature.home.adapter

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import com.bielik.progresstracker.R
import com.bielik.progresstracker.base.BaseBindingViewHolder
import com.bielik.progresstracker.databinding.ItemTicketBinding
import com.bielik.progresstracker.model.TicketModel
import com.bielik.progresstracker.model.TicketType
import com.bielik.progresstracker.utils.extensions.onClick

class TicketViewHolder(
    binding: ItemTicketBinding,
    private val onItemClick: (id: Long) -> Unit
) : BaseBindingViewHolder<TicketModel, ItemTicketBinding>(binding) {

    private val colorGreen by lazy { ContextCompat.getColor(context, R.color.green_200) }
    private val colorRed by lazy { ContextCompat.getColor(context, R.color.red_200) }

    override fun bindData(item: TicketModel?) = withBinding {
        if (item == null) {
            return@withBinding
        }

        tvTitle.text = item.name

        when (item.ticketType) {
            TicketType.TASK -> ivTicket.setImageResource(R.drawable.ic_task)
            TicketType.REPEATABLE_TASK -> ivTicket.setImageResource(R.drawable.ic_repeatable_task)
            TicketType.PROGRESS_TRACKED_TASK -> ivTicket.setImageResource(R.drawable.ic_progress_track)
        }

        flIconContainer.backgroundTintList = ColorStateList.valueOf(if (item.isDone) colorGreen else colorRed)
        root.onClick { onItemClick.invoke(item.id ?: 0) }
    }
}