package io.github.karadkar.sample.login

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val nightMode = MutableLiveData<Int>()

    init {
        nightMode.value = AppCompatDelegate.MODE_NIGHT_NO
    }

    fun setNightMode(yes: Boolean) {
        nightMode.value = if (yes) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
    }

    fun nightModeValue(): LiveData<Int> = nightMode

    fun isNightMode(): LiveData<Boolean> = Transformations.map(nightMode) { value: Int ->
        return@map (value == AppCompatDelegate.MODE_NIGHT_YES)
    }
}