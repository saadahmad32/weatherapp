package com.example.weatherapp.core.db

import android.content.ContentValues
import com.example.weatherapp.core.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WeatherDao(private val dbHelper: WeatherDatabaseHelper) {
    fun saveWeatherList(weatherList: WeatherList) {
        val db = dbHelper.writableDatabase
        val contentValues = ContentValues().apply {
            put(MainWeatherTable.COLUMN_ZIPCODE, weatherList.zipcode)
            put(MainWeatherTable.COLUMN_CNT, weatherList.cnt)
            put(MainWeatherTable.COLUMN_COD, weatherList.cod)
            put(MainWeatherTable.COLUMN_CITY, Gson().toJson(weatherList.city))
            put(MainWeatherTable.COLUMN_LIST, Gson().toJson(weatherList.list))
            put(MainWeatherTable.COLUMN_MESSAGE, weatherList.message)
        }
        db.insert(MainWeatherTable.TABLE_NAME, null, contentValues)
        db.close()
    }

    fun isZipCodeDataExists(zipcode: String): Boolean {
        val db = dbHelper.readableDatabase
        val selection = "${MainWeatherTable.COLUMN_ZIPCODE} = ?"
        val selectionArgs = arrayOf(zipcode)
        val cursor = db.query(
            MainWeatherTable.TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        val exists = cursor.moveToFirst()

        cursor.close()
        db.close()
        return exists
    }

    fun getWeatherList(): WeatherList? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            MainWeatherTable.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        val weatherList: WeatherList? = if (cursor.moveToFirst()) {
            val cityJsonIndex = cursor.getColumnIndex(MainWeatherTable.COLUMN_CITY)
            val cntIndex = cursor.getColumnIndex(MainWeatherTable.COLUMN_CNT)
            val codIndex = cursor.getColumnIndex(MainWeatherTable.COLUMN_COD)
            val listJsonIndex = cursor.getColumnIndex(MainWeatherTable.COLUMN_LIST)
            val messageIndex = cursor.getColumnIndex(MainWeatherTable.COLUMN_MESSAGE)
            val zipCodeIndex = cursor.getColumnIndex(MainWeatherTable.COLUMN_ZIPCODE)

            val cityJson = if (cityJsonIndex >= 0) cursor.getString(cityJsonIndex) else null
            val cnt = if (cntIndex >= 0) cursor.getInt(cntIndex) else 0
            val cod = if (codIndex >= 0) cursor.getString(codIndex) else null
            val listJson = if (listJsonIndex >= 0) cursor.getString(listJsonIndex) else null
            val message = if (messageIndex >= 0) cursor.getInt(messageIndex) else 0
            val zipCode = if (zipCodeIndex >= 0) cursor.getString(zipCodeIndex) else null

            val city = Gson().fromJson(cityJson, City::class.java)
            val list = Gson().fromJson<List<MainWeather>>(listJson, object : TypeToken<List<MainWeather>>() {}.type)


            WeatherList(
                city,
                cnt,
                cod!!,
                list,
                message,
                zipCode!!
            )
        } else {
            null
        }

        cursor.close()
        db.close()
        return weatherList
    }

    fun updateWeatherList(weatherList: WeatherList) {
        val db = dbHelper.writableDatabase
        val contentValues = ContentValues().apply {
            put(MainWeatherTable.COLUMN_CNT, weatherList.cnt)
            put(MainWeatherTable.COLUMN_COD, weatherList.cod)
            put(MainWeatherTable.COLUMN_LIST, Gson().toJson(weatherList.list))
            put(MainWeatherTable.COLUMN_MESSAGE, weatherList.message)
        }
        val selection = "${MainWeatherTable.COLUMN_ZIPCODE} = ?"
        val selectionArgs = arrayOf(weatherList.zipcode)
        db.update(MainWeatherTable.TABLE_NAME, contentValues, selection, selectionArgs)
        db.close()
    }
    fun clearWeatherData() {
        val db = dbHelper.writableDatabase
        db.delete(MainWeatherTable.TABLE_NAME, null, null)
        db.close()
    }
}


