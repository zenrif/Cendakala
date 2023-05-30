package com.zen.cendakala.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.zen.cendakala.R

@Composable
fun ImageBackground(
    image: Int,
) {
    Image(
        painter = painterResource(id = image),
        contentDescription = "background",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .fillMaxSize(),
        alignment = Alignment.TopCenter
    )
}