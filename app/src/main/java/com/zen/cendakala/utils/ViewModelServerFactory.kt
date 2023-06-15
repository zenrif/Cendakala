package com.zen.cendakala.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zen.cendakala.data.repositories.SurveyRepository
import com.zen.cendakala.di.Injection
import com.zen.cendakala.ui.history.HistoryViewModel
import com.zen.cendakala.ui.home.HomeViewModel
import com.zen.cendakala.ui.profile.ProfileViewModel
import com.zen.cendakala.ui.survey.create.CreateSurveyViewModel
import com.zen.cendakala.ui.survey.SurveyViewModel
import com.zen.cendakala.ui.survey.detail.SurveyDetailViewModel
import com.zen.cendakala.ui.survey.fill.SurveyFillViewModel

class ViewModelServerFactory private constructor(private val repo: SurveyRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repo) as T
        }
        if (modelClass.isAssignableFrom(SurveyViewModel::class.java)) {
            return SurveyViewModel(repo) as T
        }
        if (modelClass.isAssignableFrom(CreateSurveyViewModel::class.java)) {
            return CreateSurveyViewModel(repo) as T
        }
        if (modelClass.isAssignableFrom(SurveyDetailViewModel::class.java)) {
            return SurveyDetailViewModel(repo) as T
        }
        if (modelClass.isAssignableFrom(SurveyFillViewModel::class.java)) {
            return SurveyFillViewModel(repo) as T
        }
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(repo) as T
        }
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(repo) as T
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