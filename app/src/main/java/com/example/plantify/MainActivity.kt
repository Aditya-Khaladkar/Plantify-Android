package com.example.plantify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.plantify.view.ui.Dashboard
import com.example.plantify.view.ui.WelcomeScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed(Runnable {
            startActivity(Intent(this, Dashboard::class.java))
            finish()
        },2000)
    }
}