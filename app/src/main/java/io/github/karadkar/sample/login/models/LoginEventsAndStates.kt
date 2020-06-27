package io.github.karadkar.sample.login.models

sealed class LoginEvent {
    object ScreenLoadEvent : LoginEvent()
    data class OnClickLoginEvent(val username: String, val password: String) : LoginEvent()
    data class EnableDarkThemeEvent(val enable: Boolean) : LoginEvent()
}

sealed class LoginEventResult {
    object ScreenLoadResult : LoginEventResult()

    sealed class LoginResult : LoginEventResult() {
        data class Success(val token: String) : LoginResult()
        abstract class Error(open val message: String) : LoginResult()
        data class ApiError(override val message: String) : Error(message)
        data class UserNameError(override val message: String) : Error(message)
        data class PasswordError(override val message: String) : Error(message)
    }

    data class EnableDarkThemeResult(val enable: Boolean) : LoginEventResult()
}

// persistable ui states
data class LoginUiState(
    val userNameError: String? = null,
    val passwordError: String? = null,
    val loginApiError: String? = null,
    val enableDarkTheme: Boolean = false,
    val loading: Boolean = false
)

// one shot ui state
sealed class LoginUiEffects {
    object LoginSuccess : LoginUiEffects()
}