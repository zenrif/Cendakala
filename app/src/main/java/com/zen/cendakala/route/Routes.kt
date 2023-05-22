package com.zen.cendakala.route

sealed class Routes(val routes: String) {
    object Splash : Routes("splash")
    object Home : Routes("home")
    object Survey : Routes("survey")
    object History : Routes("history")
    object Wallet : Routes("wallet")
    object Profile : Routes("profile")
    object Detail : Routes("detail/{id}") {
        fun createRoute(id: Int) = "detail/$id"
    }
}