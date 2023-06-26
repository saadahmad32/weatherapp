package com.example.weatherapp.core.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class WeatherList(
    @SerializedName("city")
    val city: City,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("cod")
    val cod: String,
    @SerializedName("list")
    val list: List<MainWeather>,
    @SerializedName("message")
    val message: Int,
    var zipcode: String
) : Parcelable