package com.example.weatherapp

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import com.example.weatherapp.MainActivity.Loader.isLoading
import com.example.weatherapp.core.db.WeatherDao
import com.example.weatherapp.core.db.WeatherDatabaseHelper
import com.example.weatherapp.core.model.MainWeather
import com.example.weatherapp.core.model.WeatherList
import com.example.weatherapp.core.model.WeatherParam
import com.example.weatherapp.core.viewmodels.WeatherViewModel
import com.example.weatherapp.utils.LoggerUtils
import com.example.weatherapp.utils.Status
import com.example.weatherapp.utils.isConnected
import com.example.weatherapp.utils.toast

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    private var weatherList: WeatherList? = null
    private lateinit var loaderDialog: Dialog
    private lateinit var dbHelper: WeatherDatabaseHelper
    private lateinit var weatherDao: WeatherDao
    private var TAG = "MainActivity"
    private lateinit var zipCodeInput: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dbHelper = WeatherDatabaseHelper(this)
        weatherDao = WeatherDao(dbHelper)
        setContent {
            WeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    showTheZipCodeScreen()
                }
            }
        }
        setUpObserver()
    }

    object Loader {
        var isLoading by mutableStateOf(false)
    }

    @Composable
    fun showTheZipCodeScreen() {
        Scaffold(topBar = {
            TopAppBar(title = { Text(text = "Zip Code") })
        }, content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                var zipCode by remember { mutableStateOf("") }

                OutlinedTextField(
                    value = zipCode,
                    onValueChange = { zipCode = it },
                    label = { Text(text = "Zip Code") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        isLoading = true
                        performAction(zipCode)
                    }, modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = "Submit")
                }
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(32.dp)
                            .padding(16.dp)
                    )
                }
            }
        })
    }

    private fun performAction(zipCode: String) {
        zipCodeInput = zipCode
        // Perform some action with zip code and country code
        // For example, you can print them or pass them to another function
        println("Zip Code: $zipCode")
        if (isConnected()) {
            doCallForWeatherApi(
                WeatherParam(
                    zipCode, resources.getString(R.string.weather_api_key), "metric", "5"
                )
            )
        } else {
            val isZipcodeExists = weatherDao.isZipCodeDataExists(zipCode)
            if (isZipcodeExists) {
                val storedWeatherList = weatherDao.getWeatherListByZipCode(zipCode)
                if (storedWeatherList != null) {
                    LoggerUtils.info(TAG, "Existing Weather List: ${storedWeatherList.list.size}")
                    weatherList = storedWeatherList
                    gotoNextAct()
                } else {
                    LoggerUtils.info(TAG, "Not Existing Weather List exists")
                }
            } else {
                toast("This zipcode: $zipCode is not present in the DB! Try with internet on!")
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun PreviewZipCodeScreen() {
        WeatherAppTheme {
            showTheZipCodeScreen()
        }
    }

    private fun doCallForWeatherApi(weatherParam: WeatherParam) {
        viewModel.callForWeatherApi(weatherParam)
    }

    private fun setUpObserver() {
        viewModel.weatherListResponse.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    isLoading = false
                    weatherList = it.data
                    weatherList?.zipcode = zipCodeInput
                    //weatherDao.clearWeatherData()
                    val databasePath = getDatabasePath("weather_list.db").path
                    LoggerUtils.info(TAG, "Database_Path: $databasePath")
                    val zipCode = weatherList?.zipcode
                    LoggerUtils.info(TAG, "zipcode: $zipCode")
                    val isZipcodeExists =
                        zipCode?.let { it1 -> weatherDao.isZipCodeDataExists(it1) }
                    if (isZipcodeExists == true) {
                        LoggerUtils.info(TAG, "zipcode present, call update function : $zipCode")
                        weatherList?.let { it1 -> weatherDao.updateWeatherList(it1) }
                        LoggerUtils.info(TAG, "Data is updated")
                    } else {
                        weatherList?.let { it1 -> weatherDao.saveWeatherList(it1) }
                        val savedList: WeatherList? = weatherDao.getWeatherList()
                        LoggerUtils.info(
                            TAG,
                            "zipcode Not Present and new row added: ${savedList?.zipcode}"
                        )
//                        // The zipcode is not present in the database
//                        // Perform an alternative action
                    }
                    gotoNextAct()
//                    val list = countryName?.let { it1 -> weatherDao.isCountryDataExists(it1) }
//                    if (list != null) {
//                        for (weather in list) {
//                            LoggerUtils.info(TAG, "Existed Data: ${weather.dtTxt}")
//                        }
//                        weatherList?.let { it1 -> weatherDao.updateWeatherList(it1) }
//                        // Country data exists in the database
//                    } else {
//                        // Country data does not exist in the database
//                        weatherList?.let { it1 -> weatherDao.saveWeatherList(it1) }
//                    }
//

                }
                Status.ERROR -> {
                    isLoading = false
                    it.message?.message?.let { it1 -> toast(it1) }
                }
                Status.LOADING -> {
                    isLoading = true
                }
            }
        })
    }

    private fun gotoNextAct() {
        val intent = Intent(this, WeatherListAct::class.java)
        intent.putExtra("WeatherList", weatherList?.list)
        startActivity(intent)
    }
}
