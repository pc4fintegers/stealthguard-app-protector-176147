package com.stealthguard

import android.app.Application
import android.util.Log

/**
 * PUBLIC_INTERFACE
 * Application entry point.
 *
 * Initializes global AppContext and logs startup.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppContext.init(this)
        Log.i("StealthGuard", "App started")
    }
}
