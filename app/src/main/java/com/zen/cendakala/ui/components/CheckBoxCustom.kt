package com.zen.cendakala.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zen.cendakala.ui.theme.Color1
import com.zen.cendakala.ui.theme.Color2
import com.zen.cendakala.ui.theme.Color5
import com.zen.cendakala.ui.theme.Gray
import com.zen.cendakala.utils.Constants

@Composable
fun CheckBoxCustom(
    option: String,
    isCheckedValue: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Checkbox(
        checked = isCheckedValue,
        onCheckedChange = onCheckedChange,
        colors = CheckboxDefaults.colors(
            checkedColor = Color2,
            uncheckedColor = Gray,
            checkmarkColor = Color5
        ),

        )
    if (isCheckedValue) {
        Text(text = option,
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp, start = 5.dp, end = 4.dp),
            letterSpacing = Constants.LETTER_SPACING.sp,
            color = Color1
        )
    } else {
        Text(text = option,
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp, start = 5.dp, end = 4.dp),
            letterSpacing = Constants.LETTER_SPACING.sp,
            color = Color.Black)
    }
}