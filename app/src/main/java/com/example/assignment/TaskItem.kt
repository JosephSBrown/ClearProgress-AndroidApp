package com.example.assignment

import android.content.Context
import androidx.core.content.ContextCompat
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

class TaskItem (
    var name: String,
    var desc: String,
    var completionExpect: LocalTime?,
    var DateOfCompletion: LocalDate?,
    var id: UUID = UUID.randomUUID()
    )
    {
        fun isChecked() = DateOfCompletion != null
        fun source(): Int = if (isChecked())
        {
            R.drawable.confirm_24
        }
        else
        {
            R.drawable.unconfirmed_24
        }

        fun imageColor(context: Context): Int = if(isChecked())
        {
            purple(context)
        }
        else
        {
            black(context)
        }

        private fun purple(context: Context) = ContextCompat.getColor(context, R.color.purple_500)
        private fun black(context: Context) = ContextCompat.getColor(context, R.color.black)
    }