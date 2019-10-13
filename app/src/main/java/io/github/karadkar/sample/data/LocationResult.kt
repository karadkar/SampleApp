package io.github.karadkar.sample.data

import com.fasterxml.jackson.annotation.JsonProperty
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class LocationResult : RealmObject() {

    @JsonProperty("cust_name")
    @PrimaryKey
    var customerName: String = ""

    @JsonProperty("locations")
    var locations: RealmList<LocationEntity> = RealmList()
}