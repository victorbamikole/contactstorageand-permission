package com.example.storageandpermissiontask6.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.storageandpermissiontask6.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val handler = Handler(Looper.getMainLooper())

        //Create the splash screen activity
        handler.postDelayed({
            var intent = Intent(this, ContactsActivity::class.java)
            startActivity(intent)
        }, 1000)
    }
}