package io.github.karadkar.sample.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.github.karadkar.sample.R
import io.github.karadkar.sample.databinding.LocationListItemBinding

class LocationListAdapter(
    val context: Context,
    val onClickItem: (item: LocationListItem) -> Unit,
    val onClickFavourite: (item: LocationListItem) -> Unit
) :
    ListAdapter<LocationListItem, LocationListAdapter.VH>(
        LocationListDiff()
    ) {
    private val icFavourite by lazy {
        ContextCompat.getDrawable(context, R.drawable.ic_favorite)
    }
    private val icFavouriteBorder by lazy {
        ContextCompat.getDrawable(context, R.drawable.ic_favorite_border)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = DataBindingUtil.inflate<LocationListItemBinding>(
            LayoutInflater.from(context),
            R.layout.location_list_item,
            parent,
            false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (position in 0 until itemCount) {
            holder.bind(getItem(position))
        }
    }

    inner class VH(private val binding: LocationListItemBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
            binding.btnFavourite.setOnClickListener(this)
        }

        fun bind(item: LocationListItem) {
            binding.apply {
                tvLocationName.text = item.title
                btnLocationDate.text = item.date
                btnFavourite.background = (if (item.isFavourite) icFavourite else icFavouriteBorder)
            }
            if (item.image.isNotEmpty()) {
                Picasso.get()
                    .load(item.image)
                    .into(binding.ivLocationImage)
            }
            binding.executePendingBindings()
        }

        override fun onClick(view: View?) {
            if (adapterPosition in 0 until itemCount) {
                if (view?.id == R.id.btn_favourite) {
                    onClickFavourite(getItem(adapterPosition))
                } else {
                    onClickItem(getItem(adapterPosition))
                }
            }
        }
    }

    class LocationListDiff() : DiffUtil.ItemCallback<LocationListItem>() {
        override fun areItemsTheSame(oldItem: LocationListItem, newItem: LocationListItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: LocationListItem, newItem: LocationListItem): Boolean {
            return oldItem == newItem
        }
    }
}