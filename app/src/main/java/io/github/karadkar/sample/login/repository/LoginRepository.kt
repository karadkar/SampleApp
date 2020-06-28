package io.github.karadkar.sample.login.repository

import android.content.SharedPreferences
import android.util.Patterns
import io.github.karadkar.sample.utils.AppRxSchedulers
import io.github.karadkar.sample.utils.SampleConstants
import io.reactivex.Single

class LoginRepository(
    private val apiService: LoginApiService,
    private val schedulers: AppRxSchedulers,
    private val sharedPreferences: SharedPreferences
) {
    private var isDarkModeEnabled = false

    init {
        isDarkModeEnabled = sharedPreferences.getBoolean(SampleConstants.PrefKeys.KEY_DARK_MODE_ENABLED, true)
    }

    fun toggleDarkMode(enable: Boolean) {
        isDarkModeEnabled = enable
        sharedPreferences.edit().run {
            putBoolean(SampleConstants.PrefKeys.KEY_DARK_MODE_ENABLED, enable)
            apply()
        }
    }

    fun isDarkModeEnabled() = isDarkModeEnabled

    fun login(userName: String, password: String): Single<LoginResponse.Success> {
        val request = LoginRequest().apply {
            this.userName = userName
            this.password = password
        }

        return apiService.login(request)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.main())
    }


    fun isValidEmailId(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()
}