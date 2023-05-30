package com.zen.cendakala.ui.wallet

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zen.cendakala.R
import com.zen.cendakala.ui.components.ImageBackground
import com.zen.cendakala.ui.components.PrimaryButton
import com.zen.cendakala.ui.components.TextTitle
import com.zen.cendakala.ui.theme.Black2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Topup(

){
    Box(
        modifier = Modifier
            .background(
                color = Color.White,
            )
    ) {
        ImageBackground(image = R.drawable.bg_home)
        TextTitle(
            title = stringResource(id = R.string.topup),
            fontSize = 20,
            modifier = Modifier
                .padding(top = 56.dp)
                .fillMaxSize(),
        )
        Row(
            modifier = Modifier
                .padding(top = 190.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                value = "Rp",
                onValueChange = { },)
        }
        Box(
            modifier = Modifier
                .padding(top = 260.dp)
        ) {
            val data = listOf(
                MoneyModel(
                    id = 1,
                    name = "ShopeePay",
                    image = R.drawable.ic_shopee
                ),
                MoneyModel(
                    id = 2,
                    name = "OVO",
                    image = R.drawable.ic_ovo
                ),
                MoneyModel(
                    id = 3,
                    name = "DANA",
                    image = R.drawable.ic_dana
                ),
                MoneyModel(
                    id = 4,
                    name = "GoPay",
                    image = R.drawable.ic_gopay
                ),
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(data) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent,
                            contentColor = Black2,
                        ),
                        border = CardDefaults.outlinedCardBorder(true) ,
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Image(painter = painterResource(id = item.image), contentDescription = "Logo",
                                modifier = Modifier
                                    .width(70.dp)
                                    .fillMaxSize() ,
                                alignment = Alignment.Center
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = item.name,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(14.dp)
                                )
                            }
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .padding(top = 400.dp, start = 24.dp, end = 24.dp)
                    .fillMaxSize(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center
            ){
                PrimaryButton(
                    text = "Top Up",
                    onClickButton = { /*TODO*/ },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                )
            }
        }
    }
}


data class MoneyModel(
    val id: Int,
    val name: String = "",
    val image: Int = 0,
)

@Preview
@Composable
fun TopupPreview(){
    Topup()
}