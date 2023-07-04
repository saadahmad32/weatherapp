package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.weatherapp.core.model.MainWeather
import com.example.weatherapp.databinding.ActivityWeatherDetailsBinding
import com.google.gson.Gson

class WeatherDetailsAct : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val weatherJson = intent.getStringExtra("weatherDetails")
        val mainWeather = Gson().fromJson(weatherJson, MainWeather::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather_details)
        binding.mainWeather = mainWeather
        binding.back.setOnClickListener {
            finish()
        }
    }
}