package io.github.karadkar.sample

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.github.karadkar.sample.databinding.ItemBoxBinding
import kotlin.math.floor

class BoxAdapter(
    val context: Context,
    var grid: Array<Array<GridBox>>,
    val onClickBox: (x: Int, y: Int) -> Unit
) : RecyclerView.Adapter<BoxAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_box, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(getItem(position))
    }

    fun onUpdateBox(box: GridBox) {
        val index = getGridIndex(box)
        grid[box.row][box.col].visited = box.visited
        logError("in:$index, col:${columnCount}, $box")
        notifyItemChanged(index)
    }


    fun getItem(index: Int): GridBox {
        val row = floor(index / columnCount.toDouble()).toInt()
        val columns = (index % columnCount)
        return grid[row][columns]
    }

    override fun getItemCount(): Int {
        return rowCount * columnCount
    }

    fun getGridIndex(box: GridBox): Int {
        return ((box.row * columnCount) + box.col)
    }

    fun submitList(grid: Array<Array<GridBox>>) {
        this.grid = grid
        notifyDataSetChanged()
    }

    val rowCount = grid.size
    val columnCount = if (grid.isEmpty()) 0 else grid[0].size

    inner class VH(private val binding: ItemBoxBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
        }

        fun onBind(box: GridBox) {
            binding.apply {
                if (box.visited) {
                    surfaceView.transformToCircle()
                }
                tvOverlay.text = "${box.row},${box.col}"
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