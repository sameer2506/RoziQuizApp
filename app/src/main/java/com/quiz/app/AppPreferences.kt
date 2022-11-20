package com.quiz.app

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(ctx: Context) {

    private var user: SharedPreferences =
        ctx.getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE)

    fun saveId(userId: String) {
        user.edit().putString("userId", userId).apply()
    }

    fun getId(): String? {
        return user.getString("userId", "")
    }

    fun saveName(name: String) {
        user.edit().putString("name", name).apply()
    }

    fun getName(): String? {
        return user.getString("name", "")
    }

}