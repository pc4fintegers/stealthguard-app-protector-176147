package com.stealthguard.util

import android.app.AppOpsManager
import android.content.Context
import android.os.Build

/**
 * PUBLIC_INTERFACE
 * Utilities for checking usage stats access.
 */
object AppOpsUtils {
    fun hasUsageAccess(ctx: Context): Boolean {
        val aom = ctx.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            aom.unsafeCheckOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), ctx.packageName)
        } else {
            aom.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), ctx.packageName)
        }
        return mode == AppOpsManager.MODE_ALLOWED
    }
}
