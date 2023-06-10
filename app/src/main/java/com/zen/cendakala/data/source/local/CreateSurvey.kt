package com.zen.cendakala.data.source.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zen.cendakala.data.model.Question
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "create_survey")
data class CreateSurvey(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "question_num")
    val questionNum: Int,

    @ColumnInfo(name = "quota")
    val quota: Int,

    @ColumnInfo(name = "reward")
    val reward: Long,

    @ColumnInfo(name = "category1")
    val category1: String,

    @ColumnInfo(name = "category2")
    val category2: String? = null,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "questions")
    val questions: Map<String, QuestionPar>? = null
) : Parcelable

@Parcelize
data class QuestionPar(
    val type: String,
    val choiceNum: Int? = null,
    val question: String,
    val choices: Map<String, String>? = null
) : Parcelable