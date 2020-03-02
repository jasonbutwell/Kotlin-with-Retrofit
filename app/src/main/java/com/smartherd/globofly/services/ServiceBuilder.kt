package com.smartherd.globofly.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    // Note: We must use this so our Android emulator can see our local machine

    private const val URL = "http://10.0.2.2:9000/"

    // Create Logger

    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    // OkHttp client

    private val okHttp : OkHttpClient.Builder = OkHttpClient.Builder().addInterceptor(logger)

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