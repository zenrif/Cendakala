package com.zen.cendakala.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.zen.cendakala.R
import com.zen.cendakala.data.Result
import com.zen.cendakala.route.Routes
import com.zen.cendakala.ui.components.ErrorDialog
import com.zen.cendakala.ui.components.ImageBackground
import com.zen.cendakala.ui.components.OutlinedTextFieldCustom
import com.zen.cendakala.ui.components.PrimaryButton
import com.zen.cendakala.ui.components.SecondaryButton
import com.zen.cendakala.ui.components.TextTitle
import com.zen.cendakala.ui.theme.White2
import com.zen.cendakala.utils.ViewModelServerFactory

@Composable
fun ProfileScreen(
    navController: NavController,
    paddingValuesBottom: PaddingValues,
) {
    val context = LocalContext.current
    val factory = remember { ViewModelServerFactory.getInstance(context) }
    val profileViewModel: ProfileViewModel = viewModel(factory = factory)

    val profileResult by profileViewModel.profileResult.observeAsState()

    LaunchedEffect(key1 = 0) {
        profileViewModel.getUserProfile()
    }

    Box(
        modifier = Modifier
            .background(
                color = White2,
            )
            .padding(bottom = paddingValuesBottom.calculateBottomPadding())
    ) {
        ImageBackground(image = R.drawable.bg_home)
        TextTitle(
            title = stringResource(id = R.string.profile),
            fontSize = 16,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 52.dp)
        )
        Box(
            modifier = Modifier
                .align(Alignment.Center),
        ) {
            profileResult?.let { result ->
                when (result) {
                    is Result.Success -> {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Bottom

                        ) {
                            Spacer(modifier = Modifier.padding(120.dp))
                            OutlinedTextFieldCustom(
                                labelText = result.data.body()?.user?.name ?: "",
                                placeholderText = result.data.body()?.user?.name ?: "",
                                isEnable = false,
                                onTextChanged = {

                                },
                                imeActionParam = ImeAction.Next,
                                keyboardTypeParam = KeyboardType.Text,
                                iconParam = R.drawable.ic_name,
                                iconContentDescription = "name_icon",
                            )
                            Spacer(modifier = Modifier.padding(3.dp))
                            OutlinedTextFieldCustom(
                                labelText = result.data.body()?.user?.gender ?: "",
                                placeholderText = result.data.body()?.user?.gender ?: "",
                                isEnable = false,
                                onTextChanged = {

                                },
                                imeActionParam = ImeAction.Next,
                                keyboardTypeParam = KeyboardType.Text,
                                iconParam = R.drawable.ic_gender,
                                iconContentDescription = "gender_icon",
                            )
                            Spacer(modifier = Modifier.padding(3.dp))
                            OutlinedTextFieldCustom(
                                labelText = result.data.body()?.user?.job ?: "",
                                placeholderText = result.data.body()?.user?.job ?: "",
                                isEnable = false,
                                onTextChanged = {
                                },
                                imeActionParam = ImeAction.Next,
                                keyboardTypeParam = KeyboardType.Text,
                                iconParam = R.drawable.ic_job,
                                iconContentDescription = "job_icon",
                            )

//                loginResult?.let { result ->
//                    when (result) {
//                        is Result.Success -> {
//                            LoginViewModel.saveToken(context, result.data)
//                            navController.navigate(Routes.Home.routes) {
//                                popUpTo(navController.graph.startDestinationId)
//                                launchSingleTop = true
//                            }
//                        }
//
//                        is Result.Error -> {
//                            val errorMessage = result.data
//                            println(errorMessage)
//                            loginResult?.let {
//                                Text(text = it.toString())
//                            }
//                            ErrorDialog(
//                                message = result.data.message ,
//                                image = R.drawable.error_form,
//                            )
//                        }
//
//                        is Result.Failure -> {
//                            ErrorDialog(
//                                message = result.exception.message ?: "Error" ,
//                                image = R.drawable.error_form,
//                            )
//                        }
//
//                        else -> {}
//                    }
//                }

                        }

                    }

                    is Result.Error -> {
                        val errorMessage = result.data
                        println(errorMessage)
                        profileResult?.let {
                            Text(text = it.toString())
                        }
                        ErrorDialog(
                            message = result.data.body()?.message ?: "Error",
                            image = R.drawable.error_form,
                        )
                    }

                    is Result.Failure -> {
                        ErrorDialog(
                            message = result.exception.message ?: "Error",
                            image = R.drawable.error_form,
                        )
                    }

                    else -> {
                    }
                }
            }
            Spacer(modifier = Modifier.padding(40.dp))
            PrimaryButton(
                text = stringResource(id = R.string.edit_profile),
                onClickButton = {
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp),
            )
            SecondaryButton(
                text = stringResource(id = R.string.logout),
                onClickButton = {
                    profileViewModel.logout(context)
                    navController.navigate(Routes.Login.routes) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp),
            )
        }
    }
}