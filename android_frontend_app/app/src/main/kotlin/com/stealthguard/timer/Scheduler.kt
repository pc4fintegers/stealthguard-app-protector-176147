package com.stealthguard.timer

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.stealthguard.data.db.entities.LockRule
/**
 * PUBLIC_INTERFACE
 * Schedules rules using WorkManager, with AlarmManager fallback.
 */
class Scheduler(
    private val context: Context
) {
    fun schedule(rule: LockRule) {
        val delay = rule.triggerAtMillis - System.currentTimeMillis()
        if (delay <= 0) return
        try {
            val req = OneTimeWorkRequestBuilder<LockWorker>()
                .setInputData(
                    Data.Builder()
                        .putString("pkg", rule.packageName)
                        .putString("action", rule.action)
                        .build()
                )
                .build()
            WorkManager.getInstance(context).enqueue(req)
        } catch (_: Exception) {
            val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AlarmReceiver::class.java).apply {
                putExtra("pkg", rule.packageName)
                putExtra("action", rule.action)
            }
            val pi = PendingIntent.getBroadcast(
                context, rule.id.toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, rule.triggerAtMillis, pi)
        }
    }

    fun cancel(rule: LockRule) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pi = PendingIntent.getBroadcast(
            context, rule.id.toInt(), intent, PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        if (pi != null) am.cancel(pi)
    }
}
