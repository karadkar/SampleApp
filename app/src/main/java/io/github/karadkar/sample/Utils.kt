package io.github.karadkar.sample

import android.util.Log

fun Any.logMessage(
    message: String,
    tag: String = this.javaClass.simpleName,
    t: Throwable? = null
) {
    Log.e(tag, message, t)
}