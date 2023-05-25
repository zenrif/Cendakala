package com.zen.cendakala.ui.survey

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
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
import com.zen.cendakala.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurveyCreateOverviewScreen() {
    // Declaring a boolean value to store
    // the expanded state of the Text Field
    var mExpanded by remember { mutableStateOf(false) }
    var mExpanded2 by remember { mutableStateOf(false) }

    // Create a list of cities
    val mCities = listOf("Delhi", "Mumbai", "Chennai", "Kolkata", "Hyderabad", "Bengaluru", "Pune")

    val mCities2 = listOf("Delhi", "Mumbai", "Chennai", "Kolkata", "Hyderabad")

    // Create a string value to store the selected city
    var mSelectedText by remember { mutableStateOf("") }

    var mTextFieldSize by remember { mutableStateOf(Size.Zero)}

    var mSelectedText2 by remember { mutableStateOf("") }

    var mTextFieldSize2 by remember { mutableStateOf(Size.Zero )}

    // Up Icon when expanded and down icon when collapsed
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Box(
        modifier = Modifier
            .background(
                color = Color.White,
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_survey),
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
                        fontFamily = androidx.compose.ui.text.font.FontFamily.SansSerif,
                        modifier = Modifier
                            .padding(start = 12.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.Start ,
                ) {
                    // Create an Outlined Text Field
                    // with icon and not expanded
                    OutlinedTextField(
                        value = mSelectedText,
                        onValueChange = { mSelectedText = it },
                        modifier = Modifier
                            .fillMaxSize(0.5f)
                            .onGloballyPositioned { coordinates ->
                                // This value is used to assign to
                                // the DropDown the same width
                                mTextFieldSize = coordinates.size.toSize()
                            }
                            .padding(end = 12.dp) ,
                        label = {Text("Label")},
                        trailingIcon = {
                            Icon(icon,"contentDescription",
                                Modifier.clickable { mExpanded = !mExpanded })
                        }
                    )
                    DropdownMenu(
                        expanded = mExpanded,
                        onDismissRequest = { mExpanded = false },
                        modifier = Modifier
                            .width(with(LocalDensity.current){mTextFieldSize.width.toDp()})
                    ) {
                        mCities.forEach { label ->
                            DropdownMenuItem(
                                text = { Text(text = label) },
                                onClick = {
                                    mSelectedText = label
                                    mExpanded = false
                                }
                            )
                        }
                    }
                    OutlinedTextField(
                        value = mSelectedText2,
                        onValueChange = { mSelectedText2 = it },
                        modifier = Modifier
                            .fillMaxHeight(0.5f)
                            .onGloballyPositioned { coordinates ->
                                // This value is used to assign to
                                // the DropDown the same width
                                mTextFieldSize2 = coordinates.size.toSize()
                            },
                        label = {Text("Label")},
                        trailingIcon = {
                            Icon(icon,"contentDescription",
                                Modifier.clickable { mExpanded2 = !mExpanded2 })
                        }
                    )
                    // Create a drop-down menu with list of cities,
                    // when clicked, set the Text Field text as the city selected
//                    var density  = Density(
//                        density = 2f,
//                        fontScale = 1f,
//                    )
                    DropdownMenu(
                        expanded = mExpanded2,
                        onDismissRequest = { mExpanded2 = false },
                        modifier = Modifier
                            .width(with(LocalDensity.current){mTextFieldSize2.width.toDp()})
                            .padding(start = 52.dp)
                    ) {
                        mCities2.forEach { label ->
                            DropdownMenuItem(
                                text = { Text(text = label) },
                                onClick = {
                                    mSelectedText2 = label
                                    mExpanded2 = false
                                }
                            )
                        }
                    }
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