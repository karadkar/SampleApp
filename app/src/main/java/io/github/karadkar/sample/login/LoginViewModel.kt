package io.github.karadkar.sample.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.karadkar.sample.login.models.LoginEvent
import io.github.karadkar.sample.login.models.LoginEventResult
import io.github.karadkar.sample.login.models.LoginUiEffects
import io.github.karadkar.sample.login.models.LoginUiState
import io.github.karadkar.sample.login.repository.LoginRepository
import io.github.karadkar.sample.utils.SingleLiveEvent
import io.github.karadkar.sample.utils.addTo
import io.github.karadkar.sample.utils.logError
import io.github.karadkar.sample.utils.logInfo
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
                obj.ofType(LoginEvent.OnClickLoginEvent::class.java).map {
                    return@map LoginEventResult.LoginResult.Success(token = "some-token")
                }
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
                is LoginEventResult.LoginResult.Success -> {
                    state.copy(userNameError = null, passwordError = null, loginApiError = null)
                }
                else -> error("Event Result $result not handled")
            }
        }.distinctUntilChanged()
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