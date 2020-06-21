package io.github.karadkar.sample

import android.content.Context
import android.content.res.Resources
import android.util.Log

val Int.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.toDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

fun Int.getDimension(context: Context) = context.resources.getDimension(this).toInt()

fun Any.logError(message: String, t: Throwable? = null) {
    Log.e(this.javaClass.simpleName, message, t)
}