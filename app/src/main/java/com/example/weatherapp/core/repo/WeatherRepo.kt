package com.example.weatherapp.core.repo

import com.example.weatherapp.core.model.WeatherParam
import com.example.weatherapp.data.remote.RemoteDataSource
import com.example.weatherapp.data.remote.performGetOperation
import javax.inject.Inject

class WeatherRepo @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) {
    fun getWeatherByZipcode(weatherParam: WeatherParam) =
        performGetOperation(
            networkCall = {
                remoteDataSource.getWeatherByZipcode(
                    weatherParam.zip,
                    weatherParam.appId,
                    weatherParam.units
                )
            }
        )
}