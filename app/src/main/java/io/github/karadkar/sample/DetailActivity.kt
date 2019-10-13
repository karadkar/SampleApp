package io.github.karadkar.sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import io.github.karadkar.sample.databinding.DetailActivityBinding

class DetailActivity : AppCompatActivity() {

    lateinit var binding: DetailActivityBinding
    lateinit var placeName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        placeName = intent.getStringExtra(KEY_PLACE_NAME) ?: error("place name not provided")

        binding = DataBindingUtil.setContentView(this, R.layout.detail_activity)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        val viewModel = ViewModelProviders.of(this)[LocationDetailViewModel::class.java]

        Picasso.get().load(viewModel.getLocationImage(placeName)).into(binding.ivLocationImage)
        binding.tvDate.text = viewModel.getLocationDate(placeName)
        binding.tvDescription.text = viewModel.getLocationDescription(placeName)
    }

    companion object {
        private val KEY_PLACE_NAME = "key.place.name"
        fun getIntent(context: Context, placeName: String): Intent {
            return Intent(context, DetailActivity::class.java).also {
                it.putExtra(KEY_PLACE_NAME, placeName)
            }
        }
    }
}
