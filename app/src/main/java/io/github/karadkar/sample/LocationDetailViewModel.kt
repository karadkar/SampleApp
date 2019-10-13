package io.github.karadkar.sample

import androidx.lifecycle.ViewModel
import io.github.karadkar.sample.db.LocationDao
import io.reactivex.disposables.CompositeDisposable

class LocationDetailViewModel(
    private val disposible: CompositeDisposable = CompositeDisposable(),
    private val dao: LocationDao = LocationDao()
) : ViewModel() {

    // todo: do these in single database query
    fun getLocationImage(placeName: String) = dao.getLocationImage(placeName)

    fun getLocationDate(placeName: String) = dao.getLocationDate(placeName)
    fun getLocationDescription(placeName: String) = dao.getLocationDescription(placeName)

    override fun onCleared() {
        disposible.dispose()
        dao.close()
        super.onCleared()
    }
}