package com.example.weatherapp.utils

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import com.example.weatherapp.R

class TypeFactory(context: Context) {
    var objectiveBold: Typeface? = ResourcesCompat.getFont(context, R.font.objective_bold)
    var objectiveExtraBold: Typeface? = ResourcesCompat.getFont(context, R.font.objective_extra_bold)
    var objectiveItalic: Typeface? = ResourcesCompat.getFont(context, R.font.objective_italic)
    var objectiveLight: Typeface? = ResourcesCompat.getFont(context, R.font.objective_light)
    var objectiveMedium: Typeface? = ResourcesCompat.getFont(context, R.font.objective_medium)
    var objectiveRegular: Typeface? = ResourcesCompat.getFont(context, R.font.objective_regular)
}