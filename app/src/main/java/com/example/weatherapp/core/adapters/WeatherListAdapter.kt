package com.example.weatherapp.core.adapters

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.BR
import com.example.weatherapp.core.model.MainWeather
import com.example.weatherapp.databinding.WeatherListItemBinding
import kotlin.collections.ArrayList

class WeatherListAdapter(
    private val context: Context,
    private var dataList: ArrayList<MainWeather>
) :
    RecyclerView.Adapter<WeatherListAdapter.BindViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindViewHolder {

        val rootView: ViewDataBinding =
            WeatherListItemBinding.inflate(LayoutInflater.from(context), parent, false)

        return BindViewHolder(rootView)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(viewHolder: BindViewHolder, position: Int) {
        val item = dataList[position]
        Log.i("TAG", "onBindViewHolder: item: $item")
        viewHolder.itemBinding.setVariable(BR.dataList, item)
        //viewHolder.itemBinding.setVariable(BR.onClick, context)
        viewHolder.itemBinding.executePendingBindings()
    }


    class BindViewHolder(val itemBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
    }

}