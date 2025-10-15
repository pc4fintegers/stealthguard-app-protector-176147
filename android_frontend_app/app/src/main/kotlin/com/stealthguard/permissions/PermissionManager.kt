package com.stealthguard.permissions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi

/**
 * PUBLIC_INTERFACE
 * Helpers to navigate to system settings for special permissions.
 */
object PermissionManager {
    fun overlayGranted(ctx: Context): Boolean = Settings.canDrawOverlays(ctx)

    fun openOverlaySettings(ctx: Context) {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:${ctx.packageName}")
        ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        ctx.startActivity(intent)
    }

    fun openUsageAccessSettings(ctx: Context) {
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        ctx.startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun openNotificationSettings(ctx: Context) {
        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
            putExtra(Settings.EXTRA_APP_PACKAGE, ctx.packageName)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        ctx.startActivity(intent)
    }
}
