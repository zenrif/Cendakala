package com.zen.cendakala.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zen.cendakala.ui.theme.Black2
import com.zen.cendakala.ui.theme.Color1
import com.zen.cendakala.ui.theme.Color4
import com.zen.cendakala.utils.CapsText
import com.zen.cendakala.utils.Constants

@Composable
fun SecondaryButton(
    text: String,
    onClickButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {
        onClickButton
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color4
        ),
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
        ) {
        CapsText(
            text = text,
            color = Black2,
            letterSpacing = Constants.LETTER_SPACING.sp
        )
    }
}