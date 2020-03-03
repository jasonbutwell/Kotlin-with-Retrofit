package com.smartherd.globofly.services

import android.os.Build
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

object ServiceBuilder {

    // Note: We must use this so our Android emulator can see our local machine

    private const val URL = "http://10.0.2.2:9000/"

    // Create Logger

    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    // Create a custom interceptor to apply headers application wide

    val headerInterceptor = object: Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {

            val request = chain.request()

            request.newBuilder()
                .addHeader("x-device-type", Build.DEVICE)
                .addHeader("Accept-Language", Locale.getDefault().language)
                .build()

            val response = chain.proceed(request)
            return response
        }
    }

    // OkHttp client

    private val okHttp : OkHttpClient.Builder = OkHttpClient.Builder()
        .addInterceptor(headerInterceptor)
        .addInterceptor(logger)

    // Retrofit Builder

    private val builder : Retrofit.Builder = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    // Create retrofit Instance

    private val retrofit : Retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }
}