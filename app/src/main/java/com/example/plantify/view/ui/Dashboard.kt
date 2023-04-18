package com.example.plantify.view.ui

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.plantify.databinding.ActivityDashboardBinding
import com.example.plantify.view.UploadDialogue
import com.example.plantify.viewmodel.DashboardViewModel
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class Dashboard : AppCompatActivity() {
    lateinit var binding: ActivityDashboardBinding

    lateinit var viewModel: DashboardViewModel

    private val REQUEST_IMAGE = 100
    var imageUri: Uri? = null
    var predictedImage: String? = null

    override fun onStart() {
        super.onStart()
        viewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        if (imageUri == null) {
            Toast.makeText(this, "image empty", Toast.LENGTH_LONG).show()
        } else {
            viewModel.addImageToServer(imageUri!!, this, binding.root)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnOpenCamera.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_IMAGE)
        }

        binding.btnPredict.setOnClickListener {

            UploadDialogue.setProgressDialog(this, "Predicting Image")

            val imageURL:String = viewModel.imageUrl.toString()


            viewModel.predict(imageURL, this)

            viewModel.predictionResult.observe(this, Observer {
                predictedImage = it

                val intent = Intent(this, PredictedResult::class.java)
                intent.putExtra("prediction", predictedImage)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.data!!
            binding.imgView.setImageURI(imageUri)
        }
    }
}