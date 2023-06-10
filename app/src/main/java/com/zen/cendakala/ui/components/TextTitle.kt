package com.zen.cendakala.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.zen.cendakala.ui.theme.Black2
import com.zen.cendakala.utils.CapsText
import com.zen.cendakala.utils.Constants

@Composable
fun TextTitle(
    title: String,
    modifier: Modifier = Modifier,
    fontSize : Int,
    textAlign: TextAlign? = TextAlign.Center,
) {
    CapsText(
        text = title,
        modifier = modifier,
        textAlign = textAlign,
        fontSize = fontSize.sp,
        color = Black2,
        letterSpacing = Constants.LETTER_SPACING_TITLE.sp,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleLarge,
        maxLines = 2
    )
}