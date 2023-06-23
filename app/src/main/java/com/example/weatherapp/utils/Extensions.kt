package com.example.weatherapp.utils

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.Toast
import com.example.weatherapp.R
import com.google.android.material.snackbar.Snackbar

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
