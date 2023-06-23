package com.example.weatherapp.core.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.weatherapp.core.model.WeatherList
import com.example.weatherapp.core.model.WeatherParam
import com.example.weatherapp.core.repo.WeatherRepo
import com.example.weatherapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    weatherRepo: WeatherRepo
) : ViewModel() {
    private val _weather = MutableLiveData<WeatherParam>()

    private val weatherList = _weather.switchMap { validatePassport ->
        weatherRepo.getWeatherByZipcode(validatePassport)
    }
    val weatherListResponse: LiveData<Resource<WeatherList>> =
        weatherList

    fun callForWeatherApi(
        weatherParam: WeatherParam
    ) {
        _weather.value = weatherParam
    }
}