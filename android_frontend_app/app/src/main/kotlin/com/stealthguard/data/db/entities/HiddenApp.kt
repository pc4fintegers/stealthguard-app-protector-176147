package com.stealthguard.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * PUBLIC_INTERFACE
 * Entity marking an app as hidden by package name.
 */
@Entity(tableName = "hidden_apps")
data class HiddenApp(
    @PrimaryKey val packageName: String
)
