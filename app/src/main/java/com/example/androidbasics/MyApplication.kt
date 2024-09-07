package com.example.androidbasics

import android.app.Application
import android.content.Context

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val context: Context = this
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}