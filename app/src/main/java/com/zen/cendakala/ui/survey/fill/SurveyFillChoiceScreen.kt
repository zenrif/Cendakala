package com.zen.cendakala.ui.survey.fill

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zen.cendakala.R
import com.zen.cendakala.data.model.Answer
import com.zen.cendakala.data.responses.Question
import com.zen.cendakala.ui.components.RadioButtonCustom
import com.zen.cendakala.ui.components.TextTitle
import com.zen.cendakala.ui.theme.Black2
import com.zen.cendakala.ui.theme.White2
import com.zen.cendakala.utils.ViewModelServerFactory

@Composable
fun SurveyFillChoiceScreen(number: String, question: Question){

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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                    .selectableGroup(),
            ) {
                question.choices?.forEach {
                    RadioButtonCustom(
                        text = it.value,
                        selected = currentAnswer.choice == it.key && currentAnswer.answer == it.value,
                        onOptionSelected = {
                            viewModel.updateAnswer(Answer(question.type, it.key, it.value))
                            viewModel.saveAnswer(number, Answer(question.type, it.key, it.value))
                                           },
                        modifier = Modifier
                            .padding(bottom = 8.dp),
                    )
                }
            }
        }
    }
//    Box(){
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 32.dp,bottom = 32.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Button(
//                onClick = {
//
//                },
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = White2,
//                    contentColor = Black2
//                ),
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_back),
//                    contentDescription = stringResource(id = R.string.back),
//                    modifier = Modifier
//                        .size(32.dp)
//                )
//            }
//            Button(
//                onClick = {
//
//                },
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = White2,
//                    contentColor = Black2
//                ),
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_next),
//                    contentDescription = stringResource(id = R.string.next),
//                    modifier = Modifier
//                        .size(48.dp)
//                )
//            }
//        }
//    }
}

//@Preview
//@Composable
//fun SurveyFillChoiceScreenPreview(){
//    SurveyFillChoiceScreen(question = Question())
//}
