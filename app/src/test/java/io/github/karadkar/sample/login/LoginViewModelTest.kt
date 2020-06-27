package io.github.karadkar.sample.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import io.github.karadkar.sample.login.models.LoginEvent
import io.github.karadkar.sample.login.repository.LoginRepository
import io.github.karadkar.sample.utils.getOrAwaitValue
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {

    lateinit var vm: LoginViewModel
    lateinit var mockRepo: LoginRepository

    // executes arch-components synchronously
    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @Before
    fun beforeTest() {
        mockRepo = mockk()
        vm = LoginViewModel(mockRepo)
        assertThat(vm).isNotNull()
    }

    @Test
    fun `basic view state on screen-load`() {
        assertThat(vm).isNotNull()
        vm.submitEvent(LoginEvent.ScreenLoadEvent)

        vm.viewState.getOrAwaitValue().apply {
            assertThat(loginApiError).isNull()
            assertThat(passwordError).isNull()
            assertThat(userNameError).isNull()
            assertThat(enableDarkTheme).isFalse()
            assertThat(loading).isFalse()
        }
    }
}