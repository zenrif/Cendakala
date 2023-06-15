package com.zen.cendakala.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zen.cendakala.R
import com.zen.cendakala.ui.theme.Black2
import com.zen.cendakala.ui.theme.Color1
import com.zen.cendakala.ui.theme.White
import com.zen.cendakala.utils.Constants

@Composable
fun CardSurvey(
    modifier: Modifier = Modifier,
    title: String,
    category1: String,
    category2: String,
    quota : Int,
    reward: Long,
//    image: Int,
//    onClick: () -> Unit
) {
    Card(
        border = BorderStroke(2.dp, Color1),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = White,
            contentColor = Black2,
        ),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
//            Image(
//                painter = painterResource(id = R.drawable.bg_home),
//                contentDescription = null,
//                modifier = Modifier.fillMaxSize(),
//            )
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
            )
        }
        Row {
            Column {
                Divider(
                    color = Black2,
                    thickness = 2.dp,
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp
                        )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 12.dp,
                            bottom = 12.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Column(
                        modifier = Modifier.width(180.dp)
                    ){
                        Text(
                            text = category1,
                            modifier = Modifier.padding(
                                bottom = 12.dp
                            ),
                            style = MaterialTheme.typography.labelSmall,
                        )
                        if (category2 == "null") {
                            Text(
                                text = "",
                            )
                        } else {
                            Text(
                                text = category2,
                                style = MaterialTheme.typography.labelSmall,
                            )
                        }
                    }
                    Column(
                        modifier = Modifier.padding(end = 24.dp)
                    ) {
                        Row {
                            Image(
                                painter = painterResource(id = R.drawable.ic_quota),
                                contentDescription = "quota",
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(16.dp),
                            )
                            Text(
                                text = quota.toString(),
                                modifier = Modifier.padding(
                                    bottom = 12.dp
                                ),
                                style = MaterialTheme.typography.labelSmall,
                            )
                        }
                        Row{
                            Image(
                                painter = painterResource(id = R.drawable.ic_money),
                                contentDescription = "reward",
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(16.dp),
                            )
                            Text(
                                text = reward.toString(),
                                style = MaterialTheme.typography.labelSmall,
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
fun CardSurveyPreview() {
    CardSurvey(
        title = "Card with blue border",
        category1 = "Category 1",
        category2 = "Category 2",
        quota = 100,
        reward = 10000,
    )
}