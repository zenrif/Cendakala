package com.zen.cendakala.utils

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zen.cendakala.data.repositories.SurveyRepository
import com.zen.cendakala.ui.auth.login.LoginViewModel
import com.zen.cendakala.di.Injection
import com.zen.cendakala.ui.auth.register.RegisterViewModel
import com.zen.cendakala.ui.home.HomeViewModel
import com.zen.cendakala.ui.survey.CreateSurveyViewModel

class ViewModelFactory private constructor(private val repo: SurveyRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repo) as T
        }
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
        }
    }
}