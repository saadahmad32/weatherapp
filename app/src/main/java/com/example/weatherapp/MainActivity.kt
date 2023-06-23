package com.example.weatherapp

import android.app.Dialog
import android.os.Bundle
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
import com.example.weatherapp.core.model.WeatherList
import com.example.weatherapp.core.model.WeatherParam
import com.example.weatherapp.core.viewmodels.WeatherViewModel
import com.example.weatherapp.utils.LoggerUtils
import com.example.weatherapp.utils.Status
import com.example.weatherapp.utils.toast

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    private var weatherList: WeatherList? = null
    private lateinit var loaderDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
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
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Zip Code") }
                )
            },
            content = { innerPadding ->
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
                        },
                        modifier = Modifier.align(Alignment.End)
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
            }
        )
    }

    private fun performAction(zipCode: String) {
        // Perform some action with zip code and country code
        // For example, you can print them or pass them to another function
        println("Zip Code: $zipCode")
        doCallForWeatherApi(
            WeatherParam(
                zipCode,
                resources.getString(R.string.weather_api_key),
                "metric"
            )
        )
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
                    LoggerUtils.info(
                        "PassengerCheckAct",
                        weatherList.toString()
                    )
                }
                Status.ERROR -> {
                    isLoading = false
                    it.message?.error?.let { it1 -> toast(it1) }
                }
                Status.LOADING -> {
                    isLoading = true
                }
            }
        })
    }
}
