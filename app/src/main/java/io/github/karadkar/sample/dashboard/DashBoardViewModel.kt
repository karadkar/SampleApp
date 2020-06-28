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

    private val stories = MutableLiveData<List<DashboardListItem>>()
    private val disposable = CompositeDisposable()

    init {

        repository.fetchAtBeginning()
            .subscribe({ stories ->
                logInfo("Top Stories: $stories")
                this.stories.value = stories
            }, { t ->
                logError("error fetching top stories", t)
            }).addTo(disposable)

    }

    private fun fetchTopStories() {

    }

    fun getListItems(): LiveData<List<DashboardListItem>> = stories

    override fun onCleared() {
        disposable.dispose()
    }
}