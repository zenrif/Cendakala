package com.zen.cendakala.ui.survey

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zen.cendakala.R
import com.zen.cendakala.ui.theme.Black2
import com.zen.cendakala.ui.theme.Color1
import com.zen.cendakala.ui.theme.Color2
import com.zen.cendakala.ui.theme.Color5
import com.zen.cendakala.ui.theme.Gray
import com.zen.cendakala.ui.theme.White
import com.zen.cendakala.utils.CapsText
import com.zen.cendakala.utils.Constants

@Composable
fun SurveyDetailScreen() {
    val isChecked = remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .background(
                color = Color.White,
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_survey),
            contentDescription = "background",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize(),
            alignment = Alignment.TopCenter
        )
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
                        painter = painterResource(id = R.drawable.edit_icon),
                        contentDescription = "edit",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .padding(end = 15.dp, bottom = 2.dp),
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(White)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.delete_icon),
                        contentDescription = "delete",
                        contentScale = ContentScale.Fit,
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(White)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.Start ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "title",
                        color = Color.White,
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.Start ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Hubungan antara Banyaknya Radio di Pedesaan\n" +
                                "dengan Jumlah Sepatu yang Terjual",
                        color = Color.White,
                    )
                }
Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp, bottom = 20.dp),
                    horizontalArrangement = Arrangement.Start ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "SurveyID : jihefikjasznfji212n1knJaSD1@",
                        color = Color.White,
                        fontSize = 10.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.End ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Download responses",
                        color = Color.White,
                        fontSize = 10.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.Start ,
                    verticalAlignment = Alignment.CenterVertically
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
                            .padding(top = 22.dp, bottom = 0.dp, start = 0.dp, end = 0.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 0.dp, bottom = 0.dp, start = 0.dp, end = 28.dp),
                            horizontalArrangement = Arrangement.SpaceAround ,
                        ) {
                            Text(
                                text = "Category",
                                modifier = Modifier
                                    .padding(top = 8.dp, bottom = 16.dp, start = 0.dp, end = 0.dp) ,
                            )
                            Column(
                                modifier = Modifier
                                    .padding(top = 8.dp, bottom = 16.dp, start = 0.dp, end = 0.dp) ,
                            ) {
                            Text(text = "Category 1")
                                Spacer(modifier = Modifier.padding(5.dp))
                            Text(text = "Category 2")
                            }
                        }
                    }
                }

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
                        .padding(top = 10.dp, bottom = 0.dp, start = 0.dp, end = 0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 0.dp, bottom = 0.dp, start = 0.dp, end = 28.dp),
                        horizontalArrangement = Arrangement.SpaceAround ,
                    ) {
                        Text(
                            text = "Quota",
                            modifier = Modifier
                                .padding(top = 8.dp, bottom = 16.dp, start = 0.dp, end = 0.dp) ,
                        )
                        Column(
                            modifier = Modifier
                                .padding(top = 8.dp, bottom = 16.dp, start = 0.dp, end = 0.dp) ,
                        ) {
                            Row {
                                Image(painter = painterResource(id = R.drawable.quota_icon),
                                    contentDescription = "quota",
                                    contentScale = ContentScale.Inside ,)
                                Text(text = "Category 1" , modifier = Modifier.padding(start = 5.dp))
                            }
                            Spacer(modifier = Modifier.padding(5.dp))
                            Row {
                                Image(painter = painterResource(id = R.drawable.money_icon),
                                    contentDescription = "money",
                                    contentScale = ContentScale.Inside ,)
                                Text(text = "Category 1" , modifier = Modifier.padding(start = 5.dp))
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
fun SurveyDetailScreenPreview() {
    SurveyDetailScreen()
}