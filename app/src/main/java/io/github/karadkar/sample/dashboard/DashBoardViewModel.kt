package io.github.karadkar.sample.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.karadkar.sample.dashboard.models.DashboardListItem

class DashBoardViewModel : ViewModel() {

    private val listItems = MutableLiveData<List<DashboardListItem>>()

    init {
        val dummyList = mutableListOf<DashboardListItem>()
        for (i in 1..100) {
            dummyList.add(
                DashboardListItem(id = i, title = "some list item $i", link = "https://www.google.com/")
            )
        }
        listItems.value = dummyList
    }

    fun getListItems(): LiveData<List<DashboardListItem>> = listItems
}