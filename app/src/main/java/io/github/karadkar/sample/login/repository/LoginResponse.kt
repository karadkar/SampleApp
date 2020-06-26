package io.github.karadkar.sample.login.repository

import com.fasterxml.jackson.annotation.JsonProperty

sealed class LoginResponse {
    class Success : LoginResponse() {
        @JsonProperty("token")
        var token: String = ""
    }

    class Error : LoginResponse() {
        @JsonProperty("error")
        val error: String = ""
        @JsonProperty("error")
        val description: String = ""
    }
}