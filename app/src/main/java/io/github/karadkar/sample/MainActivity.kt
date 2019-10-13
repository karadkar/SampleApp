package io.github.karadkar.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.github.karadkar.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val adapter = LocationListAdapter(this) { clickedListItem ->
            startActivity(Intent(this, DetailActivity::class.java))
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
