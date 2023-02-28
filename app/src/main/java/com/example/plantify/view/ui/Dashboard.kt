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
import com.example.plantify.viewmodel.DashboardViewModel

class Dashboard : AppCompatActivity() {
    lateinit var binding: ActivityDashboardBinding
    private val REQUEST_IMAGE_CAPTURE = 1
    lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnOpenCamera.setOnClickListener {
            var intent : Intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"

            startActivityForResult(intent, 250)
        }

        binding.txtPrediction.setOnClickListener {
            val dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
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
        if(requestCode == 250){
            binding.imgView.setImageURI(data?.data)

            var uri : Uri?= data?.data
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
        }
        else if(requestCode == 200 && resultCode == Activity.RESULT_OK){
            bitmap = data?.extras?.get("data") as Bitmap
            binding.imgView.setImageBitmap(bitmap)
        }

    }
}