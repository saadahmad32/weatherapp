package com.example.weatherapp.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherParam(
    var zip: String,
    var appId: String,
    var units: String
) : Parcelable
