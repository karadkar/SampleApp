package io.github.karadkar.sample

data class LocationListItem(
    val title: String,
    val date: String,
    val image: String,
    val isFavourite: Boolean = false
)