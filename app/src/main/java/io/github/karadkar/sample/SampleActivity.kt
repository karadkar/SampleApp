package io.github.karadkar.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.github.karadkar.sample.databinding.ActivitySampleBinding

class SampleActivity : AppCompatActivity() {
    lateinit var binding: ActivitySampleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sample)
    }
}