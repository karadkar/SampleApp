package io.github.karadkar.sample.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import io.github.karadkar.sample.R
import io.github.karadkar.sample.databinding.FragLoginBinding
import org.koin.android.viewmodel.ext.android.sharedViewModel

class LoginFragment : Fragment(), View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private lateinit var binding: FragLoginBinding
    private val viewMode: LoginViewModel by sharedViewModel()

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

        binding.apply {
            // preserves checked state. as on applying dark theme, fragments are recreated
            viewMode.isNightMode().observe(requireActivity(), Observer {
                switchTheme.isChecked = it
            })
            btnLogin.setOnClickListener(this@LoginFragment)
            switchTheme.setOnCheckedChangeListener(this@LoginFragment)
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_login) {
            viewMode.login()
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        viewMode.setNightMode(isChecked)
    }
}