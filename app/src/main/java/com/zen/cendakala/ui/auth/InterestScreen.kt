package com.zen.cendakala.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.zen.cendakala.ui.theme.Color1
import com.zen.cendakala.ui.theme.Color2
import com.zen.cendakala.ui.theme.Color5
import com.zen.cendakala.ui.theme.Gray
import com.zen.cendakala.ui.theme.White
import com.zen.cendakala.utils.CapsText
import com.zen.cendakala.utils.Constants

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
        Image(
            painter = painterResource(id = R.drawable.bg_home),
            contentDescription = "background",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize(),
            alignment = Alignment.TopCenter
        )
        CapsText(
            text = stringResource(id = R.string.signin),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 56.dp)
                .fillMaxSize(),
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            fontSize = 24.sp,
            letterSpacing = Constants.LETTER_SPACING_TITLE.sp
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
                        Checkbox(
                            checked = isChecked.value,
                            onCheckedChange = { isChecked.value = it },
                            colors = CheckboxDefaults.colors(       checkedColor = Color2,
                                uncheckedColor = Gray,
                                checkmarkColor = Color5),

                        )
                        if (isChecked.value) {
                            Text(text = option,
                                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp, start = 5.dp, end = 4.dp),
                                letterSpacing = Constants.LETTER_SPACING.sp,
                                color = Color1)
                        } else {
                            Text(text = option,
                                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp, start = 5.dp, end = 4.dp),
                                letterSpacing = Constants.LETTER_SPACING.sp,
                                color = Color.Black)
                        }
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