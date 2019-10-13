package io.github.karadkar.sample.db

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class LocationEntity : RealmObject() {

    // todo: improve primary key
    @JsonProperty("place")
    @PrimaryKey
    var place: String = ""

    @JsonProperty("url")
    var imageUrl: String = ""

    @JsonProperty("date")
    var dateString: String = ""

    @JsonProperty("rate")
    var price: Long = 0L

    @JsonProperty("description")
    var description: String = ""

    @JsonIgnore
    var isFavourite: Boolean = false

    fun getDisaplyDate(): String {
        return dateString.trim().split(" ").let { chunks ->
            if (chunks.size == 2) {
                return@let "${chunks[1]} ${chunks[0]}, 2019"
            } else {
                return@let dateString
            }
        }
    }
}