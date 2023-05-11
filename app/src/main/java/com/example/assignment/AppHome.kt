package com.example.assignment

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.assignment.databinding.ActivityAppHomeBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.json.JSONObject
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection
import java.lang.Thread
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainActivity2 : AppCompatActivity() {

    val api: String = "6606e6ca0d0b29cd7007c5ddedc73d97"

    private lateinit var binding: ActivityAppHomeBinding

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private var lastLongitude: Double = 0.0
    private var lastLatitude: Double = 0.0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = ActivityAppHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        fetchLocation()

        val buttonClick = findViewById<Button>(R.id.open_cal)
        buttonClick.setOnClickListener {
            val intent = Intent(this, Calendar::class.java)
            startActivity(intent)
        }

        val anotherButtonClick = findViewById<Button>(R.id.open_todo)
        anotherButtonClick.setOnClickListener{
                val intent = Intent(this, ToDoList::class.java)
                startActivity(intent)
            }

        val oneMoreButtonClick = findViewById<Button>(R.id.open_notes)
        oneMoreButtonClick.setOnClickListener{
            val intent = Intent(this, NoteSection::class.java)
            startActivity(intent)
        }

        fetchWeather().start()

    }

    private fun fetchLocation()
    {
        val task = fusedLocationProviderClient.lastLocation

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
            return
        }
        task.addOnSuccessListener {
            if (it != null)
            {
                lastLatitude = it.latitude
                lastLongitude = it.longitude
                //Toast.makeText(applicationContext, "$lastLongitude $lastLatitude", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchWeather() : Thread
    {
        return Thread {
            val url = URL("https://api.openweathermap.org/data/2.5/weather?lat=$lastLatitude&lon=$lastLongitude&appid=$api")
            val connection = url.openConnection() as HttpsURLConnection

            if (connection.responseCode == 200)
            {
                val json = url.readText(Charsets.UTF_8)
                val jsonObj = JSONObject(json)

                updateUI(jsonObj)
            }
            else
            {
                println(url)
                println("Error!")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateUI(jsonObj: JSONObject)
    {
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        val main = jsonObj.getJSONObject("main")
        val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
        val temp = main.getDouble("temp")
        val newtemp = temp - 273.15
        val roundedtemp = newtemp.toInt()
        val mintemp = main.getDouble("temp_min")
        val newmintemp = mintemp - 273.15
        val roundedmintemp = newmintemp.toInt()
        val maxtemp = main.getDouble("temp_max")
        val newmaxtemp = maxtemp - 273.15
        val roundedmaxtemp = newmaxtemp.toInt()
        runOnUiThread {
            kotlin.run {
                binding.locationText.text = jsonObj.getString("name")
                binding.lastUpdateText.text = "Last Update: " + LocalTime.now().format(formatter)
                binding.temperatureText.text = roundedtemp.toString() + "°C"
                binding.minimumTemperatureText.text = "Min: " + roundedmintemp.toString() + "°C"
                binding.maximumTemperatureText.text = "Max: " + roundedmaxtemp.toString() + "°C"
                binding.statusText.text = weather.getString("description").capitalize()
            }
        }
    }

    fun open_reminders(view: View)
    {
        val intent = Intent(this, Reminders::class.java)
        startActivity(intent)
    }

}