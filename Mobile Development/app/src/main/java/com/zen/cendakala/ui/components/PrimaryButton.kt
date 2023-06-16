package com.zen.cendakala.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zen.cendakala.ui.theme.Color1
import com.zen.cendakala.ui.theme.White2
import com.zen.cendakala.utils.CapsText
import com.zen.cendakala.utils.Constants

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClickButton: () -> Unit,
    isEnabled: Boolean = true,
) {
    Button(
        onClick = {
            onClickButton.invoke()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color1
        ),
        shape = RoundedCornerShape(20.dp),
        modifier = modifier,
        enabled = isEnabled
    ) {
        CapsText(
            text = text,
            color = White2,
            letterSpacing = Constants.LETTER_SPACING.sp
        )
    }
}