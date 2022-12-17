package com.bielik.progresstracker.feature.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bielik.progresstracker.base.BaseAdapter
import com.bielik.progresstracker.databinding.ItemTicketBinding
import com.bielik.progresstracker.model.TicketModel

class TicketsAdapter : BaseAdapter<TicketModel, TicketViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        return TicketViewHolder(ItemTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}
