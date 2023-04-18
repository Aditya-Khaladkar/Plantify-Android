package com.example.plantify.view.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.plantify.R
import com.example.plantify.databinding.ActivitySignInBinding
import com.example.plantify.view.ui.Dashboard
import com.google.firebase.auth.FirebaseAuth

class SignIn : AppCompatActivity() {
    lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnLogin.setOnClickListener {

            val email: String = binding.logEmail.text.toString()
            val password: String = binding.logPassword.text.toString()

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    startActivity(Intent(this, Dashboard::class.java))
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
                }
        }
    }
}