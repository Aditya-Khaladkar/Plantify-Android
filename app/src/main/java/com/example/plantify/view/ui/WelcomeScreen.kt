package com.example.plantify.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.plantify.R
import com.example.plantify.databinding.ActivityWelcomeScreenBinding
import com.example.plantify.view.auth.SignIn
import com.example.plantify.view.auth.SignUp

class WelcomeScreen : AppCompatActivity() {
    lateinit var binding: ActivityWelcomeScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnWlcLogin.setOnClickListener {
            startActivity(Intent(this, SignIn::class.java))
        }

        binding.btnWlcSignUp.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }
    }
}