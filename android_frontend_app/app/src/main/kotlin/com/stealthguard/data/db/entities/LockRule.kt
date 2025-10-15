package com.stealthguard.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * PUBLIC_INTERFACE
 * Represents a scheduled rule to toggle lock/hide at a time.
 */
@Entity(tableName = "lock_rules")
data class LockRule(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val packageName: String,
    val action: String, // "LOCK" or "HIDE"
    val triggerAtMillis: Long
)
