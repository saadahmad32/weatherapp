package com.example.weatherapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppApplication:Application() {
    private val TAG = AppApplication::class.simpleName
    companion object {
        lateinit var instance: Application

    }

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        instance = this

    }
    override fun onTerminate() {
        super.onTerminate()
    }
}