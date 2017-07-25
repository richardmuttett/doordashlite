package com.richardmuttett.doordashlite.restaurantlist

import com.google.gson.Gson
import com.richardmuttett.doordashlite.api.DoorDashLiteApi
import com.richardmuttett.doordashlite.models.Restaurant
import rx.Observable

class RestaurantsInteractor(
        private val favoritesRepository: FavoritesRepository,
        private var favorites: MutableList<Int> = mutableListOf()) {

    private val api = DoorDashLiteApi()

    fun getRestaurants(lat: Double, lng: Double): Observable<List<Restaurant>> {
        return Observable.zip(
                Observable.fromCallable { favorites = loadFavorites() },
                api.getRestaurants(lat, lng),
                { _, restaurantList -> findFavorites(restaurantList) })
                .map(this::sortList)
    }

    fun loadFavorites(): MutableList<Int> {
        val json = favoritesRepository.getFavorites()

        return when {
            json.isNotEmpty() -> Gson().fromJson(json, Array<Int>::class.java).toMutableList()
            else -> mutableListOf()
        }
    }

    fun findFavorites(restaurantList: List<Restaurant>): List<Restaurant> {
        restaurantList.forEach { restaurant ->
            restaurant.isFavorite = favorites.contains(restaurant.id)
        }

        return restaurantList
    }

    fun sortList(restaurantList: List<Restaurant>): List<Restaurant> {
        return restaurantList.sortedWith(
                compareByDescending(Restaurant::isFavorite).thenBy(Restaurant::name))
    }

    fun updateFavorites(restaurant: Restaurant): Observable<Unit> {
        return Observable.fromCallable {
            with(restaurant) {
                when {
                    isFavorite -> favorites.add(id)
                    else -> favorites.remove(id)
                }
            }

            val json = Gson().toJson(favorites)
            favoritesRepository.putFavorites(json)
        }
    }
}