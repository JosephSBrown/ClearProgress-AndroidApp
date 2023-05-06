package com.example.assignment

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class TaskView :ViewModel() {
    var ItemList = MutableLiveData<MutableList<TaskItem>>()

    init {
        ItemList.value = mutableListOf()
    }

    fun addItem(newTask: TaskItem)
    {
        val list = ItemList.value
        list!!.add(newTask)
        ItemList.postValue(list)
    }

    fun updateItem(id: UUID, name: String, desc: String, completionExpected: LocalTime?)
    {
        val list = ItemList.value
        val item = list!!.find{it.id == id}
        item!!.name = name
        item!!.desc = desc
        item!!.completionExpect = completionExpected
        ItemList.postValue(list)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setCompleted(taskItem: TaskItem)
    {
        val list = ItemList.value
        val task = list!!.find{it.id == taskItem.id}
        if (task!!.DateOfCompletion == null)
        {
            task!!.DateOfCompletion = LocalDate.now()
        }
        ItemList.postValue(list)
    }

}