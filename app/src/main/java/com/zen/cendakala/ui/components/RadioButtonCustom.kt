package com.zen.cendakala.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.zen.cendakala.ui.theme.Color1
import com.zen.cendakala.ui.theme.Color4
import com.zen.cendakala.ui.theme.White

@Composable
fun RadioButtonCustom(
    text: String,
    selected: Boolean,
    onOptionSelected: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = if (selected) {
            Color4
        } else {
            White
        },
        border = BorderStroke(
            width = 2.dp,
            color = if (selected) {
                Color4
            } else {
                Color4
            }
        ),
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .selectable(
                selected,
                onClick = onOptionSelected,
                role = Role.RadioButton
            )
            .background(color = if (selected) {
                Color1
            } else {
                White
            })
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text, Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
            Box(Modifier.padding(8.dp)) {
                RadioButton(selected, onClick = null)
            }
        }
    }
}
