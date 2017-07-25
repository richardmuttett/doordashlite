package com.richardmuttett.doordashlite.restaurantlist

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.richardmuttett.doordashlite.R
import kotlinx.android.synthetic.main.activity_main.loadingView
import kotlinx.android.synthetic.main.activity_main.restaurantList

class RestaurantsActivity : Activity(), IRestaurantsView {
    companion object {
        private const val DOOR_DASH_HQ_LAT = 37.422740
        private const val DOOR_DASH_HQ_LNG = -122.139956
    }

    private lateinit var presenter: IRestaurantsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val favoritesRepository = FavoritesRepository(getPreferences(MODE_PRIVATE))
        presenter = RestaurantsPresenter(this, RestaurantsInteractor(favoritesRepository))
        initializeView()
    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

    override fun refreshList() = restaurantList.adapter.notifyDataSetChanged()

    override fun showLoading() {
        loadingView.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loadingView.visibility = View.GONE
    }

    override fun showError() = Toast.makeText(this, R.string.could_not_load_data, Toast.LENGTH_LONG).show()

    private fun initializeView() {
        restaurantList.adapter = RestaurantListAdapter(Glide.with(this), presenter)
        restaurantList.layoutManager = LinearLayoutManager(this)
        restaurantList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        presenter.fetchRestaurants(DOOR_DASH_HQ_LAT, DOOR_DASH_HQ_LNG)
    }
}
