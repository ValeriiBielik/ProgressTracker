package com.bielik.progresstracker.feature.persistence_task_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bielik.progresstracker.base.BaseAdapter
import com.bielik.progresstracker.databinding.ItemTicketBinding
import com.bielik.progresstracker.model.database.TicketTemplateModel

class PersistenceTasksAdapter : BaseAdapter<TicketTemplateModel, PersistenceTaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersistenceTaskViewHolder {
        return PersistenceTaskViewHolder(ItemTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

}