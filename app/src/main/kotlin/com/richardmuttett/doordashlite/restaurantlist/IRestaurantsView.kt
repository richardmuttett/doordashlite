package com.richardmuttett.doordashlite.restaurantlist

interface IRestaurantsView {
    fun showLoading()
    fun hideLoading()
    fun refreshList()
    fun showError()
}
