package com.zen.cendakala.ui.survey

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.zen.cendakala.R
import com.zen.cendakala.route.Routes
import com.zen.cendakala.ui.components.CardSurvey
import com.zen.cendakala.ui.components.ImageBackground
import com.zen.cendakala.ui.components.SearchField
import com.zen.cendakala.ui.components.TextTitle
import com.zen.cendakala.ui.theme.*
import com.zen.cendakala.utils.ViewModelServerFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurveyScreen(
    navController : NavController,
    paddingValuesBottom: PaddingValues,
) {
    val searchText = remember {
        mutableStateOf("")
    }
    val columnState = rememberLazyListState()

    val context = LocalContext.current
    val factory = remember { ViewModelServerFactory.getInstance(context) }
    val surveyViewModel: SurveyViewModel = viewModel(factory = factory)
    val surveys = surveyViewModel.getSurveys().collectAsLazyPagingItems()

    Box(
        modifier = Modifier
            .background(
                color = White2,
            )
    ) {
        ImageBackground(image = R.drawable.bg_home)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 44.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Top
        ) {
            TextTitle(
                title = stringResource(id = R.string.survey),
                fontSize = 16,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 12.dp, end = 80.dp)
            )

            Button(
                onClick = {
                    navController.navigate(Routes.SurveyCreateOverview.routes)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color4,
                    contentColor = Black2
                ),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "add",
                )
            }
        }
        Box(
            modifier = Modifier
                /*.background(
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(25.dp, 5.dp, 25.dp, 5.dp)
                )*/
                .padding(top = 180.dp)
                .align(Alignment.CenterStart)

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 18.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(start = 22.dp, end = 40.dp),
                ){
                    SearchField(
                        placeholder = "Cari...",
                        value = "",
                        onValueChange = {  },
                        onClear = { }
                    )
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = White2)
                        .padding(
                            top = 16.dp,
                            bottom = paddingValuesBottom.calculateBottomPadding()
                        ),
                    state = columnState,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(
                        count = surveys.itemCount,
                        key = { index -> surveys[index]?.surveyID ?: index }
                    ) { index ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .background(color = White2)
                        ) {
                            CardSurvey(
                                title = surveys[index]?.title ?: "",
                                category1 = surveys[index]?.category1 ?: "",
                                category2 = surveys[index]?.category2 ?: "",
                                quota = surveys[index]?.quota ?: 0,
                                reward = surveys[index]?.reward ?: 0,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .padding(top = 0.dp, bottom = 0.dp, start = 8.dp, end = 26.dp)
                                    .clickable {
                                        navController.navigate(Routes.Detail.createRoute(surveys[index]?.surveyID ?: ""))
                                    },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SurveyScreenPreview() {
    SurveyScreen(navController = NavController(LocalContext.current), paddingValuesBottom = PaddingValues())
}

