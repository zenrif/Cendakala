package com.zen.cendakala.ui.home

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.zen.cendakala.R
import com.zen.cendakala.ui.components.SearchField
import com.zen.cendakala.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    paddingValuesBottom: PaddingValues,
) {
    val searchText = remember {
        mutableStateOf("")
    }
    val listState = rememberLazyListState()

    Column {
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
            items(15) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(color = White)
                ) {
                    Card(
                        border = BorderStroke(2.dp, Color1),
                        elevation = CardDefaults.cardElevation(2.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = White,
                            contentColor = Black2,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(top = 0.dp, bottom = 0.dp, start = 26.dp, end = 26.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.bg_home),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                            )

                            Text(
                                text = "Card with blue border",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                            Row() {
                                Column {
                                    Divider(
                                        color = Black2,
                                        thickness = 2.dp,
                                        modifier = Modifier
                                            .padding(
                                                top = 0.dp,
                                                bottom = 12.dp,
                                                start = 16.dp,
                                                end = 16.dp
                                            )
                                    )
                                    Text(
                                        text = "Card with blue border",
                                        modifier = Modifier.padding(
                                            top = 12.dp,
                                            bottom = 12.dp,
                                            start = 16.dp,
                                            end = 16.dp
                                        )
                                    )
                                }
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

