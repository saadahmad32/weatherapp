package com.example.weatherapp.core.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class WeatherDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(MainWeatherTable.CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(MainWeatherTable.DELETE_TABLE_QUERY)
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "weather_list.db"
        private const val DATABASE_VERSION = 1
    }
}
