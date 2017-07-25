package com.richardmuttett.doordashlite.restaurantlist

import com.richardmuttett.doordashlite.models.Restaurant
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import rx.Observable
import rx.schedulers.Schedulers

class RestaurantsPresenterTest {

    companion object {
        private const val TEST_LAT = 0.0
        private const val TEST_LNG = 0.0
    }

    @Mock
    lateinit var view: IRestaurantsView

    @Mock
    lateinit var restaurantsInteractor: RestaurantsInteractor

    @Mock
    lateinit var restaurant: Restaurant

    lateinit var presenter: RestaurantsPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        presenter = RestaurantsPresenter(
                view,
                restaurantsInteractor,
                Schedulers.immediate(),
                Schedulers.immediate())
    }

    @Test
    fun testGetRestaurants() {
        given(restaurantsInteractor.getRestaurants(TEST_LAT, TEST_LNG))
                .willReturn(Observable.just(listOf()))

        presenter.fetchRestaurants(TEST_LAT, TEST_LNG)
        verify(view).showLoading()
        verify(view).refreshList()
        verify(view).hideLoading()
    }

    @Test
    fun testGetRestaurantsFailure() {
        given(restaurantsInteractor.getRestaurants(TEST_LAT, TEST_LNG))
                .willReturn(Observable.error(Exception()))

        presenter.fetchRestaurants(TEST_LAT, TEST_LNG)
        verify(view).showLoading()
        verify(view).showError()
        verify(view).hideLoading()
    }

    @Test
    fun testUpdateFavorites() {
        given(restaurantsInteractor.updateFavorites(restaurant)).willReturn(Observable.empty())
        presenter.updateFavorites(restaurant)
        verify(restaurantsInteractor).updateFavorites(restaurant)
    }
}

