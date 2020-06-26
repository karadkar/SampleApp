package io.github.karadkar.sample.login

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import io.github.karadkar.sample.login.repository.LoginRepository
import io.github.karadkar.sample.utils.addTo
import io.github.karadkar.sample.utils.logError
import io.reactivex.disposables.CompositeDisposable

class LoginViewModel(private val repo: LoginRepository) : ViewModel() {
    private val nightMode = MutableLiveData<Int>()
    private val disposable = CompositeDisposable()

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

    fun login() {
        repo.login(userName = "test@worldofplay.in", password = "Worldofplay@2020")
            .subscribe({
                logError("api call success")
            }, {
                logError("login error", it)
            }).addTo(disposable)
    }
}