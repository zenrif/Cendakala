package com.zen.cendakala.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.zen.cendakala.R
import com.zen.cendakala.ui.components.CardSurveyHistory
import com.zen.cendakala.ui.components.ImageBackground
import com.zen.cendakala.ui.components.TabsCustom
import com.zen.cendakala.ui.components.TextTitle
import com.zen.cendakala.ui.theme.Color4
import com.zen.cendakala.ui.theme.White2
import com.zen.cendakala.utils.ViewModelServerFactory

@Composable
fun HistorySurveyScreen(navController: NavController, paddingValuesBottom: PaddingValues) {
    val columnState = rememberLazyListState()

    val context = LocalContext.current
    val factory = remember { ViewModelServerFactory.getInstance(context) }
    val historyViewModel: HistoryViewModel = viewModel(factory = factory)
    val history = historyViewModel.getHistory().collectAsLazyPagingItems()
    Box(
        modifier = Modifier
            .background(
                color = White2,
            )
    ) {
        ImageBackground(image = R.drawable.bg_home)
        TextTitle(
            title = stringResource(id = R.string.history),
            fontSize = 16,
            modifier = Modifier
                .padding(top = 56.dp)
                .fillMaxSize(),
        )
        Box(
            modifier = Modifier
                .padding(top = 200.dp)
        ) {
            TabsCustom()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp, bottom = paddingValuesBottom.calculateBottomPadding())
            ) {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = White2)
                        .padding(
                            top = 16.dp),
                    state = columnState,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (history.loadState.refresh is LoadState.Loading) {
                        item(key = "prepend_loading") {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(160.dp)
                                    .padding(top = 24.dp, bottom = 24.dp)
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .align(Alignment.Center),
                                    color = Color4,
                                )
                            }
                        }
                    }
                    items(
                        count = history.itemCount,
                        key = { index -> history[index]?.surveyID ?: index }
                    ) { index ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .background(color = White2)
                        ) {
                            CardSurveyHistory(
                                title = history[index]?.title ?: "",
                                reward = history[index]?.reward ?: 0,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .padding(top = 0.dp, bottom = 0.dp, start = 26.dp, end = 26.dp),
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
fun PreviewHistorySurveyScreen() {
    HistorySurveyScreen(
        navController = NavController(LocalContext.current),
        paddingValuesBottom = PaddingValues()
    )
}