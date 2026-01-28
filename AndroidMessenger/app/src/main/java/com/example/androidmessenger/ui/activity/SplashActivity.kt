package com.example.androidmessenger.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.androidmessenger.data.repository.AuthRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private val authRepository = AuthRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        GlobalScope.launch {
            val isLoggedIn = authRepository.isUserLoggedIn()
            
            Handler(Looper.getMainLooper()).postDelayed({
                if (isLoggedIn) {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                } else {
                    startActivity(Intent(this@SplashActivity, AuthActivity::class.java))
                }
                finish()
            }, 1000)
        }
    }
}
