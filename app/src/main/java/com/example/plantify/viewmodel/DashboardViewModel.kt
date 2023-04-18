package com.example.plantify.viewmodel

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.plantify.view.UploadDialogue
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.time.LocalDateTime

class DashboardViewModel : ViewModel() {
    private val _predictionResult = MutableLiveData<String>()
    val predictionResult: LiveData<String> = _predictionResult

    var imageUrl: String? = null

    // firebase dependency
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    @RequiresApi(Build.VERSION_CODES.O)
    fun addImageToServer(uri: Uri, context: Context, view: View) = viewModelScope.launch {

        UploadDialogue.setProgressDialog(context, "uploading image to server")

        val reference: StorageReference = FirebaseStorage.getInstance().reference

        val ref = reference.child("plantImages").child(auth.currentUser?.uid.toString())
            .child("${LocalDateTime.now()}.png")
        ref.putFile(uri).addOnSuccessListener {
            ref.downloadUrl.addOnSuccessListener {

                imageUrl = it.toString()

                val map = HashMap<String, Any>()
                map["imageUrl"] = it

                firestore.collection("my_images")
                    .document(auth.currentUser?.uid.toString())
                    .collection(auth.currentUser?.uid.toString())
                    .document()
                    .set(map)
                    .addOnSuccessListener {
                        UploadDialogue.dialog.dismiss()
                        Snackbar.make(view, "Image uploaded to server", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show()
                    }
                    .addOnFailureListener {

                    }
            }.addOnFailureListener {

            }
        }
    }

    fun predict(imageURL: String, context: Context) {
        // Build URL
        val detectURL =
            "https://detect.roboflow.com/medicinal-and-poisonous-plant-detection/1?api_key=LA6XF6D0fG9te0bgMRws"
        val query = "&image=" + URLEncoder.encode(imageURL, "utf-8")
        val urlString = detectURL + query

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, urlString, null,
            { response ->
                val predictions = response.getJSONArray("predictions")
                val prediction = predictions.getJSONObject(0)
                val predictedClass = prediction.getString("class")
                _predictionResult.value = predictedClass
                Log.d("@debug", "predict: $response")
            },
            { error ->
                Log.d("@debug", "onCreate: $error")
            }
        )

        Volley.newRequestQueue(context).add(jsonObjectRequest)
    }
}