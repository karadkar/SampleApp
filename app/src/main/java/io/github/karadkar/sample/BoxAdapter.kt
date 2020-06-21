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
    val rows: Int,
    val columns: Int,
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

    fun onUpdateBox(box: GridBox) {
        val index = ((box.row * columns) + box.col)
        getItem(index)?.visited = box.visited
        logError("in:$index, col:$columns, $box")
        notifyItemChanged(index)
    }

    inner class VH(private val binding: ItemBoxBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
        }

        fun onBind(box: GridBox) {
            binding.apply {
                if (box.visited) {
                    surfaceView.transformToCircle()
                }
                executePendingBindings()
            }
        }

        override fun onClick(v: View?) {
            val box = getItem(adapterPosition)
//            binding.surfaceView.transformToCircle()
            onClickBox.invoke(box.row, box.col)
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