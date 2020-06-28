package io.github.karadkar.sample.dashboard.repository

import io.github.karadkar.sample.dashboard.models.DashboardListItem
import io.github.karadkar.sample.utils.AppRxSchedulers
import io.github.karadkar.sample.utils.logInfo
import io.reactivex.Observable

class DashboardRepository(
    private val apiService: HackerNewsApiService,
    private val schedulers: AppRxSchedulers
) {

    private var topStoriesIds: List<Int> = emptyList()
    private val chunkSize = 20

    private val stories = LinkedHashMap<Int, DashboardListItem>(100)
    private val storyIds = HashSet<Int>(100)

    fun fetchNext(): Observable<List<DashboardListItem>> {
        return Observable.fromIterable(topStoriesIds)
            .filter { id -> !stories.contains(id) }
            .buffer(topStoriesIds.size - stories.size)
            .doOnNext { logInfo("found ${it.size} downloadable ids") }
            .map { totalIds ->
                return@map totalIds.subList(0, chunkSize)
            }
            .doOnNext { logInfo("fetching ${it.size} stories") }
            .switchMap { ids ->
                fetchStories(ids)
            }
            .map { fetchedStories ->
                // always return stored data
                for (story in fetchedStories) {
                    stories[story.id] = story
                }

                // to retain the order
                val result = ArrayList<DashboardListItem>(stories.size)
                for (key in stories.keys) {
                    result.add(stories[key]!!)
                }
                return@map result as List<DashboardListItem>
            }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.main())
    }

    private fun fetchStories(ids: List<Int>): Observable<List<DashboardListItem>> {
        return Observable.fromIterable(ids)
            .flatMap { id -> fetchStory(id) }
            .buffer(ids.size)
    }

    private fun fetchStory(id: Int): Observable<DashboardListItem> {
        return apiService.getStory(id)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.main())
            .map { DashboardListItem(id = it.id, title = it.newTitle, link = it.newsUrl) }

        // todo: add retry policy on error
    }

    fun fetchTopStoryIds(): Observable<List<Int>> {
        return apiService.topStories()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.main())
            .doOnNext { stories ->
                topStoriesIds = stories
            }
    }
}