package io.github.karadkar.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import io.github.karadkar.sample.databinding.ActivitySampleBinding
import io.github.karadkar.sample.login.LoginFragment
import io.github.karadkar.sample.login.LoginViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SampleActivity : AppCompatActivity() {
    lateinit var binding: ActivitySampleBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sample)

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