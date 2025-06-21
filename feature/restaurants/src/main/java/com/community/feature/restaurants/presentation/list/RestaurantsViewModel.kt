package com.community.feature.restaurants.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.community.core.common.result.Result
import com.community.domain.restaurants.model.Restaurant
import com.community.domain.restaurants.repository.RestaurantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * ViewModel for the restaurants list screen
 * 
 * This ViewModel manages the state for the restaurants listing,
 * including pagination, filtering, and search functionality.
 */
@HiltViewModel
class RestaurantsViewModel @Inject constructor(
    private val restaurantRepository: RestaurantRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RestaurantsUiState())
    val uiState: StateFlow<RestaurantsUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCity = MutableStateFlow<String?>(null)
    val selectedCity: StateFlow<String?> = _selectedCity.asStateFlow()

    private val _selectedCuisine = MutableStateFlow<String?>(null)
    val selectedCuisine: StateFlow<String?> = _selectedCuisine.asStateFlow()

    val restaurants: Flow<PagingData<Restaurant>> = combine(
        searchQuery,
        selectedCity,
        selectedCuisine
    ) { query, city, cuisine ->
        Triple(query, city, cuisine)
    }.flatMapLatest { (query, city, cuisine) ->
        when {
            query.isNotBlank() -> restaurantRepository.searchRestaurants(query)
            city != null -> restaurantRepository.getRestaurantsByCity(city)
            cuisine != null -> restaurantRepository.getRestaurantsByCuisine(cuisine)
            else -> restaurantRepository.getRestaurants()
        }
    }.cachedIn(viewModelScope)

    init {
        loadFeaturedRestaurants()
        refreshRestaurants()
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        Timber.d("Search query changed: $query")
    }

    fun onCitySelected(city: String?) {
        _selectedCity.value = city
        Timber.d("City selected: $city")
    }

    fun onCuisineSelected(cuisine: String?) {
        _selectedCuisine.value = cuisine
        Timber.d("Cuisine selected: $cuisine")
    }

    fun clearFilters() {
        _searchQuery.value = ""
        _selectedCity.value = null
        _selectedCuisine.value = null
        Timber.d("Filters cleared")
    }

    fun refreshRestaurants() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isRefreshing = true)
            
            restaurantRepository.refreshRestaurants()
                .onSuccess {
                    _uiState.value = _uiState.value.copy(
                        isRefreshing = false,
                        error = null
                    )
                    Timber.d("Restaurants refreshed successfully")
                }
                .onError { error ->
                    _uiState.value = _uiState.value.copy(
                        isRefreshing = false,
                        error = error.message
                    )
                    Timber.e(error, "Failed to refresh restaurants")
                }
        }
    }

    private fun loadFeaturedRestaurants() {
        viewModelScope.launch {
            restaurantRepository.getFeaturedRestaurants()
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            _uiState.value = _uiState.value.copy(
                                featuredRestaurants = result.data,
                                isLoadingFeatured = false
                            )
                            Timber.d("Featured restaurants loaded: ${result.data.size}")
                        }
                        is Result.Error -> {
                            _uiState.value = _uiState.value.copy(
                                isLoadingFeatured = false,
                                error = result.exception.message
                            )
                            Timber.e(result.exception, "Failed to load featured restaurants")
                        }
                        is Result.Loading -> {
                            _uiState.value = _uiState.value.copy(isLoadingFeatured = true)
                        }
                    }
                }
        }
    }

    fun onFavoriteToggle(restaurant: Restaurant) {
        viewModelScope.launch {
            val isFavorite = restaurantRepository.isFavorite(restaurant.id)
            
            if (isFavorite) {
                restaurantRepository.removeFromFavorites(restaurant.id)
                    .onSuccess {
                        Timber.d("Restaurant removed from favorites: ${restaurant.name}")
                    }
                    .onError { error ->
                        Timber.e(error, "Failed to remove from favorites: ${restaurant.name}")
                    }
            } else {
                restaurantRepository.addToFavorites(restaurant.id)
                    .onSuccess {
                        Timber.d("Restaurant added to favorites: ${restaurant.name}")
                    }
                    .onError { error ->
                        Timber.e(error, "Failed to add to favorites: ${restaurant.name}")
                    }
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

/**
 * UI state for the restaurants screen
 */
data class RestaurantsUiState(
    val featuredRestaurants: List<Restaurant> = emptyList(),
    val isLoadingFeatured: Boolean = true,
    val isRefreshing: Boolean = false,
    val error: String? = null
)
