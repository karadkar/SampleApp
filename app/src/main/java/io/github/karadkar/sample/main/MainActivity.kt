package io.github.karadkar.sample.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.github.karadkar.sample.DetailActivity
import io.github.karadkar.sample.R
import io.github.karadkar.sample.databinding.ActivityMainBinding
import io.github.karadkar.sample.db.LCE

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val viewModel by lazy {
        ViewModelProviders.of(this)[MainViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val adapter = LocationListAdapter(
            this,
            onClickItem = this::onClickLocation,
            onClickFavourite = this::onClickFavourite
        )
        binding.rvLocations.adapter = adapter
        viewModel.getLocations().observe(this, Observer { locations ->
            if (locations != null) {
                adapter.submitList(locations)
            }
        })

        viewModel.apiResultLiveData.observe(this, Observer { result ->
            when (result) {
                is LCE.Error -> {
                    Toast.makeText(this, "Opps something went wrong", Toast.LENGTH_SHORT).show()
                }
                is LCE.Content -> {
                    setupName()
                }
            }

            binding.swipeRefreshLayout.isRefreshing = result.isLoading()
            binding.container.visibleOrGone(!result.isLoading())
        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchLocations()
        }
    }

    private fun onClickLocation(item: LocationListItem) {
        startActivity(DetailActivity.getIntent(this, placeName = item.title))
    }

    private fun onClickFavourite(item: LocationListItem) {
        viewModel.markAsFavourite(item.title)
    }

    private fun setupName() {
        binding.tvUserName.text = String.format(getString(R.string.format_custome_name), viewModel.getCustomerName())
    }

    private fun View.visibleOrGone(visible: Boolean) {
        this.visibility = if (visible) View.VISIBLE else View.GONE
    }
}
