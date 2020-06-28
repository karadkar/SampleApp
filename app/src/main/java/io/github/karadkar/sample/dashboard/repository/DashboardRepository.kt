package io.github.karadkar.sample.dashboard.repository

import io.github.karadkar.sample.dashboard.models.DashboardListItem
import io.github.karadkar.sample.utils.AppRxSchedulers
import io.reactivex.Observable

class DashboardRepository(
    private val apiService: HackerNewsApiService,
    private val schedulers: AppRxSchedulers
) {
    private var topStoriesIds: List<Int> = emptyList()
    private val chunkSize = 20
    private val stories = ArrayList<DashboardListItem>(100)

    fun fetchAtBeginning(): Observable<List<DashboardListItem>> {
        return fetchTopStories()
            .switchMap { ids ->
                if (ids.size >= chunkSize) {
                    fetchStories(ids.subList(0, chunkSize))
                } else {
                    fetchStories(ids)
                }
            }.map { fetchedStories ->
                // always return stored data
                stories.addAll(fetchedStories)
                return@map stories
            }
    }

    fun fetchStories(ids: List<Int>): Observable<List<DashboardListItem>> {
        return Observable.fromIterable(ids)
            .flatMap { id -> fetchStory(id) }
            .buffer(ids.size)
    }

    fun fetchStory(id: Int): Observable<DashboardListItem> {
        return apiService.getStory(id)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.main())
            .map { DashboardListItem(id = it.id, title = it.newTitle, link = it.newsUrl) }

        // todo: add retry policy on error
    }

    fun fetchTopStories(): Observable<List<Int>> {
        return apiService.topStories()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.main())
            .doOnNext { stories ->
                topStoriesIds = stories
            }
    }
}