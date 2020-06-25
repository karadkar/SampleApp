package io.github.karadkar.sample.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.github.karadkar.sample.R
import io.github.karadkar.sample.databinding.FragLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragLoginBinding
    private lateinit var viewMode: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.frag_login, container, false
        )
        return binding.root
    }

    // fixme: retain edit-text values when dark theme is applied
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewMode = ViewModelProviders.of(requireActivity())[LoginViewModel::class.java]

        binding.apply {
            switchTheme.setOnCheckedChangeListener { _, isChecked ->
                viewMode.setNightMode(isChecked)
            }
            // preserves checked state. as on applying dark theme, fragments are recreated
            viewMode.isNightMode().observe(requireActivity(), Observer {
                switchTheme.isChecked = it
            })
        }
    }
}