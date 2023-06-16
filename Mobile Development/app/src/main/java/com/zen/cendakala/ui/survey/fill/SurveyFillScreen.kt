package com.zen.cendakala.ui.survey.fill

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.zen.cendakala.R
import com.zen.cendakala.data.Result
import com.zen.cendakala.route.Routes
import com.zen.cendakala.ui.components.ErrorDialog
import com.zen.cendakala.ui.components.PrimaryButton
import com.zen.cendakala.ui.components.TextTitle
import com.zen.cendakala.ui.theme.White2
import com.zen.cendakala.utils.ViewModelServerFactory

@Composable
fun SurveyFillScreen(navController: NavController, surveyID: String) {

    val context = LocalContext.current
    val factory = remember { ViewModelServerFactory.getInstance(context) }
    val detailViewModel: SurveyFillViewModel = viewModel(factory = factory)
    val detailResult by detailViewModel.detailResult.observeAsState()
    val answerResult by detailViewModel.answerResult.observeAsState()

    LaunchedEffect(key1 = surveyID) {
        detailViewModel.getById(surveyID = surveyID)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = White2,
            )
    ) {
        item {
            Box(
                modifier = Modifier
                    .background(color = White2)
            )
            {
                Image(
                    painter = painterResource(id = R.drawable.bg_home),
                    contentDescription = "background",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxSize(),
                    alignment = Alignment.TopCenter
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Top
                ) {
                    TextTitle(
                        title = stringResource(id = R.string.question),
                        fontSize = 16,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 12.dp)
                    )
                }
            }
            detailResult?.let { result ->
                when (result) {
                    is Result.Success -> {
                        result.data.survey.questions.forEach { (questionNumber, question) ->
                            when (question.type) {
                                "multiple" -> SurveyFillChoiceScreen(questionNumber, question)
                                "essay" -> SurveyFillEssayScreen(questionNumber, question)
                            }
                        }
                        Row(
                            modifier = Modifier
                                .padding(top = 8.dp, bottom = 8.dp),
                        ) {
                            PrimaryButton(
                                text = stringResource(id = R.string.submit),
                                onClickButton = {
                                    detailViewModel.submitAnswer(
                                        surveyID = surveyID,
                                        reward = result.data.survey.reward
                                    )
//                                    navController.navigate(Routes.Success.routes) {
//                                        popUpTo(navController.graph.startDestinationId)
//                                        launchSingleTop = true
//                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = 26.dp,
                                        start = 32.dp,
                                        end = 32.dp,
                                        bottom = 26.dp
                                    ),
                            )
                            answerResult?.let { result ->
                                when (result) {
                                    is Result.Success -> {
                                        navController.navigate(Routes.Success.routes) {
                                            popUpTo(navController.graph.startDestinationId)
                                            launchSingleTop = true
                                        }
                                    }

                                    is Result.Error -> {
                                        ErrorDialog(
                                            message = result.data.message,
                                            image = R.drawable.error_form,
                                        )
                                    }

                                    else -> {
                                        ErrorDialog(
                                            message = "Error",
                                            image = R.drawable.error_form,
                                        )
                                    }
                                }
                            }
                        }
                    }

                    is Result.Error -> {
                        Text(
                            text = result.data.message,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .padding(16.dp)
                        )
                    }

                    else -> {}
                }
            }
        }
    }
}

@Preview
@Composable
fun SurveyFillScreenPreview() {
    SurveyFillScreen(navController = NavController(LocalContext.current), surveyID = "1")
}
