package com.example.fetchrewardstest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter (private val items: MutableList<Item>)
    : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>()
{

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currItem = items[position]
        holder.itemView.apply {

            val name : TextView = findViewById<TextView>(R.id.name)
            val listId : TextView = findViewById(R.id.list_id)

            name.text = currItem.name
            listId.text = currItem.listId.toString()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}