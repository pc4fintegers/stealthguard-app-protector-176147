package com.stealthguard.timer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

/**
 * PUBLIC_INTERFACE
 * AlarmManager receiver to trigger LockWorker as a fallback.
 */
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val pkg = intent.getStringExtra("pkg") ?: return
        val action = intent.getStringExtra("action") ?: "LOCK"
        val req = OneTimeWorkRequestBuilder<LockWorker>()
            .setInputData(
                Data.Builder()
                    .putString("pkg", pkg)
                    .putString("action", action)
                    .build()
            ).build()
        WorkManager.getInstance(context).enqueue(req)
    }
}

/**
 * PUBLIC_INTERFACE
 * Receives boot to reschedule alarms after reboot.
 */
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // In a full implementation we would reload rules from DB and reschedule.
        // Kept minimal for this iteration.
    }
}
