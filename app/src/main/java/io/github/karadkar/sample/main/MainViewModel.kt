package io.github.karadkar.sample.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.karadkar.sample.db.ApiHelper
import io.github.karadkar.sample.db.LCE
import io.github.karadkar.sample.db.LocationApiService
import io.github.karadkar.sample.db.LocationDao
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val apiService: LocationApiService = ApiHelper.getLocationApiService(),
    private val disposible: CompositeDisposable = CompositeDisposable(),
    private val dao: LocationDao = LocationDao()
) : ViewModel() {

    init {
        fetchLocations()
    }

    // fixme: only expose liveData
    val apiResultLiveData = MutableLiveData<LCE<Boolean>>()

    fun fetchLocations() {
        val sub = apiService.getLocations()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { apiResultLiveData.value = LCE.loading(true) }
            .flatMap { result -> dao.save(result).andThen(Single.just(result)) }
            .subscribe({
                apiResultLiveData.value = LCE.content(true)
                Log.i("MainVM", "success")
            }, {
                apiResultLiveData.value = LCE.error(it)
                Log.e("MainVM", "error", it)
            })
        disposible.add(sub)
    }

    fun getLocations(): LiveData<List<LocationListItem>> {
        return LiveDataReactiveStreams.fromPublisher(dao.getLocations())
    }

    fun getCustomerName(): String = dao.getCustomerName()

    override fun onCleared() {
        disposible.dispose()
        dao.close()
        super.onCleared()
    }
}