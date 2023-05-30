package com.zen.cendakala.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zen.cendakala.ui.theme.Color1
import com.zen.cendakala.utils.CapsText

@Composable
fun PrimaryButton(
    text: String,
    onClickButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {
        onClickButton
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color1
        ),
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
        ) {
        CapsText(
            text = text
        )
    }
}