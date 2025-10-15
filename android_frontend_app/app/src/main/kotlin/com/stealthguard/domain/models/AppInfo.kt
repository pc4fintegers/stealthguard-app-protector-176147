package com.stealthguard.domain.models

import android.graphics.drawable.Drawable

/**
 * PUBLIC_INTERFACE
 * Lightweight app info for UI lists.
 */
data class AppInfo(
    val appName: String,
    val packageName: String,
    val icon: Drawable?,
    val isHidden: Boolean,
    val isLocked: Boolean
)
