package io.github.karadkar.sample.dashboard.repository

import io.github.karadkar.sample.utils.AppRxSchedulers

class DashboardRepository(
    private val apiService: HackerNewsApiService,
    private val schedulers: AppRxSchedulers
) {

    fun fetchTopStories() = apiService.topStories()
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.main())
}