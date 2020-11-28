package com.enginebai.project.di

import com.enginebai.base.extensions.EnumGsonSerializedNameConverterFactory
import com.enginebai.base.extensions.EnumHasValueConverterFactory
import com.enginebai.project.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }
    }
    single {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(get<HttpLoggingInterceptor>())
        builder.protocols(listOf(Protocol.HTTP_1_1, Protocol.HTTP_2))
        builder.build()
    }

    single { GsonConverterFactory.create() }
    single { EnumGsonSerializedNameConverterFactory }
    single { EnumHasValueConverterFactory }
    single { RxJava2CallAdapterFactory.create() }
    single {
        // TODO: 5. Set your API base url
        Retrofit.Builder()
            .baseUrl("")
            .addCallAdapterFactory(get<RxJava2CallAdapterFactory>())
            .addConverterFactory(get<GsonConverterFactory>())
            .addConverterFactory(get<EnumHasValueConverterFactory>())
            .client(get())
            .build()
    }
}