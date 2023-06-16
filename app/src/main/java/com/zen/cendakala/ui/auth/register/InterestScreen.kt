package com.zen.cendakala.ui.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.zen.cendakala.R
import com.zen.cendakala.data.Result
import com.zen.cendakala.route.Routes
import com.zen.cendakala.ui.components.CheckBoxCustom
import com.zen.cendakala.ui.components.ErrorDialog
import com.zen.cendakala.ui.components.ImageBackground
import com.zen.cendakala.ui.components.PrimaryButton
import com.zen.cendakala.ui.components.TextTitle
import com.zen.cendakala.ui.theme.Black2
import com.zen.cendakala.ui.theme.Color4
import com.zen.cendakala.utils.ViewModelFactory

@Composable
fun InterestScreen(navController: NavController) {
    val context = LocalContext.current
    val factory = remember { ViewModelFactory.getInstance(context) }
    val registerViewModel: RegisterViewModel = viewModel(factory = factory)

    val registerResult by registerViewModel.registerResult.observeAsState()

    val isCheckedMap = remember { mutableStateMapOf<String, Boolean>() }
    val isChecked = remember { mutableStateOf(false) }
//    val selectedInterests = registerViewModel.selectedInterests.entries.forEach() { isCheckedMap[it.value] = isChecked.value }
    Box(
        modifier = Modifier
            .background(
                color = Color.White,
            )
    ) {
        ImageBackground(image = R.drawable.bg_home)
//        if (registerResult is Result.Error) {
//            ErrorDialog(
//                message = (registerResult as Result.Error).exception.message.toString(),
//                onDismiss = {
//                    registerViewModel.resetRegisterResult()
//                }
//            )
//        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 44.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Button(
                onClick = {
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color4,
                    contentColor = Black2
                ),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = stringResource(id = R.string.back),
                )
            }
            TextTitle(
                title = stringResource(id = R.string.interest),
                fontSize = 16,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxSize(),
            )
        }
        Box(
            modifier = Modifier
                /*.background(
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(25.dp, 5.dp, 25.dp, 5.dp)
                )*/
                .padding(top = 160.dp)
                .align(Alignment.CenterStart)
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 0.dp, bottom = 0.dp, start = 28.dp, end = 0.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start
            ) {
                val category = arrayListOf(
                    "Kesehatan",
                    "Pendidikan",
                    "Hukum",
                    "Keuangan",
                    "Pariwisata",
                    "Sosial dan Kemanusiaan",
                    "Lingkungan dan Konversi",
                    "Teknologi Informasi dan Komunikasi",
                    "Olahraga dan Rekreasi",
                    "Seni dan Budaya",
                    "Agama dan Kepercayaan",
                    "Bisnis dan Industri",
                    "Politik dan Pemerintahan",
                    "Transportasi dan Logistik",
                    "Pertanian dan Logistik"
                )
                val categoryMap = category.mapIndexed { index, value ->
                    value to (index + 1).toString()
                }.toMap()
                categoryMap.forEach { (interest: String, id: String) ->
                    Row {
                        CheckBoxCustom(
                            option = interest,
                            isCheckedValue = isChecked.value,
                            onCheckedChange = {
//                                registerViewModel.toggleInterest(interest, id)
                            }
                        )
                    }
                }
//                val selectedOptions = remember {
//                    derivedStateOf {
//                        val selectedOptionsMap = mutableMapOf<String, String>()
//                        var index = 1
//                        isCheckedMap.entries.forEach { entry ->
//                            if (entry.value) {
//                                selectedOptionsMap[index.toString()] = entry.key
//                                index++
//                            }
//                        }
//                        selectedOptionsMap
//                    }
//                }
//                LaunchedEffect(Unit) {
//                    registerViewModel.onEvent(RegisterUIEvent.InterestChanged(selectedOptions.value))
//                }
//                val interests: List<String> = registerViewModel.selectedInterests.entries.map { it.value }
//                Text(
//                    text = interests.joinToString(", "),
//                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
//                    color = Color.Gray,
//                )
                PrimaryButton(
                    text = stringResource(id = R.string.finish),
                    onClickButton = {
                        registerViewModel.onEvent(RegisterUIEvent.RegisterButtonClicked)
                    },
                    isEnabled = isCheckedMap.values.any { it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp, end = 32.dp, bottom = 32.dp),
                )
                registerResult?.let { result ->
                    when (result) {
                        is Result.Success -> {
                            RegisterViewModel.saveToken(
                                context,
                                result.data.body()?.token.toString()
                            )
                            navController.navigate(Routes.Home.routes) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }

                        is Result.Error -> {
                            result.data.body()?.let {
                                ErrorDialog(
                                    message = it.message,
                                    image = R.drawable.error_form,
                                )
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun InterestScreenPreview() {
    InterestScreen(navController = NavController(LocalContext.current))
}