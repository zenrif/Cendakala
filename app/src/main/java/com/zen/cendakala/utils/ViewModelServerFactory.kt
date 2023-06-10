package com.zen.cendakala.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zen.cendakala.data.repositories.SurveyRepository
import com.zen.cendakala.di.Injection
import com.zen.cendakala.ui.home.HomeViewModel
import com.zen.cendakala.ui.survey.CreateSurveyViewModel

class ViewModelServerFactory private constructor(private val repo: SurveyRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repo) as T
        }
        if (modelClass.isAssignableFrom(CreateSurveyViewModel::class.java)) {
            return CreateSurveyViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelServerFactory? = null
        fun getInstance(context: Context): ViewModelServerFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelServerFactory(Injection.provideServerRepository(context))
            }.also { instance = it }
        }
    }
}