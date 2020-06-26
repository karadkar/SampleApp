package io.github.karadkar.sample.login.repository

import io.reactivex.Single
import retrofit2.http.Body

interface LoginApiService {
    @retrofit2.http.POST("login")
    fun login(@Body loginRequest: LoginRequest): Single<LoginResponse.Success>

}