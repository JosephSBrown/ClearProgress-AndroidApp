package com.example.assignment

import android.content.Context
import android.graphics.Paint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.databinding.TodolayoutBinding
import java.time.format.DateTimeFormatter

class ItemViewHolder(
    private val context: Context,
    private val binding: TodolayoutBinding,
    private val clickListener: ToDoClickListener
): RecyclerView.ViewHolder(binding.root) {

    @RequiresApi(Build.VERSION_CODES.O)
    private val time = DateTimeFormatter.ofPattern("HH:mm")

    @RequiresApi(Build.VERSION_CODES.O)
    fun bindItem(taskItem: TaskItem)
    {
        binding.itemName.text = taskItem.name

        if (taskItem.isChecked())
        {
            binding.itemName.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.itemExpectComplete.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }

        binding.checkButton.setImageResource(taskItem.source())
        binding.checkButton.setColorFilter(taskItem.imageColor(context))

        binding.checkButton.setOnClickListener{
            clickListener.completeItem(taskItem)
        }
        binding.taskContainer.setOnClickListener{
            clickListener.editItem(taskItem)
        }

        if (taskItem.completionExpect != null)
        {
            binding.itemExpectComplete.text = time.format(taskItem.completionExpect)
        }
        else
        {
            binding.itemExpectComplete.text = ""
        }
    }
}