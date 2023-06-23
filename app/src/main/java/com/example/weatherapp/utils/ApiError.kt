package com.example.weatherapp.utils;

import com.google.gson.annotations.SerializedName
import org.json.JSONArray

data class ApiError(
    @SerializedName("error")
    var error: String,
    @SerializedName("error_description")
    var error_description: String,
    @SerializedName("validationErrors")
    var validationErrors: JSONArray? = null,
    @SerializedName("validationErrorMessages")
    var validationErrorMessages: JSONArray? = null,
)