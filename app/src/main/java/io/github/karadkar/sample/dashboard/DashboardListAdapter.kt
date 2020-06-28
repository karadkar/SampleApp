package io.github.karadkar.sample.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.karadkar.sample.R
import io.github.karadkar.sample.dashboard.models.DashboardListItem
import io.github.karadkar.sample.databinding.ItemDashboardListBinding

class DashboardListAdapter(
    val context: Context,
    val onClick: (item: DashboardListItem) -> Unit
) : ListAdapter<DashboardListItem, DashboardListAdapter.VH>(
    DashboardListItemDiffUtil()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_dashboard_list, parent, false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))


    inner class VH(val binding: ItemDashboardListBinding) : RecyclerView.ViewHolder(binding.root), OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (adapterPosition == RecyclerView.NO_POSITION) return
            onClick.invoke(getItem(adapterPosition))
        }

        fun bind(item: DashboardListItem) {
            binding.apply {
                tvTitle.text = item.title
                executePendingBindings()
            }
        }
    }

    private class DashboardListItemDiffUtil : DiffUtil.ItemCallback<DashboardListItem>() {
        override fun areItemsTheSame(oldItem: DashboardListItem, newItem: DashboardListItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DashboardListItem, newItem: DashboardListItem): Boolean {
            return oldItem == newItem
        }
    }
}
