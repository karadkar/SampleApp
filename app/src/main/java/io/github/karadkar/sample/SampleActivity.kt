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
    }

    override fun onResume() {
        super.onResume()
        val listener = object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                createGrid()
                binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        }
        binding.root.viewTreeObserver.addOnGlobalLayoutListener(listener)
    }


    // grid should only be created when height/width are available at runtime
    lateinit var boxGrid: Array<Array<GridBox>>

    private fun createGrid() {
        val totalBoxesInRow = totalBoxesInRow(binding.gridContainer)
        val totalBoxesInColumn = totalBoxesInColumn(binding.gridContainer)
        boxGrid = createBoxGrid(totalBoxesInRow, totalBoxesInColumn)
        boxAdapter = BoxAdapter(this, this::onClickBoxItem)

        val layoutManager = GridLayoutManager(
            this@SampleActivity,
            totalBoxesInRow, GridLayoutManager.VERTICAL, false
        )
        binding.apply {
            rvGrid.adapter = boxAdapter
            rvGrid.layoutManager = layoutManager
        }
        boxAdapter.submitList(getBoxGridList())
    }

    private fun onClickBoxItem(x: Int, y: Int) {
        val box = boxGrid[x][y]
        boxGrid[x][y] = box.copy(visited = !box.visited)
    }

    fun totalBoxesInRow(parent: View): Int {
        return parent.width / R.dimen.box_width.getDimension(this)
    }

    fun totalBoxesInColumn(parent: View): Int {
        return (parent.height / R.dimen.box_height.getDimension(this))
    }

    fun getBoxGridList(): List<GridBox> {
        return mutableListOf<GridBox>().apply {
            boxGrid.forEach { gridRow ->
                addAll(gridRow.toList())
            }
        }
    }

    fun createBoxGrid(rowCount: Int, columnCount: Int): Array<Array<GridBox>> {
        val boxGrid: Array<Array<GridBox>> = Array(rowCount) { row ->
            Array(columnCount) { col ->
                GridBox(x = row, y = col)
            }
        }
        return boxGrid
    }
}