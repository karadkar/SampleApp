package io.github.karadkar.sample.db

import io.reactivex.Single
import retrofit2.http.GET

interface LocationApiService {

    @GET("v2/5c261ccb3000004f0067f6ec")
    fun getLocations(): Single<LocationResult>

}