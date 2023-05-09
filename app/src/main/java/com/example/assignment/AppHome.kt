package com.example.assignment

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class MainActivity2 : AppCompatActivity() {

    //val city: String = "Ramsey, UK"
    val api: String = "API_HERE"

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    var lastLongitude: Double = 0.0
    var lastLatitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_app_home)

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

        weather().execute()
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
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class weather() : AsyncTask<String, Void, String>()
    {
        override fun onPreExecute()
        {
            super.onPreExecute()
            findViewById<TextView>(R.id.status_text).visibility = View.VISIBLE
        }

        override fun doInBackground(vararg p0: String?) : String?
        {
            var response: String?
            try
            {
                response = URL("https://api.openweathermap.org/data/2.5/weather?lat=(35.6762)&lon=(139.6503)&appid=$api").readText(Charsets.UTF_8)
            }
            catch(e: java.lang.Exception)
            {
                response = null;
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            try
            {
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                val updated:Long = jsonObj.getLong("uk")
                val updatedText = "Updated At: " + SimpleDateFormat("dd/mm/yyyy hh:mm:ss a", Locale.ENGLISH).format(Date(updated*1000))
                val temp = main.getString("temp") + "°C"
                val tempmin = "Min Temp: " + main.getString("temp_min") + "°C"
                val tempmax = "Max Temp: " + main.getString("temp_max") + "°C"
                val status = weather.getString("description")
                val location = jsonObj.getString("name") + ", " + sys.getString("country")

                findViewById<TextView>(R.id.location_text).text = location.capitalize()
                findViewById<TextView>(R.id.last_update_text).text = updatedText
                findViewById<TextView>(R.id.status_text).text = status
                findViewById<TextView>(R.id.temperature_text).text = temp
                findViewById<TextView>(R.id.minimum_temperature_text).text = tempmin
                findViewById<TextView>(R.id.maximum_temperature_text).text = tempmax
            }
            catch (e: java.lang.Exception)
            {
                findViewById<TextView>(R.id.location_text).text = "ERROR!"
            }
        }

    }

}