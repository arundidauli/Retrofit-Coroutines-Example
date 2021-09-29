package com.retrofitcoroutines.example.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Profiles : Serializable {
    @SerializedName("user_id")
    @Expose
    var user_id: String? = null

    @SerializedName("user_first_name")
    @Expose
    var user_first_name: String? = null

    @SerializedName("user_age")
    @Expose
    var age_count: String? = null

    @SerializedName("user_profile_image")
    @Expose
    var user_profile_image: String? = null

    @SerializedName("user_location")
    @Expose
    var user_location: String? = null
}