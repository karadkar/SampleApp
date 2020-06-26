package io.github.karadkar.sample.login.repository

import com.fasterxml.jackson.annotation.JsonProperty

class LoginRequest {
    @JsonProperty("username")
    var userName: String = ""

    @JsonProperty("password")
    var password: String = ""
}
