package com.example.hopitalainwzen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hopitalainwzen.Login.Login.login.LoginActivity
import java.util.*

class MainScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        Timer().schedule(object : TimerTask() {
            override fun run() {
                startActivity(Intent(this@MainScreen,LoginActivity::class.java))
                finish()
            }
        }, 1002)
    }
}