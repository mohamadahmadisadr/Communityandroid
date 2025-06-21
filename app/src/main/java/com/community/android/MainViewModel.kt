package com.community.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for MainActivity
 * 
 * Manages the main activity state including splash screen loading state
 * and initial app setup.
 */
@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    init {
        // Simulate app initialization
        viewModelScope.launch {
            // Perform any necessary initialization here
            // For now, just a delay to show splash screen
            delay(2000)
            _isLoading.value = false
        }
    }
}
