package io.github.karadkar.sample.main

data class LocationListItem(
    val title: String,
    val date: String,
    val image: String,
    val isFavourite: Boolean = false
)