package io.github.karadkar.sample

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import io.github.karadkar.sample.databinding.ActivitySampleBinding

class SampleActivity : AppCompatActivity() {
    lateinit var binding: ActivitySampleBinding
    lateinit var boxAdapter: BoxAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sample)
        val listener = object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                createGrid()
                binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        }
        binding.root.viewTreeObserver.addOnGlobalLayoutListener(listener)
    }


    // grid should only be created when height/width are available at runtime
    private fun createGrid() {
        val totalBoxesInRow = totalBoxesInRow(binding.gridContainer)
        val totalBoxesInColumn = totalBoxesInColumn(binding.gridContainer)
        boxAdapter = BoxAdapter(this)

        val layoutManager = GridLayoutManager(
            this@SampleActivity,
            totalBoxesInRow, GridLayoutManager.VERTICAL, false
        )
        binding.apply {
            rvGrid.adapter = boxAdapter
            rvGrid.layoutManager = layoutManager
        }
        boxAdapter.submitList(getGridBoxes(totalBoxesInRow, totalBoxesInColumn))
    }

    fun totalBoxesInRow(parent: View): Int {
        return parent.height / R.dimen.box_width.getDimension(this)
    }

    fun totalBoxesInColumn(parent: View): Int {
        return (parent.height / R.dimen.box_height.getDimension(this))
    }

    fun getGridBoxes(rowCount: Int, columnCount: Int): List<GridBox> {
        return mutableListOf<GridBox>().apply {
            for (row in 0 until rowCount) {
                for (col in 0 until columnCount) {
                    add(GridBox(x = row, y = col))
                }
            }
        }
    }
}