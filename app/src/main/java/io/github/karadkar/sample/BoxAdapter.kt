package io.github.karadkar.sample

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.karadkar.sample.databinding.ItemBoxBinding

class BoxAdapter(
    val context: Context,
    val onClickBox: (x: Int, y: Int) -> Unit
) : ListAdapter<GridBox, BoxAdapter.VH>(GridListDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_box, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class VH(private val binding: ItemBoxBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
        }

        fun onBind(box: GridBox) {
            binding.apply {
                executePendingBindings()
            }
        }

        override fun onClick(v: View?) {
            val box = getItem(adapterPosition)
            binding.surfaceView.transformToCircle()
            onClickBox.invoke(box.x, box.y)
        }
    }

    class GridListDiff() : DiffUtil.ItemCallback<GridBox>() {
        override fun areItemsTheSame(oldBox: GridBox, newBox: GridBox): Boolean {
            return oldBox == newBox
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldBox: GridBox, newBox: GridBox): Boolean {
            return oldBox == newBox
        }
    }
}