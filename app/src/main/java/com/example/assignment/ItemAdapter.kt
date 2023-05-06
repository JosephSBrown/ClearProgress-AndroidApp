package com.example.assignment

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.databinding.TodolayoutBinding

class ItemAdapter(
    private val taskItems: List<TaskItem>,
    private val clickListener: ToDoClickListener
): RecyclerView.Adapter<ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = TodolayoutBinding.inflate(from, parent, false)
        return ItemViewHolder(parent.context, binding, clickListener)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItem(taskItems[position])
    }

    override fun getItemCount(): Int = taskItems.size

}