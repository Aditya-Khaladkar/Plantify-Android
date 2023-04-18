package com.example.plantify.view.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.plantify.R
import com.example.plantify.databinding.ActivitySignUpBinding
import com.example.plantify.view.ui.Dashboard
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnSignUp.setOnClickListener {

            val email: String = binding.regEmail.text.toString()
            val password: String = binding.regPassword.text.toString()

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
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