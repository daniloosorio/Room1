package com.daniloosorio.room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import java.util.*
import kotlin.concurrent.timerTask

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash)
        /////////////////////tiempo de duracion del splash////////////////
        val time= Timer()
        time.schedule(timerTask {goToLogin()},2000)
    }
    private fun goToLogin(){
        val intent= Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
