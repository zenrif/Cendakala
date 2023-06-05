package com.zen.cendakala.ui.auth.register

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zen.cendakala.R
import com.zen.cendakala.ui.components.CheckBoxCustom
import com.zen.cendakala.ui.components.ImageBackground
import com.zen.cendakala.ui.components.TextTitle

@Composable
fun InterestScreen() {
    // in below line we are setting
    // the state of our checkbox.
    val isChecked = remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .background(
                color = Color.White,
            )
    ) {
        ImageBackground(image = R.drawable.bg_home)
        TextTitle(
            title = stringResource(id = R.string.interest),
            fontSize = 16,
            modifier = Modifier
                .padding(top = 56.dp)
                .fillMaxSize(),
        )
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
                    .padding(top = 1.dp, bottom = 3.dp, start = 28.dp, end = 4.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                ,
                horizontalAlignment = Alignment.Start
            ) {
                val fruitsList = arrayListOf("Checkbox Example Panjang Banget Super Panjang Sekali", "Apple", "Mango", "Orange")
                fruitsList.forEach { option: String ->
                    Row {
                        CheckBoxCustom(
                            option = option,
                            isCheckedValue = isChecked.value,
                            onCheckedChange = { isChecked.value = it}
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun InterestScreenPreview() {
    InterestScreen()
}