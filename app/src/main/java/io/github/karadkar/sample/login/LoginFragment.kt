package io.github.karadkar.sample.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import io.github.karadkar.sample.R
import io.github.karadkar.sample.databinding.FragLoginBinding
import io.github.karadkar.sample.login.models.LoginEvent
import io.github.karadkar.sample.login.models.LoginUiEffects
import io.github.karadkar.sample.utils.visibleOrGone
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

        renderViewState()
        triggerViewEffects()

        binding.apply {
            btnLogin.setOnClickListener(this@LoginFragment)
            switchTheme.setOnCheckedChangeListener(this@LoginFragment)

            etUserName.addTextChangedListener(afterTextChanged = {
                viewMode.submitEvent(
                    LoginEvent.UserNameValidationCheckEvent(username = it.toString())
                )
            })

            etPassword.addTextChangedListener(afterTextChanged = {
                viewMode.submitEvent(
                    LoginEvent.PasswordValidationCheckEvent(password = it.toString())
                )
            })
        }
        viewMode.submitEvent(LoginEvent.ScreenLoadEvent)
    }
    private fun renderViewState() {
        viewMode.viewState.observe(requireActivity(), Observer { state ->
            if (state != null) {
                binding.apply {
                    btnLogin.isEnabled = state.enableLoginButton() && !state.loading
                    tiUserName.isEnabled = !state.loading
                    tiPassword.isEnabled = !state.loading
                    switchTheme.isEnabled = !state.loading

                    progressBar.visibleOrGone(state.loading)

                    switchTheme.isChecked = state.enableDarkTheme
                    enableDarkMode(state.enableDarkTheme) // for the fist load

                    if (state.userNameError != null) {
                        tiUserName.error = getString(state.userNameError)
                    } else {
                        tiUserName.error = null
                    }

                    if (state.passwordError != null) {
                        tiPassword.error = getString(state.passwordError)
                        tiPassword.errorIconDrawable = null
                    } else {
                        tiPassword.error = null
                    }
                }
            }
        })
    }

    private fun triggerViewEffects() {
        viewMode.viewEffect.observe(requireActivity(), Observer { effect ->
            if (effect != null) {
                when (effect) {
                    is LoginUiEffects.LoginError -> {
                        Toast.makeText(requireContext(), effect.message, Toast.LENGTH_SHORT).show()
                    }
                    is LoginUiEffects.LoginSuccess -> {
                        Toast.makeText(requireContext(), "Login success", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun enableDarkMode(enable: Boolean) {
        val modeValue = if (enable) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }

        val act = activity
        if (act is AppCompatActivity?) {
            act?.delegate?.let { delegate ->
                if (delegate.localNightMode != modeValue) {
                    delegate.localNightMode = modeValue
                }
            }
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_login) {
            viewMode.submitEvent(
                LoginEvent.OnClickLoginEvent(
                    username = binding.etUserName.text.toString(),
                    password = binding.etPassword.text.toString()
                )
            )
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        viewMode.submitEvent(LoginEvent.EnableDarkThemeEvent(isChecked))
    }
}