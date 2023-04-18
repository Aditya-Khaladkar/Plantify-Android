package com.example.plantify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.plantify.view.ui.Dashboard
import com.example.plantify.view.ui.WelcomeScreen
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed(Runnable {
            if (FirebaseAuth.getInstance().currentUser?.uid != null) {
                startActivity(Intent(this, Dashboard::class.java))
                finish()
            } else{
                startActivity(Intent(this, WelcomeScreen::class.java))
                finish()
            }
        },2000)
    }
}