package io.github.karadkar.sample

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.karadkar.sample.login.LoginViewModel
import io.github.karadkar.sample.login.repository.LoginApiService
import io.github.karadkar.sample.login.repository.LoginRepository
import io.github.karadkar.sample.utils.SampleConstants
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

val koinAppModules = module {

    single<ObjectMapper> {
        return@single ObjectMapper().also {
            // don't fail on unknown properties
            it.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

            // in serialization, only include non-null and non empty
            it.setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
        }
    }

    single<Retrofit> {
        val builder = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create(get()))

        return@single builder.baseUrl(SampleConstants.LOGIN_BASE_URL).build()
    }

    single<LoginApiService> {
        val retrofit = get<Retrofit>()
        return@single retrofit.create(LoginApiService::class.java)
    }

    single<LoginRepository> {
        return@single LoginRepository(apiService = get(), objectMapper = get())
    }

    viewModel {
        LoginViewModel(repo = get())
    }
}