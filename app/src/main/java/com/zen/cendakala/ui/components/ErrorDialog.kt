package com.zen.cendakala.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ErrorDialog(
    message: String = "error",
    image: Int,
    action: (() -> Unit)? = null
) {
    val showDialog = remember { mutableStateOf(true) }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = {
                Image(
                    painter = painterResource(id = image),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp),
                    alignment = Alignment.Center,
                )
            },
            text = {
                Text(
                    message,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        action?.invoke()
                        showDialog.value = false
                    }
                ) {
                    Text("Dismiss")
                }
            },
//            dismissButton = {
//                TextButton(
//                    onClick = {
//                        action?.invoke()
//
//                    }
//                ) {
//                    Text("Dismiss")
//                }
//            },
            )
    }
}



