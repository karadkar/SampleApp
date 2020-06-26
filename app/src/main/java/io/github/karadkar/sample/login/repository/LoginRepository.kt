package io.github.karadkar.sample.login.repository

import com.fasterxml.jackson.databind.ObjectMapper
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginRepository(val apiService: LoginApiService, val objectMapper: ObjectMapper) {

    fun login(userName: String, password: String): Single<LoginResponse.Success> {
        val request = LoginRequest().apply {
            this.userName = userName
            this.password = password
        }

        return apiService.login(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}