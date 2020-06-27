package io.github.karadkar.sample.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.github.karadkar.sample.login.repository.LoginRepository
import io.github.karadkar.sample.utils.addTo
import io.github.karadkar.sample.utils.logError
import io.reactivex.disposables.CompositeDisposable

class LoginViewModel(private val repo: LoginRepository) : ViewModel() {
    private val disposable = CompositeDisposable()


    fun setNightMode(yes: Boolean) = repo.setNightMode(yes)

    fun isNightMode(): LiveData<Boolean> = repo.isNightMode()

    fun login() {
        repo.login(userName = "test@worldofplay.in", password = "Worldofplay@2020")
            .subscribe({
                logError("api call success")
            }, {
                logError("login error", it)
            }).addTo(disposable)
    }
}