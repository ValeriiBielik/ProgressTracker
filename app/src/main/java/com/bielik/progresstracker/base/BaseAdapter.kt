package com.bielik.progresstracker.base

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView

@SuppressLint("NotifyDataSetChanged")
abstract class BaseAdapter<T, VH : BaseBindingViewHolder<T, *>> : RecyclerView.Adapter<VH>() {

    protected var list: MutableList<T> = ArrayList()

    open fun setData(data: MutableList<T>?) {
        data?.let {
            list.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun clearAndSetData(data: List<T>?) {
        data?.let {
            list.clear()
            list.addAll(it)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindData(list[position])
    }

    fun isEmpty() = list.isEmpty()
}