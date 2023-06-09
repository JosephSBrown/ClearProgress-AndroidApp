package com.example.assignment

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment.databinding.ActivityRemindersBinding
import java.util.*

class Reminders : AppCompatActivity()
{
    private lateinit var binding: ActivityRemindersBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRemindersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()
        binding.scheduleButton.setOnClickListener {
            scheduleNotification()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun scheduleNotification()
    {
        val intent = Intent(applicationContext, Notification::class.java)
        val title = binding.reminderTitle.text.toString()
        val message = binding.reminderMessage.text.toString()
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
        showAlert(time, title, message)
    }

    private fun showAlert(time: Long, title: String, message: String)
    {
        val date = Date(time)
        val dateFormat = DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = DateFormat.getTimeFormat(applicationContext)

        AlertDialog.Builder(this)
            .setTitle("Notification Scheduled")
            .setMessage(
                "Title" + title +
                        "\nMessage" + message +
                        "\nAt" + dateFormat.format(date) + " " + timeFormat.format(date)
            )
            .setPositiveButton("Okay"){_,_ ->}
            .show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getTime(): Long
    {
        val minute = binding.timePicker.minute
        val hour = binding.timePicker.hour
        val day = binding.datePicker.dayOfMonth
        val month = binding.datePicker.month
        val year = binding.datePicker.year

        val calendar = java.util.Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        return calendar.timeInMillis
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel()
    {
        val  name = "Notif Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun returnHome() {
        startActivity(Intent(this@Reminders, MainActivity2::class.java))
    }
}