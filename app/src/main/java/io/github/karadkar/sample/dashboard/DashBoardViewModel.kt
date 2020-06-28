package io.github.karadkar.sample.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.karadkar.sample.dashboard.models.DashboardListItem
import io.github.karadkar.sample.dashboard.repository.DashboardRepository
import io.github.karadkar.sample.utils.*
import io.reactivex.disposables.CompositeDisposable

class DashBoardViewModel(
    private val repository: DashboardRepository,
    private val schedulers: AppRxSchedulers
) : ViewModel() {

    private val stories = MutableLiveData<Lce<List<DashboardListItem>>>()
    private val disposable = CompositeDisposable()

    init {

        repository.fetchTopStoryIds()
            .switchMap {
                repository.fetchNext()
            }
            .doOnSubscribe {
                stories.value = Lce.Loading()
            }
            .subscribe({ stories ->
                logInfo("Top Stories: $stories")
                this.stories.value = Lce.Content(stories)
            }, { t ->
                logError("error fetching top stories", t)
                this.stories.value = Lce.Error(t)
            }).addTo(disposable)

    }

    fun fetchNext() {
        repository.fetchNext()
            .doOnSubscribe {
                stories.value = Lce.Loading()
            }
            .subscribe({ stories ->
                logInfo("Top Stories: $stories")
                this.stories.value = Lce.Content(stories)
            }, { t ->
                logError("error fetching top stories", t)
                this.stories.value = Lce.Error(t)
            }).addTo(disposable)
    }

    fun getListItems(): LiveData<Lce<List<DashboardListItem>>> = stories

    override fun onCleared() {
        disposable.dispose()
    }
}