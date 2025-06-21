package com.community.feature.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.community.core.database.dao.SearchDao
import com.community.core.database.entity.SearchHistoryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

/**
 * ViewModel for the search screen
 * 
 * This ViewModel manages the global search functionality across all categories
 * including search history and suggestions.
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchDao: SearchDao
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    val searchHistory = searchDao.getRecentSearches()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val searchSuggestions = searchQuery
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.length >= 2) {
                flow {
                    try {
                        val suggestions = searchDao.getSearchSuggestions(query)
                        emit(suggestions)
                    } catch (e: Exception) {
                        Timber.e(e, "Error getting search suggestions")
                        emit(emptyList<String>())
                    }
                }
            } else {
                flowOf(emptyList())
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onCategorySelected(category: String?) {
        _selectedCategory.value = category
        Timber.d("Category selected: $category")
    }

    fun performSearch(query: String = _searchQuery.value) {
        if (query.isBlank()) return

        viewModelScope.launch {
            try {
                // Save search to history
                val searchHistory = SearchHistoryEntity(
                    id = UUID.randomUUID().toString(),
                    query = query,
                    category = _selectedCategory.value,
                    resultCount = null // Will be updated when results are loaded
                )
                searchDao.insertSearch(searchHistory)

                _uiState.value = _uiState.value.copy(
                    isSearching = true,
                    searchResults = emptyList(),
                    error = null
                )

                // TODO: Implement actual search across different categories
                // For now, just simulate search
                kotlinx.coroutines.delay(1000)
                
                val mockResults = generateMockSearchResults(query)
                
                _uiState.value = _uiState.value.copy(
                    isSearching = false,
                    searchResults = mockResults,
                    hasSearched = true
                )

                Timber.d("Search performed for: $query, category: ${_selectedCategory.value}")
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSearching = false,
                    error = e.message ?: "Search failed"
                )
                Timber.e(e, "Search failed")
            }
        }
    }

    fun onSearchHistoryItemClick(query: String) {
        _searchQuery.value = query
        performSearch(query)
    }

    fun clearSearchHistory() {
        viewModelScope.launch {
            try {
                searchDao.deleteAllSearches()
                Timber.d("Search history cleared")
            } catch (e: Exception) {
                Timber.e(e, "Error clearing search history")
            }
        }
    }

    fun clearSearch() {
        _searchQuery.value = ""
        _selectedCategory.value = null
        _uiState.value = SearchUiState()
    }

    private fun generateMockSearchResults(query: String): List<SearchResult> {
        // Mock search results for demonstration
        return listOf(
            SearchResult(
                id = "1",
                title = "Pizza Palace - $query",
                description = "Best pizza in town with $query",
                category = "restaurant",
                imageUrl = null
            ),
            SearchResult(
                id = "2",
                title = "Coffee Corner - $query",
                description = "Great coffee and $query atmosphere",
                category = "cafe",
                imageUrl = null
            ),
            SearchResult(
                id = "3",
                title = "Downtown Apartment - $query",
                description = "Modern apartment near $query",
                category = "rental",
                imageUrl = null
            )
        )
    }
}

/**
 * UI state for the search screen
 */
data class SearchUiState(
    val isSearching: Boolean = false,
    val hasSearched: Boolean = false,
    val searchResults: List<SearchResult> = emptyList(),
    val error: String? = null
)

/**
 * Search result model
 */
data class SearchResult(
    val id: String,
    val title: String,
    val description: String,
    val category: String,
    val imageUrl: String?
)
