package com.stealthguard

import android.content.Context
import androidx.room.Room
import com.stealthguard.data.db.StealthGuardDatabase
import com.stealthguard.data.prefs.SecurePrefs
import com.stealthguard.data.repository.AppRepository
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.stealthguard.domain.usecase.*
import com.stealthguard.timer.Scheduler

/**
 * PUBLIC_INTERFACE
 * Simple Injector to provide app-level singletons without Hilt.
 */
object Injector {
    @Volatile private var repo: AppRepository? = null

    fun repository(ctx: Context): AppRepository {
        return repo ?: synchronized(this) {
            repo ?: buildRepository(ctx.applicationContext).also { repo = it }
        }
    }

    private fun buildRepository(ctx: Context): AppRepository {
        val db = Room.databaseBuilder(ctx, StealthGuardDatabase::class.java, "stealthguard.db")
            .fallbackToDestructiveMigration()
            .build()
        val pm = ctx.packageManager
        val masterKey = MasterKey.Builder(ctx).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
        val esp = EncryptedSharedPreferences.create(
            ctx,
            "secure_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        val securePrefs = SecurePrefs(esp)
        val scheduler = Scheduler(ctx)
        return AppRepository(
            context = ctx,
            pm = pm,
            hiddenDao = db.hiddenAppDao(),
            lockedDao = db.lockedAppDao(),
            ruleDao = db.lockRuleDao(),
            securePrefs = securePrefs,
            scheduler = scheduler
        )
    }

    fun getInstalledApps(ctx: Context) = GetInstalledAppsUseCase(repository(ctx))
    fun toggleHide(ctx: Context) = ToggleHideAppUseCase(repository(ctx))
    fun toggleLock(ctx: Context) = ToggleLockAppUseCase(repository(ctx))
    fun setPin(ctx: Context) = SetPinUseCase(repository(ctx))
    fun validatePin(ctx: Context) = ValidatePinUseCase(repository(ctx))
    fun scheduleRule(ctx: Context) = ScheduleLockRuleUseCase(repository(ctx))
}
