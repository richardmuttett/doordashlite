package com.richardmuttett.doordashlite.restaurantlist

import com.richardmuttett.doordashlite.models.Restaurant
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import kotlin.test.assertFalse

class RestaurantsInteractorTest {

    private val json = "[1,3,7]"
    private val favorites = mutableListOf(1, 3)

    @Mock
    lateinit var favoritesRepository: FavoritesRepository

    lateinit var interactor: RestaurantsInteractor

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        interactor = RestaurantsInteractor(favoritesRepository, favorites)
    }

    @Test
    fun testLoadFavoritesWhenNoData() {
        given(favoritesRepository.getFavorites()).willReturn("")
        val favorites = interactor.loadFavorites()
        assert(favorites.size == 0)
    }

    @Test
    fun testLoadFavorites() {
        given(favoritesRepository.getFavorites()).willReturn(json)
        val favorites = interactor.loadFavorites()
        assert(favorites.size == 3)
        assert(favorites.contains(1))
        assert(favorites.contains(3))
        assert(favorites.contains(7))
        assertFalse(favorites.contains(2))
    }

    @Test
    fun testSortList() {
        val list = listOf(
                Restaurant(
                        id = 1,
                        name = "M",
                        description = "",
                        coverImageUrl = "",
                        status = "",
                        isFavorite = false),
                Restaurant(
                        id = 2,
                        name = "Z",
                        description = "",
                        coverImageUrl = "",
                        status = "",
                        isFavorite = true),
                Restaurant(
                        id = 3,
                        name = "C",
                        description = "",
                        coverImageUrl = "",
                        status = "",
                        isFavorite = true),
                Restaurant(
                        id = 4,
                        name = "D",
                        description = "",
                        coverImageUrl = "",
                        status = "",
                        isFavorite = false))

        val sortedList = interactor.sortList(list)
        assert(sortedList[0].id == 3)
        assert(sortedList[1].id == 2)
        assert(sortedList[2].id == 4)
        assert(sortedList[3].id == 1)
    }

    @Test
    fun testFindFavorites() {
        val originalList = listOf(
                Restaurant(
                        id = 1,
                        name = "A",
                        description = "",
                        coverImageUrl = "",
                        status = "",
                        isFavorite = false),
                Restaurant(
                        id = 2,
                        name = "B",
                        description = "",
                        coverImageUrl = "",
                        status = "",
                        isFavorite = false),
                Restaurant(
                        id = 3,
                        name = "C",
                        description = "",
                        coverImageUrl = "",
                        status = "",
                        isFavorite = false))

        val newList = interactor.findFavorites(originalList)
        assert(newList[0].isFavorite)
        assertFalse(newList[1].isFavorite)
        assert(newList[2].isFavorite)
    }
}

