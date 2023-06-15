package com.zen.cendakala.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zen.cendakala.R
import com.zen.cendakala.ui.auth.register.RegisterUIEvent
import com.zen.cendakala.ui.auth.register.RegisterViewModel
import com.zen.cendakala.ui.theme.Color4
import com.zen.cendakala.ui.theme.Color5
import com.zen.cendakala.ui.theme.Gray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownCustom(
    labelText: String,
    registerViewModel: RegisterViewModel
) {
    val context = LocalContext.current
    val gender = arrayOf("Male", "Female")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(gender[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
    ) {
        TextField(
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            label = { Text(text = labelText) },
            modifier = Modifier.menuAnchor(),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color4,
                unfocusedBorderColor = Color5
            ),
            leadingIcon = {
                Icon( painter = painterResource(id = R.drawable.ic_gender),
                    contentDescription = "gender",
                    tint = Gray,
                    modifier = Modifier.size(24.dp)
                )
            },
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            gender.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        selectedText = item
                        expanded = false
                        registerViewModel.onEvent(RegisterUIEvent.GenderChanged(item))
                    }
                )
            }
        }
    }
}

