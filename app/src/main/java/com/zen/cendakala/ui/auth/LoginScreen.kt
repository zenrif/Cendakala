@file:OptIn(ExperimentalMaterial3Api::class)

package com.zen.cendakala.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zen.cendakala.R
import com.zen.cendakala.ui.components.ImageBackground
import com.zen.cendakala.ui.components.OutlinedTextFieldCustom
import com.zen.cendakala.ui.components.OutlinedTextFieldPasswordCustom
import com.zen.cendakala.ui.components.PrimaryButton
import com.zen.cendakala.ui.components.TextButtonCustom
import com.zen.cendakala.ui.components.TextTitle
import com.zen.cendakala.ui.theme.Black2
import com.zen.cendakala.ui.theme.Color1
import com.zen.cendakala.utils.CapsText
import com.zen.cendakala.utils.Constants

@Composable
fun LoginScreen(navController: NavController) {
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
                /*.background(
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(25.dp, 5.dp, 25.dp, 5.dp)
                )*/
                .align(Alignment.Center),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                ,

                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom

            ) {
                Spacer(modifier = Modifier.padding(120.dp))
                OutlinedTextFieldCustom(
                    labelText = "Email",
                    placeholderText = "Email",
                    imeActionParam = ImeAction.Next,
                    keyboardTypeParam = KeyboardType.Email,
                    iconParam = R.drawable.email_icon,
                    iconContentDescription = "email_icon",
                )
                Spacer(modifier = Modifier.padding(3.dp))
                OutlinedTextFieldPasswordCustom(
                    placeholderText = "Password"
                )
                Spacer(modifier = Modifier.padding(3.dp))
                TextButtonCustom(
                    text = stringResource(id = R.string.forgot_password),
                    onClickButton = {
                        navController.navigate("forgot_password_page"){
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 16.dp),
                    )

                Spacer(modifier = Modifier.padding(40.dp))
                PrimaryButton(
                    text = stringResource(id = R.string.signin),
                    onClickButton = {
                    navController.navigate("home_page"){
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    } },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp, end = 32.dp),
                )
                Spacer(modifier = Modifier.padding(16.dp))
                    Text(
                        text = stringResource(id = R.string.dont_have_account) ,
                        style = MaterialTheme.typography.bodyMedium,
                        letterSpacing = 1.sp,
                    )
                    TextButtonCustom(
                        text = stringResource(id = R.string.signup),
                        onClickButton = {
                            navController.navigate("register_page") {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                    )
            }

        }

    }
}


//...........................................................................
@Composable
private fun GradientButton(
    gradientColors: List<Color>,
    cornerRadius: Dp,
    nameButton: String,
    roundedCornerShape: RoundedCornerShape
) {

    androidx.compose.material3.Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp),
        onClick = {
            //your code
        },

        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(cornerRadius)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(colors = gradientColors),
                    shape = roundedCornerShape
                )
                .clip(roundedCornerShape)
                /*.background(
                    brush = Brush.linearGradient(colors = gradientColors),
                    shape = RoundedCornerShape(cornerRadius)
                )*/
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Text(
                text = nameButton,
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}


//email id
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SimpleOutlinedTextFieldSample() {
    val keyboardController = LocalSoftwareKeyboardController.current
    var text by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        shape = RoundedCornerShape(topEnd =12.dp, bottomStart =12.dp),
        label = {
            Text("Name or Email Address",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelMedium,
            ) },
        placeholder = { Text(text = "Name or Email Address") },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.primary),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(0.8f),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                // do something here
            }
        )
    )
}

//password
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SimpleOutlinedPasswordTextField() {
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }
    TextField(value = "Enter Password",
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        leadingIcon = {
            IconButton(onClick = {
                passwordVisibility = !passwordVisibility
            }) {
                Icon( painter = painterResource(id = R.drawable.lock_icon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        onValueChange = { })
}

@Preview
@Composable
fun Preview() {
    LoginScreen(navController = rememberNavController())
}