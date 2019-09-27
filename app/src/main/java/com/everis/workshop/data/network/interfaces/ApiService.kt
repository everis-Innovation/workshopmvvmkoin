package com.everis.workshop.data.network.interfaces

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface ApiService {

    companion object Factory {
        private val URL_BASE = "https://randomuser.me/"

        fun create(): ApiInterface {
            val retrofit = Retrofit.Builder().apply {
                client(makeOkHttpClient())
                baseUrl(URL_BASE)
                addCallAdapterFactory(CoroutineCallAdapterFactory())
                addConverterFactory(GsonConverterFactory.create())
            }
                .build()

            return retrofit.create(ApiInterface::class.java)
        }


        private fun makeOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(makeLoggingInterceptor()).apply {
                    connectTimeout(120, TimeUnit.SECONDS)
                    readTimeout(120, TimeUnit.SECONDS)
                    writeTimeout(90, TimeUnit.SECONDS)
                }.build()
        }

        private fun makeLoggingInterceptor(): HttpLoggingInterceptor {
            val logging = HttpLoggingInterceptor()
            logging.level =
                HttpLoggingInterceptor.Level.BODY
            return logging
        }
    }
}

