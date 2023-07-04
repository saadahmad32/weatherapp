package com.example.weatherapp.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.core.adapters.WeatherListAdapter
import com.example.weatherapp.core.model.MainWeather
import kotlin.collections.ArrayList

@BindingAdapter("weatherList")
fun bindingRecyclerViewCustomerList(
    view: RecyclerView,
    dataList: ArrayList<MainWeather>?
) {
    if (dataList?.isEmpty() == true)
        return
    val layoutManager = view.layoutManager
    if (layoutManager == null)
        view.layoutManager = LinearLayoutManager(view.context)
    var adapter = view.adapter
    if (adapter == null) {
        adapter = dataList?.let {
           WeatherListAdapter(view.context, it)
        }
        view.adapter = adapter
    }
}
