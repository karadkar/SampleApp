package io.github.karadkar.sample

import android.content.Context
import android.util.Log
import android.widget.Toast

fun Any.logMessage(
    message: String,
    tag: String = this.javaClass.simpleName,
    logLevel: Int = Log.ERROR
) {
    Log.println(logLevel, tag, message)
}

fun String.makeToast(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this, duration).show()
}