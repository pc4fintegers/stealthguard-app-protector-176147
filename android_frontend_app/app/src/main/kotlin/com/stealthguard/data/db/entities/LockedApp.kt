package com.stealthguard.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * PUBLIC_INTERFACE
 * Entity marking an app as locked by package name.
 */
@Entity(tableName = "locked_apps")
data class LockedApp(
    @PrimaryKey val packageName: String
)
