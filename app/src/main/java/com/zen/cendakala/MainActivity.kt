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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zen.cendakala.route.Routes
import com.zen.cendakala.ui.auth.login.LoginScreen
import com.zen.cendakala.ui.auth.register.InterestScreen
import com.zen.cendakala.ui.auth.register.RegisterScreen
import com.zen.cendakala.ui.components.BottomNavigationBar
import com.zen.cendakala.ui.home.HomeScreen
import com.zen.cendakala.ui.home.SplashScreen
import com.zen.cendakala.ui.theme.CendakalaTheme

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
        if (currentRoute != Routes.Splash.routes && currentRoute != Routes.Login.routes && currentRoute != Routes.Register.routes && currentRoute != Routes.Interest.routes) {
            BottomNavigationBar(navController)
        }
    }) { paddingValues ->
        NavHost(navController = navController, startDestination = Routes.Splash.routes) {
            composable(Routes.Splash.routes){
                SplashScreen(navController = navController)
            }
            composable(Routes.Login.routes){
                LoginScreen(navController = navController)
            }
            composable(Routes.Register.routes){
                RegisterScreen(navController = navController)
            }
            composable(Routes.Interest.routes){
                InterestScreen(navController = navController)
            }
            composable(Routes.Home.routes) {
                HomeScreen(navController = navController, paddingValuesBottom = paddingValues)
            }
        }
    }
}