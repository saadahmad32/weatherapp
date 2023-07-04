package com.example.weatherapp.data.remote

import com.example.weatherapp.core.model.WeatherList
import com.example.weatherapp.utils.AppConstants
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET(AppConstants.GET_WEATHER_BY_ZIPCODE)
    suspend fun getWeatherByZipcode(
        @Query("zip") zip: String,
        @Query("appid") appid: String,
        @Query("units") units: String,
        @Query("cnt") cnt: String
    ): Response<WeatherList>

}