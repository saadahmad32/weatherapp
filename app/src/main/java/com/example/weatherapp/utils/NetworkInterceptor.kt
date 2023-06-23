package com.example.weatherapp.utils;
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.weatherapp.R
import okhttp3.Interceptor
import okhttp3.Response
import kotlinx.coroutines.*

class NetworkInterceptor(context: Context) : Interceptor {

    private val mContext: Context = context
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected()) {
            LoggerUtils.info("Network", "no internet")
            coroutineScope.launch {
                mContext.toast(mContext.resources.getString(R.string.no_internet))
            }
            throw NoConnectivityException()
            // Throwing our custom exception 'NoConnectivityException'
        }
        val requestBuilder = chain.request().newBuilder()
        return chain.proceed(requestBuilder.build())
    }
    private fun isConnected(): Boolean {
        var result = false
        val connectivityManager =
            mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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
}