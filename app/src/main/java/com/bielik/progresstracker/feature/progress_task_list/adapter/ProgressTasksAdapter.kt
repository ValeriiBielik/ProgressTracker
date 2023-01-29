package com.bielik.progresstracker.feature.progress_task_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bielik.progresstracker.base.BaseAdapter
import com.bielik.progresstracker.databinding.ItemTicketBinding
import com.bielik.progresstracker.model.database.TicketTemplateModel

class ProgressTasksAdapter : BaseAdapter<TicketTemplateModel, ProgressTaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressTaskViewHolder {
        return ProgressTaskViewHolder(ItemTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

}