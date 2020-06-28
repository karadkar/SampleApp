package io.github.karadkar.sample.login.models

sealed class LoginEvent {
    object ScreenLoadEvent : LoginEvent()
    data class OnClickLoginEvent(val username: String, val password: String) : LoginEvent()
    data class UserNameValidationCheckEvent(val username: String) : LoginEvent()
    data class PasswordValidationCheckEvent(val password: String) : LoginEvent()
    data class EnableDarkThemeEvent(val enable: Boolean) : LoginEvent()
}

sealed class LoginEventResult {
    object ScreenLoadResult : LoginEventResult()

    object ApiLoading : LoginEventResult()
    data class ApiSuccess(val token: String) : LoginEventResult()
    data class ApiError(val message: String) : LoginEventResult()

    data class EmailValidationError(val userNameError: Int) : LoginEventResult()
    data class PasswordValidationError(val passwordError: Int) : LoginEventResult()

    object UserNameValidationSuccess : LoginEventResult()
    object PasswordValidationSuccess : LoginEventResult()
    data class EnableDarkThemeResult(val enable: Boolean) : LoginEventResult()
}

// persistable ui states
data class LoginUiState(
    val userNameError: Int? = null,
    val passwordError: Int? = null,
    val loginApiError: String? = null,
    val enableDarkTheme: Boolean = false,
    val loading: Boolean = false,
    val enableLoginButton: Boolean = false,
    val isUserNameValid: Boolean = true,
    val isPasswordValid: Boolean = true
) {
    fun enableLoginButton(): Boolean = isUserNameValid && isPasswordValid
}

// one shot ui state
sealed class LoginUiEffects {
    object LoginSuccess : LoginUiEffects()
}