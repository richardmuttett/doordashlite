package com.richardmuttett.doordashlite.restaurantlist

import com.richardmuttett.doordashlite.models.Restaurant

interface IRestaurantsPresenter {
    var restaurantList: List<Restaurant>
    fun fetchRestaurants(lat: Double, lng: Double)
    fun updateFavorites(restaurant: Restaurant)
    fun destroy()
}
