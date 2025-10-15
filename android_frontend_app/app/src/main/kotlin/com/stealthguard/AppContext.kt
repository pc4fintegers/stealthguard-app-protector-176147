package com.stealthguard

import android.annotation.SuppressLint
import android.app.Application

/**
 * PUBLIC_INTERFACE
 * Global application context holder for simple injection.
 */
object AppContext {
    @SuppressLint("StaticFieldLeak")
    lateinit var app: Application
        private set

    fun init(application: Application) {
        app = application
    }
}
