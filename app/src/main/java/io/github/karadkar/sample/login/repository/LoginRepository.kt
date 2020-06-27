package io.github.karadkar.sample.login.repository

import android.util.Patterns
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.fasterxml.jackson.databind.ObjectMapper
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginRepository(val apiService: LoginApiService, val objectMapper: ObjectMapper) {

    val nightMode = MutableLiveData<Int>()

    init {
        nightMode.value = AppCompatDelegate.MODE_NIGHT_NO
    }

    fun login(userName: String, password: String): Single<LoginResponse.Success> {
        val request = LoginRequest().apply {
            this.userName = userName
            this.password = password
        }

        return apiService.login(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun setNightMode(yes: Boolean) {
        // todo: persist in preferences
        nightMode.value = if (yes) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
    }

    fun isNightMode(): LiveData<Boolean> = Transformations.map(nightMode) { value: Int ->
        return@map (value == AppCompatDelegate.MODE_NIGHT_YES)
    }

    fun isValidEmailId(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()
}