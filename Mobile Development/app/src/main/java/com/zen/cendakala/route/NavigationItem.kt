package com.zen.cendakala.route

import com.zen.cendakala.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Home : NavigationItem(Routes.Home.routes, R.drawable.home_icon, "Home")
    object Survey : NavigationItem(Routes.Survey.routes, R.drawable.ic_survey, "Survey")
    object History : NavigationItem(Routes.History.routes, R.drawable.ic_history, "History")
    object Wallet : NavigationItem(Routes.Wallet.routes, R.drawable.ic_wallet, "Wallet")
    object Profile : NavigationItem(Routes.Profile.routes, R.drawable.ic_profile, "Profile")
}
