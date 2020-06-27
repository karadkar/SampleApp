package io.github.karadkar.sample.utils

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