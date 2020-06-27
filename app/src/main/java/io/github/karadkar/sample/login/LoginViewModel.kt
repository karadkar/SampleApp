package io.github.karadkar.sample.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.karadkar.sample.R
import io.github.karadkar.sample.login.models.LoginEvent
import io.github.karadkar.sample.login.models.LoginEventResult
import io.github.karadkar.sample.login.models.LoginUiEffects
import io.github.karadkar.sample.login.models.LoginUiState
import io.github.karadkar.sample.login.repository.LoginRepository
import io.github.karadkar.sample.utils.*
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

class LoginViewModel(private val repo: LoginRepository) : ViewModel() {
    private val disposable = CompositeDisposable()

    private val viewEventEmitter = PublishSubject.create<LoginEvent>()

    // persistable ui state
    val viewState = MutableLiveData<LoginUiState>()

    // on-shot ui state
    val viewEffect = SingleLiveEvent<LoginUiEffects>()

    init {
        viewEventEmitter
            .doOnNext { logInfo("event $it") }
            .share().also { event ->
                event.eventToResult()
                    .doOnNext { logInfo("result $it") }
                    .resultToViewState()
                    .doOnNext { logInfo("state $it") }
                    .subscribe({
                        viewState.value = it
                    }, {
                        logError("error processing event $event", t = it)
                    }).addTo(disposable)
            }
    }

    private fun Observable<LoginEvent>.eventToResult(): Observable<LoginEventResult> {
        return this.publish { obj ->
            Observable.merge(
                obj.ofType(LoginEvent.ScreenLoadEvent::class.java).map {
                    return@map LoginEventResult.ScreenLoadResult
                },
                obj.ofType(LoginEvent.EnableDarkThemeEvent::class.java).map {
                    return@map LoginEventResult.EnableDarkThemeResult(enable = it.enable)
                },
                obj.ofType(LoginEvent.ValidationCheckEvent::class.java).validationEventToResult(),
                obj.ofType(LoginEvent.OnClickLoginEvent::class.java).loginEventToResult()
            )
        }
    }

    private fun Observable<LoginEventResult>.resultToViewState(): Observable<LoginUiState> {
        // gives previousUiState and new Result
        return this.scan(LoginUiState()) { state, result ->
            return@scan when (result) {
                is LoginEventResult.ScreenLoadResult -> state.copy()
                is LoginEventResult.EnableDarkThemeResult -> {
                    state.copy(enableDarkTheme = result.enable)
                }
                is LoginEventResult.ValidationSuccess -> {
                    state.copy(
                        userNameError = null,
                        passwordError = null,
                        loginApiError = null,
                        enableLoginButton = true,
                        loading = false
                    )
                }
                is LoginEventResult.ApiSuccess -> {
                    state.copy(
                        userNameError = null,
                        passwordError = null,
                        loginApiError = null,
                        enableLoginButton = true,
                        loading = false
                    )
                }
                is LoginEventResult.PasswordValidationError -> {
                    state.copy(
                        userNameError = null,
                        passwordError = result.passwordError,
                        loginApiError = null,
                        enableLoginButton = false,
                        loading = false
                    )
                }
                is LoginEventResult.EmailValidationError -> {
                    state.copy(
                        userNameError = result.userNameError,
                        passwordError = null,
                        loginApiError = null,
                        enableLoginButton = false,
                        loading = false
                    )
                }
                is LoginEventResult.ApiLoading -> state.copy(loading = true)
                is LoginEventResult.ApiError -> {
                    state.copy(
                        userNameError = null,
                        passwordError = null,
                        loginApiError = null,
                        enableLoginButton = false,
                        loading = false
                    )
                }
                else -> error("Event Result $result not handled")
            }
        }.distinctUntilChanged()
    }

    private fun Observable<LoginEvent.ValidationCheckEvent>.validationEventToResult(): Observable<LoginEventResult> {
        return this.map { event ->
            val error = checkValidationError(event)
            if (error != null) {
                return@map error
            }
            return@map LoginEventResult.ValidationSuccess
        }
    }

    private fun Observable<LoginEvent.OnClickLoginEvent>.loginEventToResult(): Observable<LoginEventResult> {
        return this.switchMap { event ->
            return@switchMap repo.login(event.username, event.password)
                .map {
                    LoginEventResult.ApiSuccess(it.token) as LoginEventResult
                }
                .onErrorReturn { t ->
                    logError("error login $event", t = t)
                    LoginEventResult.ApiError(t.message ?: "api error")
                }.toObservable()
                .startWith(LoginEventResult.ApiLoading)
        }
    }

    // todo: improve readability
    private fun checkValidationError(event: LoginEvent.ValidationCheckEvent): LoginEventResult? {
        return if (!repo.isValidEmailId(event.username)) {
            LoginEventResult.EmailValidationError(R.string.error_invalid_email)
        } else if (event.password.length < 8 || event.password.length > 16) {
            LoginEventResult.PasswordValidationError(R.string.error_password_length)
        } else if (!event.password.containsAtleastOne { Character.isUpperCase(it) }) {
            LoginEventResult.PasswordValidationError(R.string.error_password_need_uppercase)
        } else if (!event.password.containsAtleastOne { Character.isLowerCase(it) }) {
            LoginEventResult.PasswordValidationError(R.string.error_password_need_lowercase)
        } else if (!event.password.containsAtleastOne { Character.isDigit(it) }) {
            LoginEventResult.PasswordValidationError(R.string.error_password_need_digit)
        } else if (!event.password.containsAtleastOne {
                !Character.isSpaceChar(it) && !Character.isDigit(it) && !Character.isLetter(it)
            }) {
            LoginEventResult.PasswordValidationError(R.string.error_password_need_special_char)
        } else {
            null
        }
    }

    fun submitEvent(event: LoginEvent) {
        viewEventEmitter.onNext(event)
    }

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