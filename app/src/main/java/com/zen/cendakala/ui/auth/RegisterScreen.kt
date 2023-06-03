//@file:OptIn(ExperimentalMaterial3Api::class)
//
//package com.zen.cendakala.ui.auth
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.*
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import com.zen.cendakala.R
//import com.zen.cendakala.ui.components.ImageBackground
//import com.zen.cendakala.ui.components.OutlinedTextFieldCustom
//import com.zen.cendakala.ui.components.OutlinedTextFieldPasswordCustom
//import com.zen.cendakala.ui.components.PrimaryButton
//import com.zen.cendakala.ui.components.TextButtonCustom
//import com.zen.cendakala.ui.components.TextTitle
//
//@Composable
//fun RegisterScreen(navController: NavController) {
//    Box(
//        modifier = Modifier
//            .background(
//                color = Color.White,
//            )
//    ) {
//        ImageBackground(image = R.drawable.bg_auth)
//        TextTitle(
//            title = stringResource(id = R.string.register),
//            fontSize = 20,
//            modifier = Modifier
//                .padding(top = 144.dp, start = 38.dp, end = 38.dp)
//                .fillMaxSize(),
//            )
//        Box(
//            modifier = Modifier
//                /*.background(
//                    color = MaterialTheme.colorScheme.onPrimary,
//                    shape = RoundedCornerShape(25.dp, 5.dp, 25.dp, 5.dp)
//                )*/
//                .align(Alignment.BottomCenter),
//        ) {
//            Column(
//                modifier = Modifier
//                    .padding(16.dp)
//                    .fillMaxWidth()
//                    .verticalScroll(rememberScrollState())
//                ,
//
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                OutlinedTextFieldCustom(
//                    labelText = "Name",
//                    placeholderText = "Name",
//                    imeActionParam = ImeAction.Next,
//                    keyboardTypeParam = KeyboardType.Text,
//                    iconParam = R.drawable.name_icon,
//                    iconContentDescription = "name_icon",
//                )
//                Spacer(modifier = Modifier.padding(3.dp))
//                OutlinedTextFieldCustom(
//                    labelText = "Gender",
//                    placeholderText = "Gender",
//                    imeActionParam = ImeAction.Next,
//                    keyboardTypeParam = KeyboardType.Text,
//                    iconParam = R.drawable.gender_icon,
//                    iconContentDescription = "gender_icon",
//                )
//                Spacer(modifier = Modifier.padding(3.dp))
//                OutlinedTextFieldCustom(
//                    labelText = "Birthday",
//                    placeholderText = "Birthday",
//                    imeActionParam = ImeAction.Next,
//                    keyboardTypeParam = KeyboardType.Text,
//                    iconParam = R.drawable.birthday_icon,
//                    iconContentDescription = "birthday_icon",
//                )
//                Spacer(modifier = Modifier.padding(3.dp))
//                OutlinedTextFieldCustom(
//                    labelText = "Job",
//                    placeholderText = "Job",
//                    imeActionParam = ImeAction.Next,
//                    keyboardTypeParam = KeyboardType.Text,
//                    iconParam = R.drawable.job_icon,
//                    iconContentDescription = "job_icon",
//                )
//                Spacer(modifier = Modifier.padding(3.dp))
//                OutlinedTextFieldCustom(
//                    labelText = "Email",
//                    placeholderText = "Email",
//                    imeActionParam = ImeAction.Next,
//                    keyboardTypeParam = KeyboardType.Email,
//                    iconParam = R.drawable.email_icon,
//                    iconContentDescription = "email_icon",
//                )
//                Spacer(modifier = Modifier.padding(3.dp))
//                OutlinedTextFieldPasswordCustom(
//                    placeholderText = "Password"
//                )
//
//                Spacer(modifier = Modifier.padding(10.dp))
//                PrimaryButton(
//                    text = stringResource(id = R.string.signup),
//                    onClickButton = {
//                        navController.navigate("login_page") {
//                            popUpTo(navController.graph.startDestinationId)
//                            launchSingleTop = true
//                        }
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(start = 32.dp, end = 32.dp),
//                )
//                Spacer(modifier = Modifier.padding(3.dp))
//                Text(
//                    text = stringResource(id = R.string.already_have_an_account) ,
//                    style = MaterialTheme.typography.bodyMedium,
//                    letterSpacing = 1.sp,
//                )
//                TextButtonCustom(
//                    text = stringResource(id = R.string.signin),
//                    onClickButton = {
//                        navController.navigate("login_page") {
//                            popUpTo(navController.graph.startDestinationId)
//                            launchSingleTop = true
//                        }
//                    },
//                )
//            }
//
//        }
//
//    }
//}
//
//
////...........................................................................
//@Composable
//private fun GradientButton(
//    gradientColors: List<Color>,
//    cornerRadius: Dp,
//    nameButton: String,
//    roundedCornerShape: RoundedCornerShape
//) {
//
//    androidx.compose.material3.Button(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(start = 32.dp, end = 32.dp),
//        onClick = {
//            //your code
//        },
//
//        contentPadding = PaddingValues(),
//        colors = ButtonDefaults.buttonColors(
//            containerColor = Color.Transparent
//        ),
//        shape = RoundedCornerShape(cornerRadius)
//    ) {
//
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(
//                    brush = Brush.horizontalGradient(colors = gradientColors),
//                    shape = roundedCornerShape
//                )
//                .clip(roundedCornerShape)
//                /*.background(
//                    brush = Brush.linearGradient(colors = gradientColors),
//                    shape = RoundedCornerShape(cornerRadius)
//                )*/
//                .padding(horizontal = 16.dp, vertical = 8.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            androidx.compose.material3.Text(
//                text = nameButton,
//                fontSize = 20.sp,
//                color = Color.White
//            )
//        }
//    }
//}
//
//@Preview
//@Composable
//fun PreviewRegisterScreen() {
//    RegisterScreen(navController = rememberNavController())
//}