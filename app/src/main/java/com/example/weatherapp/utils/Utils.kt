package com.example.weatherapp.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.regex.Pattern

fun formatDate(timestamp: Int, pattern: String = "EEE,MMM d"): String {
    val sdf = SimpleDateFormat(pattern)
    val date = Date(timestamp.toLong() * 1000)

    return sdf.format(date)
}

