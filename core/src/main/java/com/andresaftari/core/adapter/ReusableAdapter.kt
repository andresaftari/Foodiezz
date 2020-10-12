package com.andresaftari.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ReusableAdapter<T>(
    var layout: Int,
    private var items: ArrayList<T>,
    var view: (View, T) -> Unit,
    var handler: (Int, T) -> Unit
) : RecyclerView.Adapter<ReusableAdapter.ViewHolder<T>>() {
    fun setData(list: List<T>?) {
        if (list.isNullOrEmpty()) return
        items.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder<T>(
            LayoutInflater
                .from(parent.context)
                .inflate(this.layout, parent, false)
        )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        val item = this.items[position]
        holder.apply {
            bind(item, view)
            itemView.setOnClickListener { handler(position, item) }
        }
    }

    class ViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: T, view: (View, T) -> Unit) = view(itemView, item)
    }
}