package com.zen.cendakala.ui.survey.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zen.cendakala.R
import com.zen.cendakala.data.Result
import com.zen.cendakala.ui.auth.login.LoginUIEvent
import com.zen.cendakala.ui.components.ErrorDialog
import com.zen.cendakala.ui.components.PrimaryButton
import com.zen.cendakala.ui.components.SecondaryButton
import com.zen.cendakala.ui.components.TextTitle
import com.zen.cendakala.ui.theme.Black2
import com.zen.cendakala.ui.theme.Color4
import com.zen.cendakala.ui.theme.Gray
import com.zen.cendakala.ui.theme.White
import com.zen.cendakala.ui.theme.White2
import com.zen.cendakala.utils.Constants
import com.zen.cendakala.utils.ViewModelServerFactory

@Composable
fun SurveyDetailScreen(
    navController: NavController,
    surveyID: String,
) {
    val context = LocalContext.current
    val factory = remember { ViewModelServerFactory.getInstance(context) }
    val detailViewModel: SurveyDetailViewModel= viewModel(factory = factory)
    val detailResult by detailViewModel.detailResult.observeAsState()


    LaunchedEffect(key1 = surveyID) {
        detailViewModel.getById(surveyID)
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
                    .fillMaxSize()
            ) {
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
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Top
                ) {
                    Button(
                        onClick = {
                            navController.navigateUp()
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
                        title = stringResource(id = R.string.detail),
                        fontSize = 16,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 12.dp, start = 85.dp)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp, start = 30.dp, end = 30.dp)
                ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxSize(),
//                    horizontalArrangement = Arrangement.End,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Image(
//                        painter = painterResource(id = R.drawable.edit_icon),
//                        contentDescription = "edit",
//                        contentScale = ContentScale.Fit,
//                        modifier = Modifier
//                            .padding(end = 15.dp, bottom = 2.dp),
//                        colorFilter = ColorFilter.tint(White)
//                    )
//                    Image(
//                        painter = painterResource(id = R.drawable.delete_icon),
//                        contentDescription = "delete",
//                        contentScale = ContentScale.Fit,
//                        colorFilter = ColorFilter.tint(White)
//                    )
//                }
                    detailResult?.let { result ->
                        when (result) {
                            is Result.Success -> {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = result.data.survey.title,
                                        style = MaterialTheme.typography.titleSmall,
                                    )
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = 14.dp, bottom = 14.dp),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "SurveyID : ${result.data.survey.surveyID}",
                                        style = MaterialTheme.typography.labelSmall,
                                    )
                                }
//                Row(
//                    modifier = Modifier
//                        .fillMaxSize(),
//                    horizontalArrangement = Arrangement.End ,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        text = "Download responses",
//                        color = Black2,
//                        fontSize = 10.sp
//                    )
//                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Card(
                                        border = BorderStroke(1.dp, Gray),
                                        elevation = CardDefaults.cardElevation(10.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = White,
                                            contentColor = Black2,
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight()
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceAround,
                                        ) {
                                            Text(
                                                text = "Category",
                                                style = MaterialTheme.typography.titleSmall,
                                                modifier = Modifier
                                                    .padding(
                                                        top = 8.dp,
                                                        bottom = 16.dp,
                                                        start = 0.dp,
                                                        end = 0.dp
                                                    ),
                                            )
                                            Column(
                                                modifier = Modifier
                                                    .padding(
                                                        top = 8.dp,
                                                        bottom = 8.dp,
                                                        start = 0.dp,
                                                        end = 0.dp
                                                    ),
                                            ) {
                                                Text(
                                                    text = result.data.survey.category1,
                                                    style = MaterialTheme.typography.labelSmall,
                                                    )
                                                Spacer(modifier = Modifier.padding(2.5.dp))
                                                if (result.data.survey.category2 == "null") {
                                                    Text(
                                                        text = "",
                                                        letterSpacing = Constants.LETTER_SPACING.sp,
                                                    )
                                                } else {
                                                    Text(
                                                        text = result.data.survey.category2,
                                                        style = MaterialTheme.typography.labelSmall,
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }

                                Card(
                                    border = BorderStroke(1.dp, Gray),
                                    elevation = CardDefaults.cardElevation(10.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = White,
                                        contentColor = Black2,
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                        .padding(
                                            top = 10.dp,
                                            bottom = 0.dp,
                                            start = 0.dp,
                                            end = 0.dp
                                        )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                top = 0.dp,
                                                bottom = 0.dp,
                                                start = 0.dp,
                                                end = 28.dp
                                            ),
                                        horizontalArrangement = Arrangement.SpaceAround,
                                    ) {
                                        Text(
                                            text = "Quota",
                                            style = MaterialTheme.typography.titleSmall,
                                            modifier = Modifier
                                                .padding(
                                                    top = 8.dp,
                                                    bottom = 16.dp,
                                                    start = 0.dp,
                                                    end = 0.dp
                                                ),
                                        )
                                        Column(
                                            modifier = Modifier
                                                .padding(
                                                    top = 8.dp,
                                                    bottom = 16.dp,
                                                    start = 0.dp,
                                                    end = 0.dp
                                                ),
                                        ) {
                                            Row {
                                                Image(
                                                    painter = painterResource(id = R.drawable.ic_quota),
                                                    contentDescription = "quota",
                                                    contentScale = ContentScale.Inside,
                                                    modifier = Modifier
                                                        .padding(end = 8.dp)
                                                        .size(16.dp),
                                                )
                                                Text(
                                                    text = result.data.survey.quota.toString(),
                                                    style = MaterialTheme.typography.labelSmall,
                                                    modifier = Modifier.padding(start = 5.dp)
                                                )
                                            }
                                            Spacer(modifier = Modifier.padding(2.5.dp))
                                            Row {
                                                Image(
                                                    painter = painterResource(id = R.drawable.ic_money),
                                                    contentDescription = "money",
                                                    contentScale = ContentScale.Inside,
                                                    modifier = Modifier
                                                        .padding(end = 8.dp)
                                                        .size(16.dp),
                                                )
                                                Text(
                                                    text = result.data.survey.reward.toString(),
                                                    style = MaterialTheme.typography.labelSmall,
                                                    modifier = Modifier.padding(start = 5.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                                Card(
                                    border = BorderStroke(1.dp, Gray),
                                    elevation = CardDefaults.cardElevation(10.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = White,
                                        contentColor = Black2,
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                        .padding(
                                            top = 10.dp,
                                            bottom = 0.dp,
                                            start = 0.dp,
                                            end = 0.dp
                                        )
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                top = 12.dp,
                                                bottom = 12.dp,
                                                start = 18.dp,
                                                end = 28.dp
                                            ),
                                        horizontalAlignment = Alignment.Start,
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.description),
                                            style = MaterialTheme.typography.titleSmall,
                                        )
                                        Spacer(modifier = Modifier.padding(2.5.dp))
                                        Box(
                                            contentAlignment = Alignment.Center,
                                        ){
                                            Text(
                                                text = result.data.survey.description,
                                                textAlign = TextAlign.Justify,
                                                style = MaterialTheme.typography.labelSmall,
                                            )
                                        }
                                    }
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = 8.dp, bottom = 16.dp),
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Created at : ${result.data.survey.createdAt}",
                                        style = MaterialTheme.typography.labelSmall,
                                    )
                                }
                                Text(
                                    text = "Status : ${result.data.survey.finished} & ${result.data.survey.sell}",
                                )
                                if (result.data.survey.finished){
                                    Row(
                                        modifier = Modifier
                                            .padding(top = 8.dp, bottom = 8.dp),
                                    ) {
                                        PrimaryButton(
                                            text = stringResource(id = R.string.fill),
                                            onClickButton = {
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 32.dp, end = 32.dp, bottom = 50.dp),
                                        )
                                    }
                                }
                                if (result.data.survey.sell){
                                    Row() {
                                        SecondaryButton(
                                            text = stringResource(id = R.string.buy),
                                            onClickButton = {
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 32.dp, end = 32.dp, bottom = 50.dp),
                                        )
                                    }
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
                }

            }
        }
    }
}

@Preview
@Composable
fun SurveyDetailScreenPreview() {
    SurveyDetailScreen(navController = rememberNavController(), surveyID = "jihefikjasznfji212n1knJaSD1@")
}