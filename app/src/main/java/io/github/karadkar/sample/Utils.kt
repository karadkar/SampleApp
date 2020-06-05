package io.github.karadkar.sample

import android.util.Log

fun Any.log(
    tag: String = this.javaClass.simpleName,
    message: String,
    t: Throwable? = null
) {
    Log.e(tag, message, t)
}