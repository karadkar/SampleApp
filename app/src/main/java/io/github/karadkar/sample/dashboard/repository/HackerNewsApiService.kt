package io.github.karadkar.sample.dashboard.repository

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface HackerNewsApiService {
    @GET("v0/topstories.json")
    fun topStories(): Observable<List<Int>>

    @GET("v0/item/{story-id}.json")
    fun getStory(@Path("story-id") storyId: Int): Observable<NewsStoryResponse>
}