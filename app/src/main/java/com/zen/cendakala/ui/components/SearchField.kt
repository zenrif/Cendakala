package com.zen.cendakala.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zen.cendakala.R
import com.zen.cendakala.ui.theme.Black2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(
    placeholder: String,
    value: String,
    enable: Boolean = true,
    onClick: () -> Unit = {},
    onValueChange: (String) -> Unit = {},
    onClear: () -> Unit = {},
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable {
                onClick()
            },
        value = value,
        enabled = enable,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Transparent,
            unfocusedBorderColor = Black2,
            focusedBorderColor = Black2,
            cursorColor = Black2,
        ),
        shape = RoundedCornerShape(20.dp),
        maxLines = 1,
        singleLine = true,
        textStyle = TextStyle(
            fontWeight = FontWeight.Medium,
        ),
        leadingIcon = {
            Icon(
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp),
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search Icon"
            )
        },
        trailingIcon = {
            if (value.isNotEmpty()) {
                Icon(
                    modifier = Modifier.clickable {
                        onClear()
                    },
                    painter = painterResource(id = R.drawable.ic_cancel),
                    contentDescription = "cancel",
                    tint = Gray
                )
            }
        },
        onValueChange = onValueChange,
        placeholder = {
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium)
                )
            }
        },
    )
}

@Preview
@Composable
fun SearchFieldPreview() {
    SearchField(
        placeholder = "Search",
        value = "Naruto",
        enable = true,
        onClick = {},
        onValueChange = {},
        onClear = {}
    )
}