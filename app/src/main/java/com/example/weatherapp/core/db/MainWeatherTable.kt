package com.example.weatherapp.core.db

object MainWeatherTable {
    const val TABLE_NAME = "weather_list"
    const val COLUMN_ID = "_id"
    const val COLUMN_ZIPCODE = "zipcode" // Added zipcode column
    const val COLUMN_CNT = "cnt"
    const val COLUMN_COD = "cod"
    const val COLUMN_CITY = "city"
    const val COLUMN_LIST = "list"
    const val COLUMN_MESSAGE = "message"

    const val CREATE_TABLE_QUERY = "CREATE TABLE $TABLE_NAME (" +
            "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$COLUMN_ZIPCODE TEXT," +
            "$COLUMN_CNT INTEGER," +
            "$COLUMN_COD TEXT," +
            "$COLUMN_CITY TEXT," +
            "$COLUMN_LIST TEXT," +
            "$COLUMN_MESSAGE INTEGER" +
            ")"

    const val DELETE_TABLE_QUERY = "DROP TABLE IF EXISTS $TABLE_NAME"
}
