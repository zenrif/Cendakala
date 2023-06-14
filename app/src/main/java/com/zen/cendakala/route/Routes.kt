package com.zen.cendakala.route

sealed class Routes(val routes: String) {
    object Splash : Routes("splash")
    object Home : Routes("home")
    object Login : Routes("login")
    object Register : Routes("register")
    object Interest : Routes("interest")
    object Survey : Routes("survey")
    object SurveyDetail : Routes("surveyDetail")
    object SurveyCreateOverview : Routes("surveyCreateOverview")
    object SurveyCreateQuestion : Routes("surveyCreateQuestion")
    object History : Routes("history")
    object Wallet : Routes("wallet")
    object Profile : Routes("profile")
    object Detail : Routes("detail/{surveyID}") {
        fun createRoute(surveyID: String) = "detail/$surveyID"
    }
}