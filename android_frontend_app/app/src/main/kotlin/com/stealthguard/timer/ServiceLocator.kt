package com.stealthguard.timer

import android.content.Context
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.stealthguard.data.db.StealthGuardDatabase
import com.stealthguard.data.prefs.SecurePrefs
import com.stealthguard.data.repository.AppRepository

/**
 * PUBLIC_INTERFACE
 * Lightweight service locator for Worker context (avoids Hilt in workers).
 */
object ServiceLocator {
    @Volatile private var repo: AppRepository? = null

    fun repository(context: Context): AppRepository {
        return repo ?: synchronized(this) {
            repo ?: buildRepository(context.applicationContext).also { repo = it }
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
}
