package io.github.karadkar.sample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.github.karadkar.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val adapter = LocationListAdapter(this) { clickedListItem ->
            Toast.makeText(this, "clicked ${clickedListItem.title}", Toast.LENGTH_SHORT).show()
        }
        binding.rvLocations.adapter = adapter
        adapter.submitList(dummyListItems())
    }

    fun dummyListItems(): MutableList<LocationListItem> {
        val list = mutableListOf<LocationListItem>()
        for (i in 0..10) {
            list.add(
                LocationListItem(
                    title = "Location name $i",
                    date = "June 19 - 20, 2019",
                    isFavourite = i % 2 == 0,
                    image = "https://picsum.photos/300"
                )
            )
        }
        return list
    }
}
