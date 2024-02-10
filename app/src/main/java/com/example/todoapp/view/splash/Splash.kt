package com.example.todoapp.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.todoapp.R
import com.example.todoapp.view.mainView.MainView

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSpash= installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Thread.sleep(800)
        screenSpash.setKeepOnScreenCondition{true}
        val intent = Intent(this, MainView::class.java)
        startActivity(intent)
        finish()
    }
}