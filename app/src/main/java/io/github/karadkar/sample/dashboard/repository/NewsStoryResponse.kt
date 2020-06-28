package io.github.karadkar.sample.dashboard.repository

import com.fasterxml.jackson.annotation.JsonProperty

class NewsStoryResponse {
    @JsonProperty("id")
    var id: Int = 0

    @JsonProperty("title")
    var newTitle: String = ""

    @JsonProperty("url")
    var newsUrl: String = ""
}