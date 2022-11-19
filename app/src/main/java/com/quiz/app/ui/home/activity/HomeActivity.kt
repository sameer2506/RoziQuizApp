package com.quiz.app.ui.home.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.quiz.app.AppPreferences
import com.quiz.app.R
import com.quiz.app.utils.log

class HomeActivity : AppCompatActivity() {

    private lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        appPreferences = AppPreferences(this)

        log(appPreferences.getId().toString())
        log(appPreferences.getName().toString())

    }
}