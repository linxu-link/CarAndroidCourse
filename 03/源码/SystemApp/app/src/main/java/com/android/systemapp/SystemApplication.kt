package com.android.systemapp

import android.app.Application
import android.content.Intent
import android.util.Log

class SystemApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.e("System", "System APP started")
        val intent = Intent()
        intent.setPackage("com.android.systemapp")
        intent.setAction("com.android.systemapp.action")
        startService(intent)
    }
}