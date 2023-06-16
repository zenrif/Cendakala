package com.zen.cendakala.ui.wallet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zen.cendakala.R
import com.zen.cendakala.route.Routes
import com.zen.cendakala.ui.components.ImageBackground
import com.zen.cendakala.ui.components.PrimaryButton
import com.zen.cendakala.ui.components.SecondaryButton
import com.zen.cendakala.ui.components.TextTitle
import com.zen.cendakala.ui.theme.Black2
import com.zen.cendakala.ui.theme.Color4
import com.zen.cendakala.utils.Constants

@Composable
fun WalletScreen(navController: NavController, paddingValuesBottom: PaddingValues) {
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
                onClickButton = {
                    navController.navigate(Routes.Topup.routes)
                }
            )
            SecondaryButton(
                text = stringResource(id = R.string.withdrawal),
                onClickButton = { /*TODO*/ }
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(bottom = paddingValuesBottom.calculateBottomPadding())
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 280.dp, bottom = 3.dp, start = 28.dp, end = 28.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(id = R.string.transaction_history),
                    textAlign = TextAlign.Start,
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
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center),
                color = Color4,
            )
        }
    }
}

@Preview
@Composable
fun PreviewWalletScreen() {
    WalletScreen(
        navController = NavController(LocalContext.current),
        paddingValuesBottom = PaddingValues()
    )
}