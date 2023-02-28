package com.example.plantify.viewmodel

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantify.databinding.ActivityDashboardBinding
import com.example.plantify.ml.MobilenetV110224Quant
import kotlinx.coroutines.launch
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class DashboardViewModel : ViewModel() {

    fun predict(
        binding: ActivityDashboardBinding,
        context: Context,
        bitmap: Bitmap,
        application: Application
    ) = viewModelScope.launch {

        val labels =
            application.assets.open("labels.txt").bufferedReader().use { it.readText() }.split("\n")

        val resized = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
        val model = MobilenetV110224Quant.newInstance(context)

        var tbuffer = TensorImage.fromBitmap(resized)
        var byteBuffer = tbuffer.buffer

        // Creates inputs for reference.
        val inputFeature0 =
            TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.UINT8)
        inputFeature0.loadBuffer(byteBuffer)

        // Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        val max = getMax(outputFeature0.floatArray)

        binding.txtPrediction.text = labels[max]

        // Releases model resources if no longer used.
        model.close()
    }

    private fun getMax(arr: FloatArray): Int {
        var ind = 0;
        var min = 0.0f;

        for (i in 0..1000) {
            if (arr[i] > min) {
                min = arr[i]
                ind = i;
            }
        }
        return ind
    }
}