package com.retrofitcoroutines.example.remote

import com.retrofitcoroutines.example.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserService {

    // private val BASE_URL = "https://reqres.in/api/";
    private val BASE_URL = "https://love2knot.com/loveknot_api/api/Apiloveknot/"

    fun getUsersService(): UserApi {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE


        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }
}