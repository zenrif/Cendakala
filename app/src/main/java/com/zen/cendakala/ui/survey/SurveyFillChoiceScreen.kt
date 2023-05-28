package com.zen.cendakala.ui.survey

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zen.cendakala.ui.theme.Color1
import com.zen.cendakala.ui.theme.Color4
import com.zen.cendakala.ui.theme.White
import com.zen.cendakala.ui.theme.White2

@Composable
fun SurveyFillChoiceScreen(){
    val possibleAnswers = listOf(
        Superhero("Superman"),
        Superhero("Batman"),
        Superhero("Flash"),
        Superhero("Wonder Woman"),
        Superhero("Coba Testing Text Panjang Banget Sekali Lorem Ipsum Dolor Sit Amet Consectetur Adipiscing Elit Sed Do Eiusmod Tempor Incididunt Ut Labore Et Dolore Magna Aliqua Ut Enim Ad Minim Veniam Quis Nostrud Exercitation Ullamco Laboris Nisi Ut Aliquip Ex Ea Commodo Consequat Duis Aute Irure Dolor In Reprehenderit In Voluptate Velit Esse Cillum Dolore Eu Fugiat Nulla Pariatur Excepteur Sint Occaecat Cupidatat Non Proident Sunt In Culpa Qui Officia Deserunt Mollit Anim Id Est Laborum")
    )
    var selectedAnswer by remember { mutableStateOf<Superhero?>(null) }
    var onOptionSelected: (Superhero) -> Unit = { selectedAnswer = it }
    Box(
        modifier = Modifier
            .background(color = White)
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 100.dp, start = 24.dp, end = 24.dp)
            ) {
//                Text(
//                    text = "Pilih Jawaban",
//                    fontSize = 20.sp,
//                    modifier = Modifier
//                        .padding(16.dp)
//                        .align(Alignment.Start)
//                )
                Text(
                    text = "Question 1",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Start)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp, bottom = 24.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "How would you rate the waste recycling program in your area?",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center

                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                        .selectableGroup(),
                ) {
                    possibleAnswers.forEach {
                        val selected = it == selectedAnswer
                        RadioButtonWithImageRow(
                            text = it.hero,
                            selected = selected,
                            onOptionSelected = { onOptionSelected(it) },
                            modifier = Modifier
                                .padding(bottom = 8.dp),
                        )
                    }
                }
            }
        }
    }
}

data class Superhero(
    val hero: String,
)

@Composable
fun RadioButtonWithImageRow(
    text: String,
    selected: Boolean,
    onOptionSelected: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = MaterialTheme.shapes.small,
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
            .clip(MaterialTheme.shapes.small)
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
//            Image(
//                painter = painterResource(id = imageResourceId),
//                contentDescription = null,
//                modifier = Modifier
//                    .size(56.dp)
//                    .clip(MaterialTheme.shapes.extraSmall)
//                    .padding(start = 0.dp, end = 8.dp)
//            )
//            Spacer(Modifier.width(8.dp))

            Text(text, Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
            Box(Modifier.padding(8.dp)) {
                RadioButton(selected, onClick = null)
            }
        }
    }
}

@Preview
@Composable
fun SurveyFillChoiceScreenPreview(){
    SurveyFillChoiceScreen()
}
