package com.zen.cendakala.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zen.cendakala.R
import com.zen.cendakala.ui.theme.Black2
import com.zen.cendakala.ui.theme.Color1
import com.zen.cendakala.ui.theme.White

@Composable
fun CardSurvey(
    modifier: Modifier = Modifier,
    title: String,
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
                .padding(10.dp),
        ) {
//            Image(
//                painter = painterResource(id = R.drawable.bg_home),
//                contentDescription = null,
//                modifier = Modifier.fillMaxSize(),
//            )
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