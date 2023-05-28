package com.zen.cendakala.ui.survey

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
fun SurveyCreateQuestionScreen() {
    val context = LocalContext.current

    val numberQuestion = arrayOf("Question 1", "Question 2", "Question 3", "Question 4", "Question 5")
    val typeQuestion = arrayOf("Multiple Choice", "Short Answer", "Long Answer", "Dropdown")
    val amountQuestion = arrayOf("1", "2", "3", "4", "5")
    var expanded by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }
    var expanded3 by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(numberQuestion[0]) }
    var selectedText2 by remember { mutableStateOf(typeQuestion[0]) }
    var selectedText3 by remember { mutableStateOf(amountQuestion[0]) }

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
                    .padding(top = 100.dp, start = 24.dp, end = 24.dp)
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
                            numberQuestion.forEach { item ->
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
                }
                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween ,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(0.5f)
                            .padding(end = 6.dp)
                    ) {
                        Text(
                            text = "Type",
                            color = Black2,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                        )
                    ExposedDropdownMenuBox(
                        expanded = expanded2,
                        onExpandedChange = {
                            expanded2 = !expanded2
                        },
                    ) {
                        TextField(
                            value = selectedText2,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded2) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxSize()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded2,
                            onDismissRequest = { expanded2 = false }
                        ) {
                            typeQuestion.forEach { item ->
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
                    Column(
                        modifier = Modifier
                            .fillMaxSize(0.4f)
                    ) {
                        Text(
                            text = "Number",
                            color = Black2,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                        )
                    ExposedDropdownMenuBox(
                        expanded = expanded3,
                        onExpandedChange = {
                            expanded3 = !expanded3
                        },
                        modifier = Modifier
                            .background(Color1) ,
                    ) {
                        TextField(
                            value = "",
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded3) },
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
                            expanded = expanded3,
                            onDismissRequest = { expanded3 = false },
                            modifier = Modifier
                                .background(Color1)
                        ) {
                            amountQuestion.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item) },
                                    onClick = {
                                        selectedText3 = item
                                        expanded3 = false
                                        Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                                    }
                                )
                            }
                        }
                    }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(0.2f)
                            .padding(start = 8.dp)
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "Apply",
                            color = Black2,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
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
                        labelText = "Question",
                        placeholderText = "Write anything ...",
                        imeActionParam = ImeAction.Done,
                        keyboardTypeParam = KeyboardType.Text,
                        maxLine = 5,
                        minHeightLine = 112
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp) ,
                    horizontalArrangement = Arrangement.Start ,
                ) {
                    OutlinedTextFieldSurvey(
                        labelText = "Choice 1",
                        placeholderText = "Write anything ...",
                        imeActionParam = ImeAction.Done,
                        keyboardTypeParam = KeyboardType.Text,
                        maxLine = 2,
                        minHeightLine = 56
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp) ,
                    horizontalArrangement = Arrangement.Start ,
                ) {
                    OutlinedTextFieldSurvey(
                        labelText = "Choice 2",
                        placeholderText = "Write anything ...",
                        imeActionParam = ImeAction.Done,
                        keyboardTypeParam = KeyboardType.Text,
                        maxLine = 2,
                        minHeightLine = 56
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SurveyCreateQuestionScreenPreview() {
    SurveyCreateQuestionScreen()
}