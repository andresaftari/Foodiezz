package com.andresaftari.foodiezz.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.andresaftari.foodiezz.MainActivity
import com.andresaftari.foodiezz.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        val background = object : Thread() {
            override fun run() {
                try {
                    sleep(1500)
                    startActivity(Intent(baseContext, MainActivity::class.java))
                } catch (e: Exception) {
                    Log.i(
                        "SplashScreen",
                        "Failed! ${e.message} --- ${e.printStackTrace()}"
                    )
                }
            }
        }
        background.start()
    }
}