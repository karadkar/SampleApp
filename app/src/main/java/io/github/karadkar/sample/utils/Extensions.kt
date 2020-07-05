package io.github.karadkar.sample.utils

import android.view.View
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.addTo(disposable: CompositeDisposable) {
    disposable.add(this)
}

fun String.containsAtleastOne(matcher: (char: Char) -> Boolean): Boolean {
    this.onEach { char: Char ->
        if (matcher(char)) return true
    }
    return false
}

fun View.visibleOrGone(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}