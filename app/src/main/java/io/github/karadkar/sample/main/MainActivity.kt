package io.github.karadkar.sample.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.github.karadkar.sample.DetailActivity
import io.github.karadkar.sample.R
import io.github.karadkar.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val adapter = LocationListAdapter(this) { clickedListItem ->
            startActivity(Intent(this, DetailActivity::class.java))
        }
        binding.rvLocations.adapter = adapter
        viewModel.getLocations().observe(this, Observer { locations ->
            if (locations != null) {
                adapter.submitList(locations)
            }
        })

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
