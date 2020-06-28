package io.github.karadkar.sample.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.karadkar.sample.dashboard.models.DashboardListItem
import io.github.karadkar.sample.dashboard.repository.DashboardRepository
import io.github.karadkar.sample.utils.AppRxSchedulers
import io.github.karadkar.sample.utils.addTo
import io.github.karadkar.sample.utils.logError
import io.github.karadkar.sample.utils.logInfo
import io.reactivex.disposables.CompositeDisposable

class DashBoardViewModel(
    private val repository: DashboardRepository,
    private val schedulers: AppRxSchedulers
) : ViewModel() {

    private val listItems = MutableLiveData<List<DashboardListItem>>()
    private val disposable = CompositeDisposable()

    init {
        val dummyList = mutableListOf<DashboardListItem>()
        for (i in 1..100) {
            dummyList.add(
                DashboardListItem(id = i, title = "some list item $i", link = "https://www.google.com/")
            )
        }
        listItems.value = dummyList

        fetchTopStories()
    }

    private fun fetchTopStories() {
        repository.fetchTopStories()
            .subscribe({ stories ->
                logInfo("Top Stories: $stories")
            }, { t ->
                logError("error fetching top stories", t)
            }).addTo(disposable)
    }

    fun getListItems(): LiveData<List<DashboardListItem>> = listItems

    override fun onCleared() {
        disposable.dispose()
    }
}