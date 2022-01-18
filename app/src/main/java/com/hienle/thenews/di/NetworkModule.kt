package com.hienle.thenews.di

import com.google.gson.Gson
import com.hienle.thenews.BuildConfig
import com.hienle.thenews.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun providesOkhttpBuilder(): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(30, TimeUnit.SECONDS)
        builder.readTimeout(30, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val logger = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    println(message)
                }
            })
            logger.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logger)
        }

        return builder
    }

    @Provides
    fun providesCommonInterceptor(): Interceptor {
        return Interceptor { chain ->
            val reqBuilder = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "7fbe9415fb614d65b75621da46646c6e")
            chain.proceed(reqBuilder.build())
        }
    }

    @Provides
    fun providesBaseRetrofitBuilder(
        gson: Gson
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Provides
    fun providesGson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideGithubService(
        httpClientBuilder: OkHttpClient.Builder,
        retrofitBuilder: Retrofit.Builder,
        commonInterceptor: Interceptor
    ): Retrofit {
        httpClientBuilder.addInterceptor(commonInterceptor)
        return retrofitBuilder
            .baseUrl(BuildConfig.SERVER_URL)
            .client(httpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun mainRetrofitService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}