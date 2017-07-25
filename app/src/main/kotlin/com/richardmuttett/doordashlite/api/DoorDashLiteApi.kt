package com.richardmuttett.doordashlite.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DoorDashLiteApi {

    companion object {
        private val BASE_URL = "https://api.doordash.com"
    }

    private val service: DoorDashLiteService

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        service = retrofit.create<DoorDashLiteService>(DoorDashLiteService::class.java)
    }

    fun getRestaurants(lat: Double, lng: Double) = service.getRestaurants(lat, lng)
}
