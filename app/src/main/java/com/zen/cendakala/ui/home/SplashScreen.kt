package com.zen.cendakala.ui.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.zen.cendakala.R
import com.zen.cendakala.data.source.local.UserPreference
import com.zen.cendakala.route.Routes
import com.zen.cendakala.ui.components.ErrorDialog
import com.zen.cendakala.ui.theme.Color1
import com.zen.cendakala.utils.ViewModelServerFactory
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        Animatable(0.2f)
    }

    val context = LocalContext.current
    val factory = remember { ViewModelServerFactory.getInstance(context) }
    val homeViewModel: HomeViewModel = viewModel(factory = factory)
    val tokenResult by homeViewModel.tokenResult.observeAsState()
    val userPreference = UserPreference(context)
    val loginModel = userPreference.getUser()

    Image(
        painter = painterResource(id = R.drawable.bg_splash),
        contentDescription = "background",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
    )

    LaunchedEffect(key1 = true, block = {
        scale.animateTo(targetValue = 1f,
            animationSpec = tween(
                durationMillis = 2000,
            ))
        delay(2500L)

        if (loginModel.token != null) {
//            homeViewModel.cekToken()
//            tokenResult?.let { result ->
//                when (result) {
//                    is Result.Success -> {
//                        navController.navigate(Routes.Home.routes) {
//                            popUpTo(navController.graph.startDestinationId)
//                            launchSingleTop = true
//                        }
//                    }
//                    is Result.Error -> {
//                        result.data.body()?.newToken?.let { HomeViewModel.saveToken(context, it.token) }
//                        navController.navigate(Routes.Home.routes) {
//                            popUpTo(navController.graph.startDestinationId)
//                            launchSingleTop = true
//                        }
//                    }
//                    else -> {
//                        navController.navigate(Routes.Login.routes) {
//                            popUpTo(Routes.Splash.routes) {
//                                inclusive = true
//                            }
//                        }
//                    }
//                }
//            }
            navController.navigate(Routes.Home.routes) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        } else
        navController.navigate(Routes.Login.routes) {
            popUpTo(Routes.Splash.routes) {
                inclusive = true
            }
        }
    })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .scale(scale.value)
    ) {
        Row {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_cendakala),
                    contentDescription = "logo",
                    modifier = Modifier
                        .padding(24.dp),
                )
                Text(
                    text = "CENDAKALA",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color1,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    letterSpacing = 5.sp
                )
            }
        }
    }

}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen(navController = NavController(LocalContext.current))
}