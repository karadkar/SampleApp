package io.github.karadkar.sample

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class PathfinderViewModel(app: Application) : AndroidViewModel(app) {
    lateinit var boxGrid: Array<Array<GridBox>>


    fun getBoxGridList(): List<GridBox> {
        return mutableListOf<GridBox>().apply {
            boxGrid.forEach { gridRow ->
                addAll(gridRow.toList())
            }
        }
    }

    fun createBoxGrid(rowCount: Int, columnCount: Int) {
        boxGrid = Array(rowCount) { row ->
            Array(columnCount) { col ->
                GridBox(x = row, y = col)
            }
        }
    }

    fun onClickBoxItem(x: Int, y: Int) {
        val box = boxGrid[x][y]
        boxGrid[x][y] = box.copy(visited = !box.visited)
    }
}