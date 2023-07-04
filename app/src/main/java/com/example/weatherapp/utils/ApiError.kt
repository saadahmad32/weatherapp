package com.example.weatherapp.utils;

import com.google.gson.annotations.SerializedName
import org.json.JSONArray

data class ApiError(
    @SerializedName("cod")
    var code: String,
    @SerializedName("message")
    var message: String
)