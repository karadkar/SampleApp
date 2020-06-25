package io.github.karadkar.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.github.karadkar.sample.databinding.ActivitySampleBinding
import io.github.karadkar.sample.login.LoginFragment
import io.github.karadkar.sample.login.LoginViewModel

class SampleActivity : AppCompatActivity() {
    lateinit var binding: ActivitySampleBinding
    lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sample)

        viewModel = ViewModelProviders.of(this)[LoginViewModel::class.java]

        viewModel.nightModeValue().observe(this, Observer { value ->
            if (value != null) {
                delegate.localNightMode = value
            }
        })

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, LoginFragment())
            .commit()
    }
}