package io.github.karadkar.sample

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import io.github.karadkar.sample.databinding.ActivitySampleBinding

class SampleActivity : AppCompatActivity() {
    lateinit var binding: ActivitySampleBinding
    lateinit var boxAdapter: BoxAdapter
    lateinit var vm: PathfinderViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sample)
        vm = ViewModelProviders.of(this)[PathfinderViewModel::class.java]
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

    private fun createGrid() {
        val totalBoxesInRow = totalBoxesInRow(binding.rvGrid)
        val totalBoxesInColumn = totalBoxesInColumn(binding.rvGrid)

        vm.createBoxGrid(totalBoxesInRow, totalBoxesInColumn)

        boxAdapter = BoxAdapter(
            context = this,
            rows = totalBoxesInRow,
            columns = totalBoxesInColumn,
            onClickBox = this::onClickBoxItem
        )

        val layoutManager = GridLayoutManager(
            this@SampleActivity,
            totalBoxesInRow, GridLayoutManager.VERTICAL, false
        )
        binding.apply {
            rvGrid.adapter = boxAdapter
            rvGrid.layoutManager = layoutManager
        }
        boxAdapter.submitList(vm.getBoxGridList())

        vm.find().observe(this, Observer { box ->
            boxAdapter.onUpdateBox(box)
        })
    }

    private fun onClickBoxItem(row: Int, col: Int) {
        vm.onClickBoxItem(row, col)
        boxAdapter.submitList(vm.getBoxGridList())
    }

    private fun totalBoxesInRow(parent: View): Int {
        return parent.width / R.dimen.box_width.getDimension(this)
    }

    private fun totalBoxesInColumn(parent: View): Int {
        return (parent.height / R.dimen.box_height.getDimension(this))
    }
}