package ru.pcs.cs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView

class Adapter: RecyclerView.Adapter<Adapter.ViewHolder>() {
    var list: List<String> = arrayListOf()
    var selectionTracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.itemview, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Adapter.ViewHolder, position: Int) {
        val name = list[position]
        selectionTracker?.let {
            holder.bind(name, it.isSelected(position.toLong()))
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var textView: TextView = itemView.findViewById(R.id.textView)

        fun bind(name: String, isActivated: Boolean = false) {
            textView.text = name
            itemView.isActivated = isActivated
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long>
        = object : ItemDetailsLookup.ItemDetails<Long>() {
            override fun getSelectionKey(): Long? = itemId
            override fun getPosition(): Int = adapterPosition
        }
    }
}