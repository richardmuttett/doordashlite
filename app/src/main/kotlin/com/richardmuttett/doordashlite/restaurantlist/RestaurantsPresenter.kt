package com.richardmuttett.doordashlite.restaurantlist

import com.richardmuttett.doordashlite.models.Restaurant
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class RestaurantsPresenter(
        private val view: IRestaurantsView,
        private val interactor: RestaurantsInteractor,
        private val subscribeOn: Scheduler = Schedulers.io(),
        private val observeOn: Scheduler = AndroidSchedulers.mainThread()) : IRestaurantsPresenter {

    override var restaurantList: List<Restaurant> = listOf()
    private val subscriptions = CompositeSubscription()

    override fun fetchRestaurants(lat: Double, lng: Double) {
        view.showLoading()

        subscriptions.add(interactor.getRestaurants(lat, lng)
                .subscribeOn(subscribeOn)
                .observeOn(observeOn)
                .doAfterTerminate { view.hideLoading() }
                .subscribe(this::onDataLoaded, this::onDataNotLoaded))
    }

    private fun onDataLoaded(list: List<Restaurant>) {
        restaurantList = list
        view.refreshList()
    }

    private fun onDataNotLoaded(ex: Throwable) = view.showError()

    override fun updateFavorites(restaurant: Restaurant) {
        interactor.updateFavorites(restaurant)
                .subscribeOn(subscribeOn)
                .subscribe()
    }

    override fun destroy() = subscriptions.unsubscribe()
}