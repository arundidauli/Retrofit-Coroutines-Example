package com.retrofitcoroutines.example.remote

import com.google.gson.JsonObject
import com.retrofitcoroutines.example.model.UserDataResponse
import com.retrofitcoroutines.example.model.UserList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserApi {

    @GET("users")
    suspend fun getUsers(): Response<UserList>

    @Headers("Content-Type: application/json", "X-API-KEY: asif@321")
    @POST("frontApiList")
    suspend fun getProfiles(@Body jsonObject: JsonObject?): Response<UserDataResponse?>

}