package com.richardmuttett.doordashlite.api

import com.richardmuttett.doordashlite.models.Restaurant
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface DoorDashLiteService {
    @GET("/v2/restaurant/")
    fun getRestaurants(@Query("lat") lat: Double, @Query("lng") lng: Double): Observable<List<Restaurant>>
}
