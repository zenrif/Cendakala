package com.zen.cendakala.ui.survey.fill

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zen.cendakala.R
import com.zen.cendakala.data.model.Answer
import com.zen.cendakala.data.responses.Question
import com.zen.cendakala.ui.components.OutlinedTextFieldSurvey
import com.zen.cendakala.ui.theme.Black2
import com.zen.cendakala.ui.theme.White2
import com.zen.cendakala.utils.ViewModelServerFactory

@Composable
fun SurveyFillEssayScreen(number: String, question: Question) {
    val context = LocalContext.current
    val factory = remember { ViewModelServerFactory.getInstance(context) }
    val viewModel: SurveyFillViewModel = viewModel(factory = factory)

    val currentAnswer = viewModel.answerState
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp)
        ) {
            Text(
                text = number,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 32.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = question.question,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.Start,
            ) {
                OutlinedTextFieldSurvey(
                    labelText = "Answer",
                    placeholderText = "Fill your answer ...",
                    onTextChanged = {
                        currentAnswer.answer = it
                        currentAnswer.type = question.type
                        viewModel.saveAnswer(number, Answer(question.type, null, it))
                    },
                    imeActionParam = ImeAction.Done,
                    keyboardTypeParam = KeyboardType.Text,
                    maxLine = 10,
                    minHeightLine = 320
                )
            }
        }
    }
}

//@Preview
//@Composable
//fun SurveyFillEssayScreenPreview(){
//    SurveyFillEssayScreen(question = Question())
//}
