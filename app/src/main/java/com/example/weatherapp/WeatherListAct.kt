package com.example.weatherapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.core.model.MainWeather
import com.example.weatherapp.core.model.WeatherList
import com.example.weatherapp.databinding.ActivityWeatherListBinding
import com.example.weatherapp.utils.LoggerUtils
import com.google.gson.Gson

class WeatherListAct : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherListBinding
    private lateinit var weatherList: ArrayList<MainWeather>
    private val TAG = "WeatherListAct"

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather_list)
        weatherList = intent.getParcelableArrayListExtra("WeatherList", MainWeather::class.java) as ArrayList<MainWeather>
        LoggerUtils.info(TAG, "weatherList: ${weatherList.size}")
        setUpRecyclerViewForCustomer()
        binding.back.setOnClickListener{
            finish()
        }
    }

    private fun setUpRecyclerViewForCustomer() {
        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.customerRev.layoutManager = layoutManager
        binding.setVariable(BR.data, weatherList)
    }

    fun gotoDetailsAct(mainWeather: MainWeather) {
        val intent = Intent(this, WeatherDetailsAct::class.java)
        intent.putExtra("weatherDetails", Gson().toJson(mainWeather))
        startActivity(intent)
    }
}