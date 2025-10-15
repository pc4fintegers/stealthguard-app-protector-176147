package com.stealthguard.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.stealthguard.data.db.dao.HiddenAppDao
import com.stealthguard.data.db.dao.LockRuleDao
import com.stealthguard.data.db.dao.LockedAppDao
import com.stealthguard.data.db.entities.HiddenApp
import com.stealthguard.data.db.entities.LockRule
import com.stealthguard.data.db.entities.LockedApp

/**
 * PUBLIC_INTERFACE
 * Room database for StealthGuard.
 */
@Database(
    entities = [HiddenApp::class, LockedApp::class, LockRule::class],
    version = 1,
    exportSchema = false
)
abstract class StealthGuardDatabase : RoomDatabase() {
    abstract fun hiddenAppDao(): HiddenAppDao
    abstract fun lockedAppDao(): LockedAppDao
    abstract fun lockRuleDao(): LockRuleDao
}
