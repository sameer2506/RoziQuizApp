package com.quiz.app.ui.auth.activty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.quiz.app.AppPreferences
import com.quiz.app.R
import com.quiz.app.ui.home.activity.HomeActivity

class AuthenticationActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()

        val appPreferences = AppPreferences(this)

        if (appPreferences.getId() != "" || appPreferences.getName() != "") {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
    }
}