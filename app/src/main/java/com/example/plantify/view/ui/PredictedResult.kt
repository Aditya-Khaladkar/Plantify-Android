package com.example.plantify.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.plantify.R
import com.example.plantify.databinding.ActivityPredictedResultBinding

class PredictedResult : AppCompatActivity() {
    lateinit var binding: ActivityPredictedResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPredictedResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val prediction = intent.getStringExtra("prediction")

        binding.txtPredictImage.text = prediction
    }
}