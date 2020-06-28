package io.github.karadkar.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.github.karadkar.sample.dashboard.DashBoardFragment
import io.github.karadkar.sample.databinding.ActivitySampleBinding
import io.github.karadkar.sample.login.LoginContract
import io.github.karadkar.sample.login.LoginFragment
import io.github.karadkar.sample.login.LoginViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SampleActivity : AppCompatActivity(), LoginContract {
    lateinit var binding: ActivitySampleBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sample)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, LoginFragment())
            .commit()
    }

    override fun onLoginSuccessful() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, DashBoardFragment())
            .commit()
    }
}