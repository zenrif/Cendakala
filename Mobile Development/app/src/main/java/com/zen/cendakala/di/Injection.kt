package com.zen.cendakala.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.zen.cendakala.data.repositories.AuthRepository
import com.zen.cendakala.data.repositories.SurveyRepository
import com.zen.cendakala.data.source.local.UserPreference
import com.zen.cendakala.data.source.remote.ApiConfig

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("surveys")

object Injection {
    fun provideRepository(context: Context): AuthRepository {
        val preferences = UserPreference(context)
        val apiService = ApiConfig.getApiService()
        return AuthRepository.getInstance(preferences, apiService)
    }

    fun provideServerRepository(context: Context): SurveyRepository {
        val preferences = UserPreference(context)
        val apiService = ApiConfig.serverApiService()
        return SurveyRepository.getInstance(preferences, apiService)
    }

}