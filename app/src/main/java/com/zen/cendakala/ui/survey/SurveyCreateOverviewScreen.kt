package com.zen.cendakala.ui.survey

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zen.cendakala.R
import com.zen.cendakala.ui.components.DropdownCustom
import com.zen.cendakala.ui.components.OutlinedTextFieldSurvey
import com.zen.cendakala.ui.components.TextButtonCustom
import com.zen.cendakala.ui.components.TextTitle
import com.zen.cendakala.ui.theme.Black2
import com.zen.cendakala.ui.theme.Color4
import com.zen.cendakala.ui.theme.White2
import com.zen.cendakala.utils.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurveyCreateOverviewScreen(
    navController: NavController,
) {
    val context = LocalContext.current
    val factory = remember { ViewModelFactory.getInstance(context) }
    val createSurveyViewModel: CreateSurveyViewModel = viewModel(factory = factory)
    val pattern = remember { Regex("^\\d+\$") }

    var valueQuota by remember { mutableStateOf("") }
    var valueReward by remember { mutableStateOf("") }

    val title = remember { createSurveyViewModel.title.value }
    var quota = remember { createSurveyViewModel.quota.value }
    var reward = remember { createSurveyViewModel.reward.value }
    var category1 = remember { createSurveyViewModel.category1.value }
    var category2 = remember { createSurveyViewModel.category2.value }
    val description = remember { createSurveyViewModel.description.value }

    Box(
        modifier = Modifier
            .background(
                color = Color.White,
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_home),
            contentDescription = "background",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize(),
            alignment = Alignment.TopCenter
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 44.dp),
            horizontalArrangement = Arrangement.SpaceBetween ,
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
                title = stringResource(id = R.string.overview),
                fontSize = 16,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 12.dp)
            )
                TextButtonCustom(
                    text = stringResource(id = R.string.done),
                    onClickButton = {
                        createSurveyViewModel.quota.value  = valueQuota.toInt()
                        createSurveyViewModel.reward.value  = valueReward.toLong()
                        createSurveyViewModel.onEvent(CreateSurveyUIEvent.Done1ButtonClicked)
                        Toast.makeText(context, "Done WOOOOOI", Toast.LENGTH_SHORT).show()
//                        navController.navigate(Routes.SurveyCreateQuestion.routes)
                    },
                )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 100.dp, start = 30.dp, end = 30.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = "delete",
                        contentScale = ContentScale.Fit,
                        colorFilter = ColorFilter.tint(Black2),
                        modifier = Modifier
                            .size(32.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp) ,
                    horizontalArrangement = Arrangement.Start,
                ) {
                    OutlinedTextFieldSurvey(
                        labelText = "Title",
                        placeholderText = "Write anything ...",
                        onTextChanged = {
                            createSurveyViewModel.title.value = it
                        },
                        imeActionParam = ImeAction.Done,
                        keyboardTypeParam = KeyboardType.Text,
                        maxLine = 3,
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp) ,
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Text(
                        text = "Category",
                        color = Black2,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                        modifier = Modifier
                            .padding(start = 12.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.Start ,
                ) {

                    DropdownCustom(
                        onSelectedChange = { createSurveyViewModel.category1.value  = it },
                        modifier = Modifier
                            .fillMaxSize(0.5f)
                            .padding(end = 12.dp)
                    )

                    DropdownCustom(
                        onSelectedChange = { createSurveyViewModel.category2.value  = it },
                        modifier = Modifier
                            .fillMaxHeight(0.5f)
                        )
                }
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp) ,
                    horizontalArrangement = Arrangement.SpaceBetween ,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(0.5f)
                            .padding(end = 6.dp)
                    ) {
                        Text(
                            text = "Quota",
                            color = Black2,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                            modifier = Modifier
                                .padding(start = 12.dp)
                        )
                        TextField(
                            value = valueQuota,
                            onValueChange = {
                                if (it.isEmpty() || it.matches(pattern)) {
                                valueQuota = it
                            }},
                            label = { Text("input amount") },
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = White2,
                                textColor = Black2,
                                cursorColor = Black2,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                            )
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(0.5f)
                            .padding(start = 6.dp)
                    ) {
                        Text(
                            text = "Reward",
                            color = Black2,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                        )
                        TextField(
                            value = valueReward,
                            onValueChange = {
                                if (it.isEmpty() || it.matches(pattern)) {
                                valueReward = it
                            } },
                            label = { Text("input amount") },
                            maxLines = 1,
//                            textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = White2,
                                textColor = Black2,
                                cursorColor = Black2,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                            )
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp) ,
                    horizontalArrangement = Arrangement.Start ,
                ) {
                    OutlinedTextFieldSurvey(
                        labelText = "Description",
                        placeholderText = "Write anything ...",
                        onTextChanged = {
                            createSurveyViewModel.description.value = it
                        },
                        imeActionParam = ImeAction.Done,
                        keyboardTypeParam = KeyboardType.Text,
                        maxLine = 10,
                        minHeightLine = 280
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SurveyCreateOverviewScreenPreview() {
    SurveyCreateOverviewScreen(navController = rememberNavController())
}