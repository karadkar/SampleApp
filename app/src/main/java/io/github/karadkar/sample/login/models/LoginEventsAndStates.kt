package io.github.karadkar.sample.login.models

sealed class LoginEvent {
    object ScreenLoadEvent : LoginEvent()
    data class OnClickLoginEvent(val username: String, val password: String) : LoginEvent()
    data class EnableDarkThemeEvent(val enable: Boolean) : LoginEvent()
}

sealed class LoginEventResult {
    object ScreenLoadResult : LoginEventResult()

    object Loading : LoginEventResult()
    data class Success(val token: String) : LoginEventResult()
    data class ApiError(val message: String) : LoginEventResult()

    data class EmailValidationError(val userNameError: Int) : LoginEventResult()
    data class PasswordValidationError(val passwordError: Int) : LoginEventResult()

    data class EnableDarkThemeResult(val enable: Boolean) : LoginEventResult()
}

// persistable ui states
data class LoginUiState(
    val userNameError: Int? = null,
    val passwordError: Int? = null,
    val loginApiError: String? = null,
    val enableDarkTheme: Boolean = false,
    val loading: Boolean = false,
    val enableLoginButton: Boolean = false
)

// one shot ui state
sealed class LoginUiEffects {
    object LoginSuccess : LoginUiEffects()
}