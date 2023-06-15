package com.zen.cendakala.data.source.remote

import com.zen.cendakala.data.model.Answer
import com.zen.cendakala.data.model.AnswerModel
import com.zen.cendakala.data.model.Question
import com.zen.cendakala.data.model.RegisterModel
import com.zen.cendakala.data.responses.CreateSurveyResponse
import com.zen.cendakala.data.responses.GeneralResponse
import com.zen.cendakala.data.responses.LoginResponse
import com.zen.cendakala.data.responses.RegisterResponse
import com.zen.cendakala.data.responses.SurveyByIdResponse
import com.zen.cendakala.data.responses.SurveyResponse
import com.zen.cendakala.data.responses.TokenResponse
import com.zen.cendakala.data.responses.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {
    @FormUrlEncoded
    @POST("authentication/signin")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    @POST("authentication/signup")
    suspend fun register(
        @Body registerModel: RegisterModel
    ): Response<RegisterResponse>

    @FormUrlEncoded
    @POST("surveys/create")
    suspend fun createSurvey(
        @Header("authtoken") authtoken: String,
        @Field("title") title: String,
        @Field("questionNum") questionNum: Int,
        @Field("quota") quota: Int,
        @Field("reward") reward: Long,
        @Field("category1") category1: String,
        @Field("category2") category2: String,
        @Field("description") description: String,
        @Field("questions") questions: Map<String, Question>
    ): CreateSurveyResponse

    @GET("surveys/purchaseAble")
    suspend fun purchaseable(
        @Header("authtoken") authtoken: String,
    ): Response<SurveyResponse>

    @POST("surveys/recommedation/home")
    suspend fun recommedation(
        @Header("authtoken") authtoken: String,
    ): Response<SurveyResponse>

    @GET("surveys/read/all")
    suspend fun allSurvey(
        @Header("authtoken") authtoken: String,
    ): Response<SurveyResponse>

    @GET("surveys/read/{surveyID}")
    suspend fun surveyById(
        @Header("authtoken") authtoken: String,
        @Path("surveyID") surveyID: String
    ): SurveyByIdResponse

    @GET("users/read")
    suspend fun cekToken(
        @Header("authtoken") authtoken: String,
    ): Response<TokenResponse>

    @GET("users/read")
    suspend fun getUserProfile(
        @Header("authtoken") authtoken: String,
    ): Response<UserResponse>

    @GET("surveys/read/all")
    suspend fun history(
        @Header("authtoken") authtoken: String,
    ): Response<SurveyResponse>

    @POST("response/create")
    suspend fun submitAnswer(
        @Header("authtoken") authtoken: String,
        @Body answer: AnswerModel
    ): GeneralResponse
}