package io.github.karadkar.sample.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import io.github.karadkar.sample.R
import io.github.karadkar.sample.databinding.FragLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.frag_login, container, false
        )
        return binding.root
    }
}