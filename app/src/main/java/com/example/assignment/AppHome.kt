package com.example.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_app_home)

        val buttonClick = findViewById<Button>(R.id.open_cal)
        buttonClick.setOnClickListener{
            val intent = Intent(this, Calendar::class.java)
            startActivity(intent)
        }
    }
}