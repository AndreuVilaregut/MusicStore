package com.dam.andreu.singleton

import android.app.Application
import android.util.Log

class AppSingletonKt : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.i("AppSingleton", "AppSingletonKt:onCreate")
        AppSingleton.getInstance()
    }
}
