package com.example.weatherapp.core.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Clouds(
    @SerializedName("all")
    val all: Int
) : Parcelable