package com.retrofitcoroutines.example.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserDataResponse {
    @SerializedName("data")
    @Expose
    var activeUser: List<Profiles>? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("status")
    @Expose
    var isStatus = false
}