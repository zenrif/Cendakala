package com.zen.cendakala.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zen.cendakala.R
import com.zen.cendakala.ui.components.CheckBoxCustom
import com.zen.cendakala.ui.components.ImageBackground
import com.zen.cendakala.ui.components.TabsCustom
import com.zen.cendakala.ui.components.TextTitle

@Composable
fun HistorySurveyScreen() {
    Box(
        modifier = Modifier
            .background(
                color = Color.White,
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
            Column(
                modifier = Modifier
                    .padding(top = 1.dp, bottom = 3.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                ,
                horizontalAlignment = Alignment.Start
            ) {
                TabsCustom()
            }
        }
    }
}

@Preview
@Composable
fun PreviewHistorySurveyScreen() {
    HistorySurveyScreen()
}