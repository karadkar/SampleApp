package io.github.karadkar.sample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.github.karadkar.sample.databinding.ItemPackageInfoBinding

class PackageListAdapter(
    val context: Context,
    val items: List<PackageInfo>,
    val onClickItem: (item: PackageInfo) -> Unit
) :
    RecyclerView.Adapter<PackageListAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            DataBindingUtil.inflate<ItemPackageInfoBinding>(
                LayoutInflater.from(context),
                R.layout.item_package_info,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (position in 0 until itemCount) {
            holder.bind(items[position])
        }
    }

    inner class VH(
        val binding: ItemPackageInfoBinding
    ) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener,
        View.OnFocusChangeListener,
        View.OnLongClickListener {


        init {
            binding.root.apply {
                setOnClickListener(this@VH)
                onFocusChangeListener = this@VH
                setOnLongClickListener(this@VH)
            }
        }

        fun bind(item: PackageInfo) {
            binding.apply {
                tvName.text = item.label
                ivIcon.setImageDrawable(item.iconDrawable)
                executePendingBindings()
            }
        }

        override fun onClick(v: View?) {
            onClickItem(items[adapterPosition])
        }

        override fun onLongClick(v: View?): Boolean {
            return true
        }

        override fun onFocusChange(v: View?, hasFocus: Boolean) {
            val scale = if (hasFocus) 1.25f else 1.0f
            val elevation = if (hasFocus) 10.0f else 0.0f
            binding.root.animate()
                .setDuration(300)
                .scaleX(scale)
                .scaleY(scale)
                .withStartAction {
                    binding.root.elevation = elevation
                }
                .start()
        }

    }
}