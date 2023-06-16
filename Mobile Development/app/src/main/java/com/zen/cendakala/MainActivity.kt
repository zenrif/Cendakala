package com.zen.cendakala

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zen.cendakala.route.Routes
import com.zen.cendakala.ui.auth.login.LoginScreen
import com.zen.cendakala.ui.auth.register.InterestScreen
import com.zen.cendakala.ui.auth.register.RegisterScreen
import com.zen.cendakala.ui.components.BottomNavigationBar
import com.zen.cendakala.ui.history.HistorySurveyScreen
import com.zen.cendakala.ui.home.HomeScreen
import com.zen.cendakala.ui.home.SplashScreen
import com.zen.cendakala.ui.payment.PaymentSuccessScreen
import com.zen.cendakala.ui.profile.ProfileScreen
import com.zen.cendakala.ui.survey.SurveyScreen
import com.zen.cendakala.ui.survey.create.SurveyCreateOverviewScreen
import com.zen.cendakala.ui.survey.create.SurveyCreateQuestionScreen
import com.zen.cendakala.ui.survey.detail.SurveyDetailScreen
import com.zen.cendakala.ui.survey.fill.SurveyFillScreen
import com.zen.cendakala.ui.survey.fill.SurveyFillSuccessScreen
import com.zen.cendakala.ui.theme.CendakalaTheme
import com.zen.cendakala.ui.wallet.TopupScreen
import com.zen.cendakala.ui.wallet.WalletScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CendakalaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Dashboard()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(navController: NavHostController = rememberNavController()) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(bottomBar = {
        if (currentRoute != Routes.Splash.routes && currentRoute != Routes.Login.routes && currentRoute != Routes.Register.routes && currentRoute != Routes.Interest.routes && currentRoute != Routes.Detail.routes && currentRoute != Routes.SurveyFill.routes && currentRoute != Routes.SurveyCreateOverview.routes && currentRoute != Routes.SurveyCreateQuestion.routes && currentRoute != Routes.Success.routes && currentRoute != Routes.Topup.routes && currentRoute != Routes.PaymentSuccess.routes) {
            BottomNavigationBar(navController)
        }
    }) { paddingValues ->
        NavHost(navController = navController, startDestination = Routes.Splash.routes) {
            composable(Routes.Splash.routes) {
                SplashScreen(navController = navController)
            }
            composable(Routes.Login.routes) {
                LoginScreen(navController = navController)
            }
            composable(Routes.Register.routes) {
                RegisterScreen(navController = navController)
            }
            composable(Routes.Interest.routes) {
                InterestScreen(navController = navController)
            }
            composable(Routes.Home.routes) {
                HomeScreen(navController = navController, paddingValuesBottom = paddingValues)
            }
            composable(
                Routes.Detail.routes,
                arguments = listOf(navArgument("surveyID") { type = NavType.StringType })
            ) {
                val surveyID = it.arguments?.getString("surveyID") ?: ""
                SurveyDetailScreen(navController = navController, surveyID = surveyID)
            }
            composable(
                Routes.SurveyFill.routes,
                arguments = listOf(navArgument("surveyID") { type = NavType.StringType })
            ) {
                val surveyID = it.arguments?.getString("surveyID") ?: ""
                SurveyFillScreen(navController = navController, surveyID = surveyID)
            }
            composable(Routes.Survey.routes) {
                SurveyScreen(navController = navController, paddingValuesBottom = paddingValues)
            }
            composable(Routes.SurveyCreateOverview.routes) {
                SurveyCreateOverviewScreen(navController = navController)
            }
            composable(Routes.SurveyCreateQuestion.routes) {
                SurveyCreateQuestionScreen()
            }
            composable(Routes.Profile.routes) {
                ProfileScreen(navController = navController, paddingValuesBottom = paddingValues)
            }
            composable(Routes.Success.routes) {
                SurveyFillSuccessScreen(navController = navController)
            }
            composable(Routes.History.routes) {
                HistorySurveyScreen(
                    navController = navController,
                    paddingValuesBottom = paddingValues
                )
            }
            composable(Routes.Wallet.routes) {
                WalletScreen(navController = navController, paddingValuesBottom = paddingValues)
            }
            composable(Routes.Topup.routes) {
                TopupScreen(navController = navController)
            }
            composable(Routes.PaymentSuccess.routes) {
                PaymentSuccessScreen(navController = navController)
            }
        }
    }
}