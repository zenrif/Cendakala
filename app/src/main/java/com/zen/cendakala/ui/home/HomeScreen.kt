package com.zen.cendakala.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.zen.cendakala.R
import com.zen.cendakala.ui.components.CardSurvey
import com.zen.cendakala.ui.components.CardSurveyRow
import com.zen.cendakala.ui.components.ImageBackground
import com.zen.cendakala.ui.components.SearchField
import com.zen.cendakala.ui.components.TextButtonCustom
import com.zen.cendakala.ui.theme.*
import com.zen.cendakala.utils.ViewModelFactory
import com.zen.cendakala.utils.ViewModelServerFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController : NavController,
    paddingValuesBottom: PaddingValues,
) {
    val searchText = remember {
        mutableStateOf("")
    }
    val listState = rememberLazyListState()
    val context = LocalContext.current
    val factory = remember { ViewModelServerFactory.getInstance(context) }
    val homeViewModel: HomeViewModel = viewModel(factory = factory)
    val surveys = homeViewModel.getSurveys().collectAsLazyPagingItems()

    Box(
        modifier = Modifier
            .background(
                color = Color.White,
            )
    ) {
        ImageBackground(image = R.drawable.bg_home)
        Column(
            modifier = Modifier
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                .fillMaxSize(),
        ) {
            SearchField(
                placeholder = "Cari berdasarkan nama karakter...",
                value = "",
                onValueChange = {  },
                onClear = { }
            )
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.End,
            ) {
                TextButtonCustom(
                    text = stringResource(id = R.string.filter),
                    onClickButton = { /*TODO*/ },
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_filter),
                    contentDescription = "filter",
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(Black2),
                    modifier = Modifier
                        .size(28.dp)
                        .padding(top = 8.dp)

                )
            }
        }

//        TextTitle(
//            title = stringResource(id = R.string.interest),
//            fontSize = 16,
//            modifier = Modifier
//                .padding(top = 56.dp)
//                .fillMaxSize(),
//        )
        Box(
            modifier = Modifier
                /*.background(
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(25.dp, 5.dp, 25.dp, 5.dp)
                )*/
                .align(Alignment.CenterStart)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 180.dp, start = 18.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.recommended),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Black2,
                    modifier = Modifier
                        .padding(start = 12.dp,bottom = 16.dp)
                )
                LazyRow(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = White)
                        .padding(bottom = 16.dp),
                    state = listState,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    items(10) {
                        Box(
                            modifier = Modifier
                                .background(color = White)
                        ) {
                            CardSurveyRow(
                                title = "ROW Card with blue border",
                                modifier = Modifier
                                    .padding(top = 0.dp, bottom = 0.dp, start = 8.dp, end = 8.dp)
                            )
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 360.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.available_data),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Black2,
                    modifier = Modifier
                        .padding(start = 38.dp)
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = White)
                        .padding(
                            top = 16.dp,
                            bottom = paddingValuesBottom.calculateBottomPadding()
                        ),
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(
                        count = surveys.itemCount,
                        key = { index -> surveys[index]?.surveyID ?: index }
                    ) { survey ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .background(color = White)
                        ) {
                            CardSurvey(
                                title = survey?.toString() ?: "",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .padding(top = 0.dp, bottom = 0.dp, start = 26.dp, end = 26.dp)
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
fun HomeScreenPreview() {
    HomeScreen(navController = NavController(LocalContext.current), paddingValuesBottom = PaddingValues())
}

