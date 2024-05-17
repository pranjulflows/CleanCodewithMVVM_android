package com.example.baseproject.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
    companion object {
        lateinit var myApp: MyApp
        fun getAppInstance(): MyApp {
            return myApp
        }
    }

    override fun onCreate() {
        super.onCreate()
        myApp = this
    }
}