package io.github.karadkar.sample

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PathfinderViewModel(app: Application) : AndroidViewModel(app) {
    lateinit var boxGrid: Array<Array<GridBox>>
    private val disposable = CompositeDisposable()

    fun getBoxGridList(): List<GridBox> {
        return mutableListOf<GridBox>().apply {
            boxGrid.forEach { gridRow ->
                addAll(gridRow)
            }
        }
    }

    fun createBoxGrid(rowCount: Int, columnCount: Int) {
        boxGrid = Array(rowCount) { row ->
            Array(columnCount) { col ->
                GridBox(row = row, col = col)
            }
        }
    }

    fun onClickBoxItem(x: Int, y: Int) {
        val box = boxGrid[x][y]
        boxGrid[x][y] = box.copy(visited = !box.visited)
    }

    fun find(): LiveData<GridBox> {
        val pathFinder = BFSPathfinder()
        val start = boxGrid[boxGrid.size / 2][boxGrid[0].size / 2]
        val target = boxGrid.last().last()

        val sub = pathFinder.find(start, target, boxGrid)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { logError("emiting $it") }
            .doOnError {
                logError("error emitting box", it)
            }.doOnComplete {
                logError("completed emitting")
            }
        return LiveDataReactiveStreams.fromPublisher(sub)
    }

    override fun onCleared() {
        disposable.dispose()
    }
}