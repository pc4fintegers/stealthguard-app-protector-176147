package com.stealthguard.timer

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.stealthguard.data.repository.AppRepository
import com.stealthguard.timer.ServiceLocator

/**
 * PUBLIC_INTERFACE
 * WorkManager job to apply a lock/hide action at scheduled time.
 */
class LockWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {
    private val repo: AppRepository by lazy { ServiceLocator.repository(appContext) }
    override suspend fun doWork(): Result {
        val pkg = inputData.getString("pkg") ?: return Result.failure()
        val action = inputData.getString("action") ?: "LOCK"
        when (action) {
            "LOCK" -> repo.toggleLocked(pkg, true)
            "HIDE" -> repo.toggleHidden(pkg, true)
        }
        return Result.success()
    }
}
