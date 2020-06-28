package io.github.karadkar.sample.dashboard

import io.github.karadkar.sample.dashboard.models.DashboardListItem

interface DashboardListContract {
    fun onClickDashboardNewItem(item: DashboardListItem)
}