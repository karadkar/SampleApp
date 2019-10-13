package io.github.karadkar.sample.data

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.introspect.AnnotatedMember
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector
import io.realm.RealmObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory


object ApiHelper {


    private var retrofitBuilder: Retrofit.Builder = Retrofit.Builder().apply {
        val objectMapper = ObjectMapper().configureCommon()
        addConverterFactory(JacksonConverterFactory.create(objectMapper))
        addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }


    private val retrofitClient by lazy {
        retrofitBuilder.baseUrl("http://www.mocky.io/")
            .build()
    }

    fun getLocationApiService(): LocationApiService {
        return retrofitClient.create(LocationApiService::class.java)
    }

    private fun ObjectMapper.configureCommon(): ObjectMapper {
        // don't fail on unknown properties
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        // in serialization, only include non-null and non empty
        this.setSerializationInclusion(JsonInclude.Include.NON_EMPTY)

        // ignore RealmObject properties
        this.setAnnotationIntrospector(object : JacksonAnnotationIntrospector() {
            override fun hasIgnoreMarker(m: AnnotatedMember?): Boolean {
                return (m?.declaringClass == RealmObject::class.java || super.hasIgnoreMarker(m))
            }
        })
        return this
    }
}