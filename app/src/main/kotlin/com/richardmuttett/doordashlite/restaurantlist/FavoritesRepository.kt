package com.richardmuttett.doordashlite.restaurantlist

import android.content.SharedPreferences

class FavoritesRepository(private val preferences: SharedPreferences) {
    companion object {
        private const val FAVORITES_KEY = "FAVORITES"
    }

    fun getFavorites(): String = preferences.getString(FAVORITES_KEY, "")

    fun putFavorites(value: String) {
        preferences.edit().putString(FAVORITES_KEY, value).apply()
    }
}