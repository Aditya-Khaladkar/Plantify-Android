package com.example.plantify.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.plantify.repository.MLRepository
import com.example.plantify.viewmodel.DashboardViewModel

class MLFactory(private val mlRepository: MLRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DashboardViewModel(mlRepository = mlRepository) as T
    }
}