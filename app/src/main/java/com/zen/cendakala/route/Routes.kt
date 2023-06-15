package com.zen.cendakala.route

sealed class Routes(val routes: String) {
    object Splash : Routes("splash")
    object Home : Routes("home")
    object Login : Routes("login")
    object Register : Routes("register")
    object Interest : Routes("interest")
    object Survey : Routes("survey")
    object Detail : Routes("detail/{surveyID}") {
        fun createRoute(surveyID: String) = "detail/$surveyID"
    }
    object SurveyFill : Routes("surveyFill/{surveyID}") {
        fun createRoute(surveyID: String) = "surveyFill/$surveyID"
    }
    object SurveyCreateOverview : Routes("surveyCreateOverview")
    object SurveyCreateQuestion : Routes("surveyCreateQuestion")
    object Success : Routes("success")
    object History : Routes("history")
    object Wallet : Routes("wallet")
    object Topup : Routes("topup")
    object Profile : Routes("profile")
    object PaymentSuccess : Routes("paymentSuccess")
}