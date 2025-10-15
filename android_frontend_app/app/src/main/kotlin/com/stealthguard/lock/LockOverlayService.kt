package com.stealthguard.lock

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.ui.platform.ComposeView
import androidx.core.app.NotificationCompat
import com.stealthguard.R
import com.stealthguard.data.repository.AppRepository
import com.stealthguard.timer.ServiceLocator

/**
 * PUBLIC_INTERFACE
 * Foreground service that displays a full-screen overlay requiring PIN for locked apps.
 */
class LockOverlayService : Service() {

    private val repo: AppRepository by lazy { ServiceLocator.repository(this) }
    private var wm: WindowManager? = null
    private var composeView: ComposeView? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        createChannel()
        startForeground(1, buildNotification())
        wm = getSystemService(WINDOW_SERVICE) as WindowManager
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else
                WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
            PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.TOP or Gravity.START
        composeView = ComposeView(this).also { view ->
            view.setContent {
                LockOverlayComposable(onSubmitPin = { pin ->
                    if (repo.validatePin(pin)) {
                        removeOverlay()
                    }
                })
            }
            wm?.addView(view, params)
        }
    }

    override fun onDestroy() {
        removeOverlay()
        super.onDestroy()
    }

    private fun removeOverlay() {
        composeView?.let { wm?.removeView(it) }
        composeView = null
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun createChannel() {
        val channelId = "lock_overlay"
        val nm = getSystemService(NotificationManager::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (nm.getNotificationChannel(channelId) == null) {
                nm.createNotificationChannel(
                    NotificationChannel(channelId, "Lock Overlay", NotificationManager.IMPORTANCE_LOW)
                )
            }
        }
    }

    private fun buildNotification(): Notification {
        return NotificationCompat.Builder(this, "lock_overlay")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("App Locked")
            .setContentText("Unlock to continue")
            .setOngoing(true)
            .build()
    }
}
