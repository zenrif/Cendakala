package com.zen.cendakala.ui.wallet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zen.cendakala.R
import com.zen.cendakala.ui.components.CardSurvey
import com.zen.cendakala.ui.components.ImageBackground
import com.zen.cendakala.ui.components.PrimaryButton
import com.zen.cendakala.ui.components.TextTitle
import com.zen.cendakala.ui.theme.Black2
import com.zen.cendakala.utils.Constants

@Composable
fun WalletScreen() {
    Box(
        modifier = Modifier
            .background(
                color = Color.White,
            )
    ) {
        ImageBackground(image = R.drawable.bg_wallet)
        TextTitle(
            title = stringResource(id = R.string.wallet),
            fontSize = 20,
            modifier = Modifier
                .padding(top = 56.dp)
                .fillMaxSize(),
        )
        Row(
            modifier = Modifier
                .padding(top = 60.dp)
                .fillMaxSize(),
        ) {
            Text(
                text = "Rp1.000.000,-",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = Black2,
                letterSpacing = Constants.LETTER_SPACING.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(top = 56.dp)
                    .fillMaxSize(),
            )
        }
        Row(
            modifier = Modifier
                .padding(top = 170.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            PrimaryButton(
                text = stringResource(id = R.string.topup),
                onClickButton = { /*TODO*/ }
            )
            PrimaryButton(
                text = "Top Up",
                onClickButton = { /*TODO*/ }
            )
        }
        Box(
            modifier = Modifier
                /*.background(
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(25.dp, 5.dp, 25.dp, 5.dp)
                )*/
                .align(Alignment.TopCenter)
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 250.dp, bottom = 3.dp, start = 28.dp, end = 28.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                ,
                horizontalAlignment = Alignment.Start
            ) {
//                CardSurvey(title = "Survey 1")
            }
        }
    }
}

@Preview
@Composable
fun PreviewWalletScreen() {
    WalletScreen()
}