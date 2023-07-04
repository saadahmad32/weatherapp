package com.example.weatherapp.utils

import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.text.format.DateFormat
import android.text.format.DateUtils
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import com.example.weatherapp.R
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
@BindingAdapter("convertDate")
fun CustomTextView.convertIntoRelativeDate(targetString: String) {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
    //inputFormat.timeZone = TimeZone.getTimeZone("UTC")
    val dateTimeFormatString = "h:mm, d/MM"
    val timeFormatString = "HH:mm"

    val date = inputFormat.parse(targetString)
    val timeInMilies = date?.time
    var relativeString = ""
    when {
        DateUtils.isToday(date?.time!!) -> {
            LoggerUtils.info("Extensions", "Today")
            relativeString = "${DateFormat.format(timeFormatString, timeInMilies!!)} Today"
        }
        isYesterday(date) -> {
            LoggerUtils.info("Extensions", "Yesterday")
            relativeString = "${DateFormat.format(timeFormatString, timeInMilies!!)} Yesterday"
        }
        isTomorrow(date) -> {
            LoggerUtils.info("Extensions", "Tomorrow")
            relativeString = "${DateFormat.format(timeFormatString, timeInMilies!!)} Tomorrow"
        }
        else -> {
            relativeString = DateFormat.format(dateTimeFormatString, timeInMilies!!).toString()

        }
    }
    text = relativeString
}
fun isYesterday(d: Date): Boolean {
    return DateUtils.isToday(d.time + DateUtils.DAY_IN_MILLIS)
}

fun isTomorrow(d: Date): Boolean {
    return DateUtils.isToday(d.time - DateUtils.DAY_IN_MILLIS)
}

fun Context.isConnected(): Boolean {
    var result = false
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connectivityManager.activeNetwork ?: return false
    val actNw =
        connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
    result = when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }

    return result
}