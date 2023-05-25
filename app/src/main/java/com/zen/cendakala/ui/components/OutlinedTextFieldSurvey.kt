package com.zen.cendakala.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zen.cendakala.R
import com.zen.cendakala.ui.theme.*
import com.zen.cendakala.utils.Constants

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OutlinedTextFieldSurvey (
    labelText: String,
    placeholderText: String,
    imeActionParam: ImeAction,
    keyboardTypeParam: KeyboardType,
    ){
    val keyboardController = LocalSoftwareKeyboardController.current
    var text by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        shape = RoundedCornerShape(topEnd =12.dp, bottomStart =12.dp),
        label = {
            Text(text = labelText,
                color = Black2,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            ) },
        placeholder = { Text(placeholderText, letterSpacing = Constants.LETTER_SPACING.sp) },
        keyboardOptions = KeyboardOptions(
            imeAction = imeActionParam,
            keyboardType = keyboardTypeParam
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = SoftGray,
            unfocusedBorderColor = Gray),
        singleLine = false,
        maxLines = 3,
        modifier = Modifier.fillMaxSize(),
    )
}