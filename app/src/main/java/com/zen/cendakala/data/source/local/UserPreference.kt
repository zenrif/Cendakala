package com.zen.cendakala.data.source.local

import android.content.Context
import com.zen.cendakala.data.model.LoginModel
import com.zen.cendakala.data.model.SurveyModel
import com.zen.cendakala.ui.auth.login.LoginViewModel

class UserPreference(context: Context) {
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setLogin(value: LoginModel) {
        val editor = preferences.edit()
        editor.putString(TOKEN, value.token)
        editor.apply()
    }

    fun getUser(): LoginModel {
        val token = preferences.getString(TOKEN, null)
        return LoginModel(token)
    }

    fun removeUser() {
        val editor = preferences.edit().clear()
        editor.apply()
    }

    fun saveSurvey(it: SurveyModel) {
        val editor = preferences.edit()
        editor.putString(SURVEY, it.toString())
        editor.apply()
    }

    companion object {
        private const val PREFS_NAME = "user_preference"
        private const val TOKEN = "token"
        private const val SURVEY = "survey"
    }
}