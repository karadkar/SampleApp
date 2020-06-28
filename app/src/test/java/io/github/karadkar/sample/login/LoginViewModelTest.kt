package io.github.karadkar.sample.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import io.github.karadkar.sample.R
import io.github.karadkar.sample.login.models.LoginEvent
import io.github.karadkar.sample.login.repository.LoginRepository
import io.github.karadkar.sample.login.repository.LoginResponse
import io.github.karadkar.sample.utils.TestRxSchedulersProvider
import io.github.karadkar.sample.utils.getOrAwaitValue
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeoutException

class LoginViewModelTest {

    lateinit var vm: LoginViewModel
    lateinit var mockRepo: LoginRepository

    // executes arch-components synchronously
    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val validEmail = "rohit@email.com"
    private val validPassword = "Rohit@2020"

    // replace all schedulers with trampoline (FIFO)
    private val testSchedulers = TestRxSchedulersProvider()

    @Before
    fun beforeTest() {
        mockRepo = mockk()
        every { mockRepo.isValidEmailId(validEmail) } returns true

        vm = LoginViewModel(repo = mockRepo, schedulers = testSchedulers, debounceTimeMillis = 1L)
        vm.submitEvent(LoginEvent.ScreenLoadEvent)
    }

    @After
    fun afterTest() {
        unmockkAll()
    }


    @Test
    fun `basic view state on screen-load`() {
        assertThat(vm).isNotNull()

        vm.viewState.getOrAwaitValue().apply {
            assertThat(loginApiError).isNull()
            assertThat(passwordError).isNull()
            assertThat(userNameError).isNull()
            assertThat(enableDarkTheme).isFalse()
            assertThat(loading).isFalse()
        }
    }

    @Test
    fun `email validation`() {
        val email = "rohit.karadkar@gmail.com"
        every { mockRepo.isValidEmailId(email) } returns false

        vm.submitEvent(LoginEvent.UserNameValidationCheckEvent(username = email))
        vm.viewState.getOrAwaitValue().apply {
            assertThat(userNameError).isEqualTo(R.string.error_invalid_email)
            assertThat(isUserNameValid).isFalse()
        }

        every { mockRepo.isValidEmailId(email) } returns true
        vm.submitEvent(LoginEvent.UserNameValidationCheckEvent(username = email))
        vm.viewState.getOrAwaitValue().apply {
            assertThat(userNameError).isNull()
            assertThat(isUserNameValid).isTrue()
        }
    }

    @Test
    fun `password length needs to be between 8-16 char`() {
        vm.submitEvent(LoginEvent.PasswordValidationCheckEvent(password = "rohit"))
        vm.viewState.getOrAwaitValue().apply {
            assertThat(passwordError).isEqualTo(R.string.error_password_length)
            assertThat(isPasswordValid).isFalse()
        }

        vm.submitEvent(LoginEvent.PasswordValidationCheckEvent(password = "rohit-karadkar-1239"))
        vm.viewState.getOrAwaitValue().apply {
            assertThat(passwordError).isEqualTo(R.string.error_password_length)
            assertThat(isPasswordValid).isFalse()
        }


    }

    @Test
    fun `password needs 1 uppercase`() {
        vm.submitEvent(LoginEvent.PasswordValidationCheckEvent(password = "rohit@2020"))
        vm.viewState.getOrAwaitValue().apply {
            assertThat(passwordError).isEqualTo(R.string.error_password_need_uppercase)
            assertThat(isPasswordValid).isFalse()
        }
    }

    @Test
    fun `password needs 1 lowercase`() {
        vm.submitEvent(LoginEvent.PasswordValidationCheckEvent(password = "ROHIT@2020"))
        vm.viewState.getOrAwaitValue().apply {
            assertThat(passwordError).isEqualTo(R.string.error_password_need_lowercase)
            assertThat(isPasswordValid).isFalse()
        }
    }

    @Test
    fun `password needs 1 digit`() {
        vm.submitEvent(LoginEvent.PasswordValidationCheckEvent(password = "Rohit@twent"))
        vm.viewState.getOrAwaitValue().apply {
            assertThat(passwordError).isEqualTo(R.string.error_password_need_digit)
            assertThat(isPasswordValid).isFalse()
        }
    }

    @Test
    fun `password needs 1 special char`() {
        vm.submitEvent(LoginEvent.PasswordValidationCheckEvent(password = "Rohit2020"))
        vm.viewState.getOrAwaitValue().apply {
            assertThat(passwordError).isEqualTo(R.string.error_password_need_special_char)
            assertThat(isPasswordValid).isFalse()
        }
    }


    @Test
    fun `valid email and password`() {
        val apiResponse = LoginResponse.Success()
        apiResponse.token = "random-token"

        every { mockRepo.login(validEmail, validPassword) } returns Single.just(apiResponse)
        vm.submitEvent(LoginEvent.OnClickLoginEvent(username = validEmail, password = validPassword))
        vm.viewState.getOrAwaitValue().apply {
            assertThat(userNameError).isNull()
            assertThat(passwordError).isNull()
            assertThat(enableLoginButton()).isTrue()
            assertThat(loading).isFalse()
        }

        // todo: add test for visible progress-bar state
    }

    @Test
    fun `login api error`() {
        every { mockRepo.login(validEmail, validPassword) } returns Single.error(TimeoutException())
        vm.submitEvent(LoginEvent.OnClickLoginEvent(username = validEmail, password = validPassword))
        vm.viewState.getOrAwaitValue().apply {
            assertThat(userNameError).isNull()
            assertThat(passwordError).isNull()
            assertThat(loginApiError).isNotEmpty()
            assertThat(enableLoginButton()).isTrue() // user should be able to re-try
            assertThat(loading).isFalse()
        }
    }
}