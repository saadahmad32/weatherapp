package com.example.weatherapp.utils;
import com.example.weatherapp.BuildConfig

class AppConstants {
    companion object {
        val SHOW_CONSOLE_LOGS = BuildConfig.DEBUG

        /**
         * Base and staging URLS
         */
        const val BASE_URL = "https://api.openweathermap.org/"

        /**
         * EndPoints
         */
        const val GET_WEATHER_BY_ZIPCODE = BASE_URL  + "data/2.5/forecast"

    }
}