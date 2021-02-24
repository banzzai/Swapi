package com.example.swapi.utils.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SwapiRetrofitBuilder {

    private const val BASE_URL = "https://swapi.dev/api/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            // Attempt to serialize nulls, but this didn't seem to be enough
            //.addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
            .build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}