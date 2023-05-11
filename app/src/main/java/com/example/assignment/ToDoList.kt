package com.example.assignment

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment.databinding.ActivityMainBinding
import com.example.assignment.databinding.ActivityToDoListBinding

class ToDoList : AppCompatActivity(), ToDoClickListener {

    private lateinit var binding: ActivityToDoListBinding
    private lateinit var taskViewModel: TaskView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToDoListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        taskViewModel = ViewModelProvider(this).get(TaskView::class.java)
        binding.newTaskButton.setOnClickListener{
            CreateNewTask(null).show(supportFragmentManager, "createTaskTag")
        }

        setRecyclerView()
    }

    private fun setRecyclerView()
    {
        val main = this
        taskViewModel.ItemList.observe(this)
        {
            binding.todoRecycler.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = ItemAdapter(it, main)
            }
        }
    }

    override fun editItem(taskItem: TaskItem)
    {
        CreateNewTask(taskItem).show(supportFragmentManager, "newTaskTag")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun completeItem(taskItem: TaskItem)
    {
        taskViewModel.setCompleted(taskItem)
    }

    fun returnHome(view: View)
    {
    val intent = Intent(this, MainActivity2::class.java)
    startActivity(intent)
    }
}