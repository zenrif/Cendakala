package com.zen.cendakala.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zen.cendakala.ui.theme.Black2
import com.zen.cendakala.ui.theme.Color1
import com.zen.cendakala.ui.theme.White2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownCustom(
    onSelectedChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val category = arrayOf("Kesehatan", "Pendidikan", "Hukum", "Keuangan", "Pariwisata", "Sosial dan Kemanusiaan", "Lingkungan dan Konversi", "Teknologi Informasi dan Komunikasi", "Olahraga dan Rekreasi", "Seni dan Budaya", "Agama dan Kepercayaan", "Bisnis dan Industri", "Politik dan Pemerintahan", "Transportasi dan Logistik", "Pertanian dan Logistik")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(category[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
        modifier = modifier
    ) {
        TextField(
            value = selectedText,
            onValueChange = onSelectedChange,
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = White2,
                textColor = Black2,
                cursorColor = Black2,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            category.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        selectedText = item
                        expanded = false
                    },
                    modifier = Modifier
                        .background(White2)
                )
            }
        }
    }
}