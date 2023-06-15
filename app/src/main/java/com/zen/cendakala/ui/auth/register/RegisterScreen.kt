@file:OptIn(ExperimentalMaterial3Api::class)
package com.zen.cendakala.ui.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.navigation.compose.rememberNavController
import com.zen.cendakala.R
import com.zen.cendakala.data.Result
import com.zen.cendakala.route.Routes
import com.zen.cendakala.ui.components.Category
import com.zen.cendakala.ui.components.DropdownCustom
import com.zen.cendakala.ui.components.ErrorDialog
import com.zen.cendakala.ui.components.ImageBackground
import com.zen.cendakala.ui.components.MultiComboBox
import com.zen.cendakala.ui.components.OutlinedTextFieldCustom
import com.zen.cendakala.ui.components.OutlinedTextFieldPasswordCustom
import com.zen.cendakala.ui.components.PrimaryButton
import com.zen.cendakala.ui.components.TextButtonCustom
import com.zen.cendakala.ui.components.TextTitle
import com.zen.cendakala.ui.theme.Black2
import com.zen.cendakala.ui.theme.White2
import com.zen.cendakala.utils.ViewModelFactory

@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current
    val factory = remember { ViewModelFactory.getInstance(context) }
    val registerViewModel: RegisterViewModel = viewModel(factory = factory)
    val registerResult by registerViewModel.registerResult.observeAsState()

    val category = listOf(
        Category(1, "Kesehatan"),
        Category(2, "Pendidikan"),
        Category(3, "Hukum"),
        Category(4, "Keuangan"),
        Category(5, "Pariwisata"),
        Category(6, "Sosial dan Kemanusiaan"),
        Category(7, "Lingkungan dan Konversi"),
        Category(8, "Teknologi Informasi dan Komunikasi"),
        Category(9, "Olahraga dan Rekreasi"),
        Category(10, "Seni dan Budaya"),
        Category(11, "Agama dan Kepercayaan"),
        Category(12, "Bisnis dan Industri"),
        Category(13, "Politik dan Pemerintahan"),
        Category(14, "Transportasi dan Logistik"),
        Category(15, "Pertanian dan Logistik")
    )

    val gender = arrayOf("Male", "Female")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(gender[0]) }
    Box(
        modifier = Modifier
            .background(
                color = Color.White,
            )
    ) {
        ImageBackground(image = R.drawable.bg_auth)
        TextTitle(
            title = stringResource(id = R.string.register),
            fontSize = 20,
            modifier = Modifier
                .padding(top = 144.dp, start = 38.dp, end = 38.dp)
                .fillMaxSize(),
            )
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                ,

                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextFieldCustom(
                    labelText = "Name",
                    placeholderText = "Name",
                    onTextChanged = {
                        registerViewModel.onEvent(RegisterUIEvent.NameChanged(it))
                    },
                    errorStatus = registerViewModel.registerUIState.value.nameError,
                    imeActionParam = ImeAction.Next,
                    keyboardTypeParam = KeyboardType.Text,
                    iconParam = R.drawable.ic_name,
                    iconContentDescription = "name_icon",
                )
                Spacer(modifier = Modifier.padding(3.dp))
                DropdownCustom(labelText = "Gender", registerViewModel = registerViewModel)
                Spacer(modifier = Modifier.padding(3.dp))
                OutlinedTextFieldCustom(
                    labelText = "Job",
                    placeholderText = "Job",
                    onTextChanged = {
                        registerViewModel.onEvent(RegisterUIEvent.JobChanged(it))
                    },
                    errorStatus = registerViewModel.registerUIState.value.jobError,
                    imeActionParam = ImeAction.Next,
                    keyboardTypeParam = KeyboardType.Text,
                    iconParam = R.drawable.ic_job,
                    iconContentDescription = "job_icon",
                )
                Spacer(modifier = Modifier.padding(3.dp))
                OutlinedTextFieldCustom(
                    labelText = "Email",
                    placeholderText = "Email",
                    onTextChanged = {
                        registerViewModel.onEvent(RegisterUIEvent.EmailChanged(it))
                    },
                    errorStatus = registerViewModel.registerUIState.value.emailError,
                    imeActionParam = ImeAction.Next,
                    keyboardTypeParam = KeyboardType.Email,
                    iconParam = R.drawable.ic_email,
                    iconContentDescription = "email_icon",
                )
                Spacer(modifier = Modifier.padding(3.dp))
                OutlinedTextFieldPasswordCustom(
                    placeholderText = "Password",
                    onTextChanged = {
                        registerViewModel.onEvent(RegisterUIEvent.PasswordChanged(it))
                    },
                    errorStatus = registerViewModel.registerUIState.value.passwordError,
                )
                Spacer(modifier = Modifier.padding(3.dp))
                MultiComboBox(labelText = "Interest",
                    options = List(category.size) { Category(it, category[it].text)},
                    onOptionsChosen = {
                        registerViewModel.onEvent(RegisterUIEvent.InterestChanged(it))
                    },
                )
                Spacer(modifier = Modifier.padding(10.dp))
                PrimaryButton(
                    text = stringResource(id = R.string.signup),
                    onClickButton = {
                        registerViewModel.onEvent(RegisterUIEvent.RegisterButtonClicked)
                    },
                    isEnabled = registerViewModel.allValidationsPassed.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp, end = 32.dp),
                )
                registerResult?.let { result ->
                    when (result) {
                        is Result.Success -> {
                            RegisterViewModel.saveToken(context, result.data)
                            navController.navigate(Routes.Home.routes) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }

                        is Result.Error -> {
                            ErrorDialog(
                                message = result.data.message ,
                                image = R.drawable.error_form,
                            )
                        }

                        else -> {}
                    }
                }
                Spacer(modifier = Modifier.padding(3.dp))
                Text(
                    text = stringResource(id = R.string.already_have_an_account) ,
                    style = MaterialTheme.typography.bodyMedium,
                    letterSpacing = 1.sp,
                )
                TextButtonCustom(
                    text = stringResource(id = R.string.signin),
                    onClickButton = {
                        navController.navigate(Routes.Login.routes) {
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
fun PreviewRegisterScreen() {
    RegisterScreen(navController = rememberNavController())
}