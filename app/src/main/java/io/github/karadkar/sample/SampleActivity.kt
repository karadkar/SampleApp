package io.github.karadkar.sample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.github.karadkar.sample.bottom_sheets.FirstBottomSheet
import io.github.karadkar.sample.databinding.ActivitySampleBinding

class SampleActivity : AppCompatActivity() {
    lateinit var binding: ActivitySampleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sample)
        binding.btnClickMe.setOnClickListener(this::onClickFragOne)
    }

    private fun onClickFragOne(v: View) {
        FirstBottomSheet().show(supportFragmentManager, "frag-one")
    }
}