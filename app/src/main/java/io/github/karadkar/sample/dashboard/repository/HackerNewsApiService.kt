package io.github.karadkar.sample.dashboard.repository

import io.reactivex.Single
import retrofit2.http.GET

interface HackerNewsApiService {
    @GET("v0/topstories.json")
    fun topStories(): Single<List<Int>>
}