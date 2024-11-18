package com.mediserve.pharma.mediservepharma

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {

    val api: MediServeApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://mediserve-api.vercel.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MediServeApi::class.java)
    }
}