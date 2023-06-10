package com.zen.cendakala.data.source.remote

import com.zen.cendakala.data.model.Question
import com.zen.cendakala.data.responses.CreateSurveyResponse
import com.zen.cendakala.data.responses.GeneralResponse
import com.zen.cendakala.data.responses.LoginResponse
import com.zen.cendakala.data.responses.RegisterResponse
import com.zen.cendakala.data.responses.SurveyResponse
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
    ): LoginResponse

    @FormUrlEncoded
    @POST("authentication/signup")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("job") job: String,
        @Field("gender") gender: String,
        @Field("interest") interest: Map<String, String>
    ): RegisterResponse

    @GET("stories")
    @Headers("Content-Type:application/json; charset=UTF-8")
    suspend fun surveis(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("location") location: Int
    ): Response<SurveyResponse>

    @GET("stories")
    @Headers("Content-Type:application/json; charset=UTF-8")
    suspend fun survey (
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("location") location: Int
    ): SurveyResponse

    @Multipart
    @POST("stories")
    suspend fun addStory(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: Double,
        @Part("lon") lon: Double,
    ): GeneralResponse

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

}