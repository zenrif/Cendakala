package com.zen.cendakala.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.zen.cendakala.R
import kotlin.math.max

@Composable
fun LottieExample() {

    // to keep track if the animation is playing
    // and play pause accordingly
    var isPlaying by remember {
        mutableStateOf(true)
    }
    // for speed
    var speed by remember {
        mutableStateOf(1f)
    }

    // remember lottie composition ,which
    // accepts the lottie composition result
    val composition by rememberLottieComposition(
        LottieCompositionSpec
            .RawRes(R.raw.header)
    )


    // to control the animation
    val progress by animateLottieCompositionAsState(
        // pass the composition created above
        composition,

        // Iterates Forever
        iterations = LottieConstants.IterateForever,

        // pass isPlaying we created above,
        // changing isPlaying will recompose
        // Lottie and pause/play
        isPlaying = isPlaying,

        // pass speed we created above,
        // changing speed will increase Lottie
        speed = speed,

        // this makes animation to restart when paused and play
        // pass false to continue the animation at which it was paused
        restartOnPlay = false

    )

    // Column Composable
    Column(
        Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Heading
        Text(
            text = "Lottie",
            color = Color.Gray,
            fontSize = 70.sp,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(10.dp)
        )

        // LottieAnimation
        // Pass the composition and the progress state
        LottieAnimation(
            composition,
            progress,
            modifier = Modifier.size(400.dp)
        )

        // Buttons to control the animation
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Button to decrease speed
                Button(
                    onClick = {
                        // check to prevent speed going negative
                        speed = max(speed - 0.25f, 0f)
                    },
                    // Button background color
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0F9D58)
                    )
                ) {
                    Text(
                        text = "-",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,

                        )
                }

                // Button to Increase speed
                Text(
                    text = "Speed ( $speed ) ",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp, modifier = Modifier.padding(horizontal = 10.dp)

                )
                Button(
                    onClick = {
                        // Increase the speed by 0.25
                        speed += 0.25f
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0F9D58)
                    )
                ) {
                    Text(
                        text = "+",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }

            // Button to pause and play
            Button(
                onClick = {
                    // change isPlaying state to pause/play
                    isPlaying = !isPlaying
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0F9D58)
                )
            ) {
                Text(
                    // display text according to state
                    text = if (isPlaying) "Pause" else "Play",
                    color = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewLottieExample() {
    LottieExample()
}