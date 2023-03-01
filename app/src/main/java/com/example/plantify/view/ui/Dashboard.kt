package com.example.plantify.view.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.plantify.databinding.ActivityDashboardBinding
import com.example.plantify.repository.MLRepository
import com.example.plantify.util.MLFactory
import com.example.plantify.viewmodel.DashboardViewModel

class Dashboard : AppCompatActivity() {
    lateinit var binding: ActivityDashboardBinding
    lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnOpenCamera.setOnClickListener {

            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"

            startActivityForResult(intent, 250)
        }

        binding.btnPredict.setOnClickListener {

            // Calling viewmodel

            val factory = MLFactory(MLRepository())

            val dashboardViewModel = ViewModelProvider(this, factory)[DashboardViewModel::class.java]
            dashboardViewModel.predict(
                binding = binding,
                context = this,
                bitmap = bitmap,
                application = application
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 250) {
            binding.imgView.setImageURI(data?.data)

            val uri: Uri? = data?.data
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
            bitmap = data?.extras?.get("data") as Bitmap
            binding.imgView.setImageBitmap(bitmap)
        }

    }
}