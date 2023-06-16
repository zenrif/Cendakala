@file:OptIn(ExperimentalMaterial3Api::class)

package com.zen.cendakala.ui.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.zen.cendakala.R
import com.zen.cendakala.data.Result
import com.zen.cendakala.route.Routes
import com.zen.cendakala.ui.components.ErrorDialog
import com.zen.cendakala.ui.components.ImageBackground
import com.zen.cendakala.ui.components.OutlinedTextFieldCustom
import com.zen.cendakala.ui.components.OutlinedTextFieldPasswordCustom
import com.zen.cendakala.ui.components.PrimaryButton
import com.zen.cendakala.ui.components.TextButtonCustom
import com.zen.cendakala.ui.components.TextTitle
import com.zen.cendakala.ui.theme.Color4
import com.zen.cendakala.utils.ViewModelFactory


@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val factory = remember { ViewModelFactory.getInstance(context) }
    val loginViewModel: LoginViewModel = viewModel(factory = factory)

    val loginResult by loginViewModel.loginResult.observeAsState()
    Box(
        modifier = Modifier
            .background(
                color = Color.White,
            )
    ) {
        ImageBackground(image = R.drawable.bg_auth)
        TextTitle(
            title = stringResource(id = R.string.signin),
            fontSize = 20,
            modifier = Modifier
                .padding(top = 144.dp)
                .fillMaxSize(),
        )
        Box(
            modifier = Modifier
                .align(Alignment.Center),
        ) {
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
                    labelText = "Email",
                    placeholderText = "Email",
                    onTextChanged = {
                        loginViewModel.onEvent(LoginUIEvent.EmailChanged(it))
                    },
                    errorStatus = loginViewModel.loginUIState.value.emailError,
                    imeActionParam = ImeAction.Next,
                    keyboardTypeParam = KeyboardType.Email,
                    iconParam = R.drawable.ic_email,
                    iconContentDescription = "email_icon",
                )
                Spacer(modifier = Modifier.padding(3.dp))
                OutlinedTextFieldPasswordCustom(
                    placeholderText = "Password",
                    onTextChanged = {
                        loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it))
                    },
                    errorStatus = loginViewModel.loginUIState.value.passwordError,
                )
                Spacer(modifier = Modifier.padding(3.dp))
                TextButtonCustom(
                    text = stringResource(id = R.string.forgot_password),
                    onClickButton = {
//                        navController.navigate("forgot_password_page"){
//                            popUpTo(navController.graph.startDestinationId)
//                            launchSingleTop = true
//                        }
                    },
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 16.dp),
                )

                Spacer(modifier = Modifier.padding(40.dp))
                PrimaryButton(
                    text = stringResource(id = R.string.signin),
                    onClickButton = {
                        loginViewModel.onEvent(LoginUIEvent.LoginUIButtonClicked)
                    },
                    isEnabled = loginViewModel.allValidationsPassed.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp, end = 32.dp),
                )

                loginResult?.let { result ->
                    when (result) {
                        is Result.Success -> {
                            result.data.body()?.let { LoginViewModel.saveToken(context, it.token) }
                            navController.navigate(Routes.Home.routes) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }

                        is Result.Error -> {
//                            val errorMessage = result.data
//                            println(errorMessage)
//                            loginResult?.let {
//                                Text(text = it.toString())
//                            }
                            ErrorDialog(
                                message = result.data.body()?.message ?: "Error",
                                image = R.drawable.error_form,
                            )
                        }

                        is Result.Failure -> {
                            ErrorDialog(
                                message = result.exception.message ?: "Error Exception",
                                image = R.drawable.error_form,
                            )
                        }

                        is Result.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(50.dp)
                                    .padding(10.dp),
                                color = Color4,
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(16.dp))
                Text(
                    text = stringResource(id = R.string.dont_have_account),
                    style = MaterialTheme.typography.bodyMedium,
                    letterSpacing = 1.sp,
                )
                TextButtonCustom(
                    text = stringResource(id = R.string.signup),
                    onClickButton = {
                        navController.navigate(Routes.Register.routes) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                )
            }

        }

    }
}


@Preview
@Composable
fun Preview() {
    LoginScreen(navController = NavController(LocalContext.current))
}