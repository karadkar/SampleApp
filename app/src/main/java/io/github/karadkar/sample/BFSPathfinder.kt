package io.github.karadkar.sample

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import java.util.*

class BFSPathfinder() {


    fun find(start: GridBox, goal: GridBox, grid: Array<Array<GridBox>>): Flowable<GridBox> {
        return Flowable.create({ emitter: FlowableEmitter<GridBox> ->
            val queue: Queue<GridBox> = LinkedList<GridBox>()

            queue.add(start)
            start.visited = true

            while (queue.isNotEmpty()) {
                val node = queue.poll()!!
                val (row: Int, col: Int) = (node.row to node.col)

                // emmit as visited
                emitter.onNext(node)

                if (node == goal) {
                    emitter.onComplete()
                }

                val nexItems = listOf((row + 1 to col), (row to col + 1), (row - 1 to col), (row to col - 1))
                for ((r, c) in nexItems) {
                    if (r >= 0 && c >= 0 && r < grid.size && c < grid.first().size && !grid[r][c].visited) {
                        grid[r][c].visited = true
                        queue.offer(grid[r][c])
                    }
                }
                Thread.sleep(100L)
            }

            // finished processing queue

        }, BackpressureStrategy.BUFFER)
    }
}