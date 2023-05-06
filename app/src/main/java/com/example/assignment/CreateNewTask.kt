package com.example.assignment

import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.assignment.databinding.FragmentCreateNewTaskBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalTime

class CreateNewTask(var taskItem: TaskItem?) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentCreateNewTaskBinding
    private lateinit var taskViewModel: TaskView
    private var deadline: LocalTime? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activity = requireActivity()

        if  (taskItem != null)
        {
            binding.titletext.text = "Edit Task"
            val editable = Editable.Factory.getInstance()
            binding.taskTitle.text = editable.newEditable(taskItem!!.name)
            binding.taskDesc.text = editable.newEditable((taskItem!!.desc))
            if (taskItem!!.completionExpect != null)
            {
                deadline = taskItem!!.completionExpect
                updateTimeButtonText()
            }
        }
        else
        {
            binding.titletext.text = "New Task"
        }

        taskViewModel = ViewModelProvider(activity)[TaskView::class.java]
        binding.saveTask.setOnClickListener{
            saveAction()
        }
        binding.setTime.setOnClickListener{
            openTimePicker()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun openTimePicker() {
        if (deadline == null)
        {
            deadline = LocalTime.now()
        }

        val listener = TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
            deadline = LocalTime.of(selectedHour, selectedMinute)
            updateTimeButtonText()
        }
        val dialog = TimePickerDialog(activity, listener, deadline!!.hour, deadline!!.minute, true)
        dialog.setTitle("Deadline for Completion")
        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateTimeButtonText() {
        binding.setTime.text = String.format("%02d:%02d", deadline!!.hour, deadline!!.minute)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateNewTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun saveAction(){
        val name = binding.taskTitle.text.toString()
        val desc = binding.taskDesc.text.toString()
        if (taskItem == null)
        {
            val newTask = TaskItem(name, desc, deadline, null)
            taskViewModel.addItem(newTask)
        }
        else
        {
            taskViewModel.updateItem(taskItem!!.id, name, desc, deadline)
        }

        binding.taskTitle.setText("")
        binding.taskDesc.setText("")
        dismiss()
    }
}