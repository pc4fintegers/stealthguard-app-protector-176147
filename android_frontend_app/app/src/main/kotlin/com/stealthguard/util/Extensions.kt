package com.stealthguard.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable

/**
 * PUBLIC_INTERFACE
 * Drawable conversion helpers.
 */
fun Drawable.toBitmap(): Bitmap {
    if (this is BitmapDrawable) return this.bitmap
    val bmp = Bitmap.createBitmap(
        intrinsicWidth.takeIf { it > 0 } ?: 1,
        intrinsicHeight.takeIf { it > 0 } ?: 1,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bmp)
    setBounds(0, 0, canvas.width, canvas.height)
    draw(canvas)
    return bmp
}
