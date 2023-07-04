package com.example.weatherapp.utils

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.example.weatherapp.R

class CustomTextView : AppCompatTextView {
    private var typefaceType = 0
    private var mFontFactory: TypeFactory? = null

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        applyCustomFont(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        applyCustomFont(context, attrs)
    }

    constructor(context: Context?) : super(context!!) {}

    private fun applyCustomFont(context: Context, attrs: AttributeSet?) {
        val array: TypedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomTextView,
            0, 0
        )
        typefaceType = try {
            array.getInteger(R.styleable.CustomTextView_font_name, 0)
        } finally {
            array.recycle()
        }
        if (!isInEditMode) {
            typeface = getTypeFace(typefaceType)
        }
    }

    private fun getTypeFace(type: Int): Typeface? {
        if (mFontFactory == null) mFontFactory = TypeFactory(context)
        return when (type) {
            Constants.O_Bold -> mFontFactory!!.objectiveBold
            Constants.O_Extra_Bold -> mFontFactory!!.objectiveExtraBold
            Constants.O_Italic -> mFontFactory!!.objectiveItalic
            Constants.O_Light -> mFontFactory!!.objectiveLight
            Constants.O_Medium -> mFontFactory!!.objectiveMedium
            Constants.O_Regular -> mFontFactory!!.objectiveRegular
            else -> mFontFactory!!.objectiveRegular
        }
    }

    interface Constants {
        companion object {
            const val O_Bold = 1
            const val O_Extra_Bold = 2
            const val O_Italic = 3
            const val O_Light = 4
            const val O_Medium = 5
            const val O_Regular = 6
        }
    }
}
