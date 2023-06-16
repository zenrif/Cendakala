package com.zen.cendakala.ui.components

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.zen.cendakala.ui.theme.Black2
import com.zen.cendakala.utils.Constants

@Composable
fun TextButtonCustom(
    text: String,
    onClickButton: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TextButton(
        onClick = onClickButton,
        colors = ButtonDefaults.textButtonColors(
            contentColor = Black2,
        ),
        modifier = modifier
    ) {
        Text(
            text = text,
            letterSpacing = Constants.LETTER_SPACING.sp,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold
            )
        )
    }
}