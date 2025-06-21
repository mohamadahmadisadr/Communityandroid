package com.community.feature.restaurants.presentation.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.community.feature.restaurants.presentation.components.RestaurantCard
import com.community.feature.restaurants.presentation.components.RestaurantSearchBar
import com.community.feature.restaurants.presentation.components.FilterChip
import timber.log.Timber

/**
 * Restaurants list screen
 * 
 * This composable displays a list of restaurants with search and filter capabilities.
 * It includes featured restaurants, search functionality, and paginated results.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantsScreen(
    navController: NavController,
    viewModel: RestaurantsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCity by viewModel.selectedCity.collectAsState()
    val selectedCuisine by viewModel.selectedCuisine.collectAsState()
    
    val restaurants = viewModel.restaurants.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "Restaurants",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Search Bar
        RestaurantSearchBar(
            query = searchQuery,
            onQueryChange = viewModel::onSearchQueryChanged,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Filter Chips
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            item {
                FilterChip(
                    selected = selectedCity != null || selectedCuisine != null,
                    onClick = viewModel::clearFilters,
                    label = { Text("Clear Filters") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear filters"
                        )
                    }
                )
            }
            
            // City filters
            items(listOf("Toronto", "Vancouver", "Montreal", "Calgary")) { city ->
                FilterChip(
                    selected = selectedCity == city,
                    onClick = { 
                        viewModel.onCitySelected(if (selectedCity == city) null else city)
                    },
                    label = { Text(city) }
                )
            }
            
            // Cuisine filters
            items(listOf("Italian", "Chinese", "Indian", "Mexican", "Japanese")) { cuisine ->
                FilterChip(
                    selected = selectedCuisine == cuisine,
                    onClick = { 
                        viewModel.onCuisineSelected(if (selectedCuisine == cuisine) null else cuisine)
                    },
                    label = { Text(cuisine) }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Featured Restaurants Section
        if (uiState.featuredRestaurants.isNotEmpty() && searchQuery.isBlank()) {
            Text(
                text = "Featured Restaurants",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                items(uiState.featuredRestaurants) { restaurant ->
                    RestaurantCard(
                        restaurant = restaurant,
                        onClick = {
                            // Navigate to restaurant detail
                            Timber.d("Navigate to restaurant detail: ${restaurant.id}")
                        },
                        onFavoriteClick = {
                            viewModel.onFavoriteToggle(restaurant)
                        },
                        modifier = Modifier.width(280.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "All Restaurants",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        // Restaurants List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(restaurants) { restaurant ->
                restaurant?.let {
                    RestaurantCard(
                        restaurant = it,
                        onClick = {
                            // Navigate to restaurant detail
                            Timber.d("Navigate to restaurant detail: ${it.id}")
                        },
                        onFavoriteClick = {
                            viewModel.onFavoriteToggle(it)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
        
        // Error handling
        uiState.error?.let { error ->
            LaunchedEffect(error) {
                // Show snackbar or error dialog
                Timber.e("Error in restaurants screen: $error")
            }
        }
    }
}
