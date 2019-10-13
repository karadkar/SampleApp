package io.github.karadkar.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.squareup.picasso.Picasso
import io.github.karadkar.sample.databinding.DetailActivityBinding
import io.github.karadkar.sample.main.LocationListItem

class DetailActivity : AppCompatActivity() {

    lateinit var binding: DetailActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.detail_activity)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        Picasso.get().load("https://picsum.photos/500").into(binding.ivLocationImage)
        binding.tvDate.text = "June 19 - 29, 2019"
        binding.tvDescription.text =
            "A common collapsing toolbar pattern is the parallax image scroll, in which images or other children of the CollapsingToolbarLayout scroll at different rates than their siblings. To achieve this effect, add an ImageView and any other views as children of the CollapsingToolbarLayout. Specify parallax multipliers in the XML for as many of the siblings as you like." +
                    "A common collapsing toolbar pattern is the parallax image scroll, in which images or other children of the CollapsingToolbarLayout scroll at different rates than their siblings. To achieve this effect, add an ImageView and any other views as children of the CollapsingToolbarLayout. Specify parallax multipliers in the XML for as many of the siblings as you like." +
                    "A common collapsing toolbar pattern is the parallax image scroll, in which images or other children of the CollapsingToolbarLayout scroll at different rates than their siblings. To achieve this effect, add an ImageView and any other views as children of the CollapsingToolbarLayout. Specify parallax multipliers in the XML for as many of the siblings as you like." +
                    "A common collapsing toolbar pattern is the parallax image scroll, in which images or other children of the CollapsingToolbarLayout scroll at different rates than their siblings. To achieve this effect, add an ImageView and any other views as children of the CollapsingToolbarLayout. Specify parallax multipliers in the XML for as many of the siblings as you like." +
                    "A common collapsing toolbar pattern is the parallax image scroll, in which images or other children of the CollapsingToolbarLayout scroll at different rates than their siblings. To achieve this effect, add an ImageView and any other views as children of the CollapsingToolbarLayout. Specify parallax multipliers in the XML for as many of the siblings as you like."

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
