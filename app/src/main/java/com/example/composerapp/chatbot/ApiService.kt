package com.example.composerapp.chatbot

import com.example.composerapp.chatbot.model.BotResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Url

private const val CONTENT_TYPE = "Accept:application/json"

interface ApiService {
    @Headers(CONTENT_TYPE)
    @GET
    fun getMessage(@Url url: String): Call<BotResponseModel>
}