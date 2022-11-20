package com.quiz.app.utils

import android.content.Context
import android.util.Log
import android.widget.Toast

fun log(message: String) {
    Log.d("appLogData", message)
}

fun logError(message: String) {
    Log.e("appLogData", message)
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}