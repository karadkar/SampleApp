package io.github.karadkar.sample

import io.github.karadkar.sample.login.LoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinAppModules = module {

    viewModel {
        LoginViewModel()
    }
}