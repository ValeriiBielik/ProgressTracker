package com.bielik.progresstracker.base

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseBindingViewHolder<in D, VB : ViewBinding>(protected open val binding: VB) :
    RecyclerView.ViewHolder(binding.root) {

    abstract fun bindData(item: D?)

    fun withBinding(block: (VB.() -> Unit)) {
        binding.apply {
            block.invoke(this)
        }
    }

    protected val context: Context
        get() = itemView.context
}
