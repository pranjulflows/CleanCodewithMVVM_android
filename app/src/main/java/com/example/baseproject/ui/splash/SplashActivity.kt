package com.example.baseproject.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.example.baseproject.base.BaseActivity
import com.example.baseproject.databinding.ActivitySplashBinding
import com.example.baseproject.ui.main.MainActivity
import com.example.baseproject.utils.after
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        after(2000) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}