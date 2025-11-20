package mobiledev.unb.ca.bubblegamelab

import android.content.Context

// Extension functions for the screen display size
val Context.displayWidth: Int
    get() = resources.displayMetrics.widthPixels

val Context.displayHeight: Int
    get() = resources.displayMetrics.heightPixels