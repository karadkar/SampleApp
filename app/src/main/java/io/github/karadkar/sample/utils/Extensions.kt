package io.github.karadkar.sample.utils

import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Any.logError(message: String, t: Throwable? = null) {
    Log.e(this.javaClass.simpleName, message, t)
}

fun Disposable.addTo(disposable: CompositeDisposable) {
    disposable.add(this)
}