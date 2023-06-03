package com.zen.cendakala.ui.survey

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.zen.cendakala.R
import com.zen.cendakala.ui.components.OutlinedTextFieldCustom
import com.zen.cendakala.ui.components.OutlinedTextFieldSurvey
import com.zen.cendakala.ui.theme.Black2
import com.zen.cendakala.ui.theme.Color1
import com.zen.cendakala.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurveyCreateOverviewScreen() {
    val context = LocalContext.current
    val coffeeDrinks = arrayOf("Americano", "Cappuccino", "Espresso", "Latte", "Mocha")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(coffeeDrinks[0]) }
    var expanded2 by remember { mutableStateOf(false) }
    var selectedText2 by remember { mutableStateOf(coffeeDrinks[0]) }

    Box(
        modifier = Modifier
            .background(
                color = Color.White,
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_home),
            contentDescription = "background",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize(),
            alignment = Alignment.TopCenter
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 100.dp, start = 30.dp, end = 30.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.delete_icon),
                        contentDescription = "delete",
                        contentScale = ContentScale.Fit,
                        colorFilter = ColorFilter.tint(White)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp) ,
                    horizontalArrangement = Arrangement.Start,
                ) {
                    OutlinedTextFieldSurvey(
                        labelText = "Title",
                        placeholderText = "Write anything ...",
                        imeActionParam = ImeAction.Done,
                        keyboardTypeParam = KeyboardType.Text,
                        maxLine = 3
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp) ,
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Text(
                        text = "Category",
                        color = Black2,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                        modifier = Modifier
                            .padding(start = 12.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.Start ,
                ) {
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = !expanded
                        },
                        modifier = Modifier
                            .fillMaxSize(0.5f)
                            .padding(end = 12.dp)
                    ) {
                        TextField(
                            value = selectedText,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier.menuAnchor()
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            coffeeDrinks.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item) },
                                    onClick = {
                                        selectedText = item
                                        expanded = false
                                        Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                                    }
                                )
                            }
                        }
                    }
                    ExposedDropdownMenuBox(
                        expanded = expanded2,
                        onExpandedChange = {
                            expanded2 = !expanded2
                        },
                        modifier = Modifier
                            .fillMaxHeight(0.5f)
                            .background(Color1) ,
                    ) {
                        TextField(
                            value = selectedText,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded2) },
                            modifier = Modifier.menuAnchor(),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color1,
                                textColor = Color.White,
                                cursorColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expanded2,
                            onDismissRequest = { expanded2 = false },
                            modifier = Modifier
                                .background(Color1)
                        ) {
                            coffeeDrinks.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item) },
                                    onClick = {
                                        selectedText2 = item
                                        expanded2 = false
                                        Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                                    }
                                )
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp) ,
                    horizontalArrangement = Arrangement.SpaceBetween ,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(0.5f)
                            .padding(end = 6.dp)
                    ) {
                        Text(
                            text = "Quota",
                            color = Black2,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                        )
                        var value by remember { mutableStateOf("") }
                        TextField(
                            value = value,
                            onValueChange = { value = it },
                            label = { Text("Enter text") },
                            maxLines = 1,
                            textStyle = TextStyle(color = Color.Blue),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            ),
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(0.5f)
                            .padding(start = 6.dp)
                    ) {
                        Text(
                            text = "Reward",
                            color = Black2,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                        )
                        var value by remember { mutableStateOf("Hello\nWorld\nInvisible") }
                        TextField(
                            value = value,
                            onValueChange = { value = it },
                            maxLines = 1,
                            textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            ),
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp) ,
                    horizontalArrangement = Arrangement.Start ,
                ) {
                    OutlinedTextFieldSurvey(
                        labelText = "Description",
                        placeholderText = "Write anything ...",
                        imeActionParam = ImeAction.Done,
                        keyboardTypeParam = KeyboardType.Text,
                        maxLine = 10,
                        minHeightLine = 280
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SurveyCreateOverviewScreenPreview() {
    SurveyCreateOverviewScreen()
}