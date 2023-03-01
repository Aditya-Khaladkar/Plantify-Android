package com.example.plantify.viewmodel

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantify.databinding.ActivityDashboardBinding
import com.example.plantify.repository.MLRepository
import kotlinx.coroutines.launch

class DashboardViewModel(private val mlRepository: MLRepository) : ViewModel() {

    fun predict(
        binding: ActivityDashboardBinding,
        context: Context,
        bitmap: Bitmap,
        application: Application
    ) = viewModelScope.launch {
        mlRepository.predict(binding, context, bitmap, application)
    }
}