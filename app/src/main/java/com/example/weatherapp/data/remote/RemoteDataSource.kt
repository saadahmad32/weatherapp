package com.example.weatherapp.data.remote

import com.example.weatherapp.data.remote.ApiService
import com.example.weatherapp.data.remote.BaseDataSource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService,
) : BaseDataSource() {

    suspend fun getWeatherByZipcode(zip: String, appId: String, units: String, cnt: String) =
        getResult {
            apiService.getWeatherByZipcode(zip, appId, units, cnt)
        }
}