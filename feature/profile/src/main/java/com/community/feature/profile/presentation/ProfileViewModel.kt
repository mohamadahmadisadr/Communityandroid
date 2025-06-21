package com.community.feature.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.community.core.common.result.Result
import com.community.domain.auth.model.User
import com.community.domain.auth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * ViewModel for the profile screen
 * 
 * This ViewModel manages user profile data and authentication state.
 */
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    val currentUser = authRepository.getCurrentUser()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    init {
        checkAuthenticationStatus()
    }

    private fun checkAuthenticationStatus() {
        viewModelScope.launch {
            val isLoggedIn = authRepository.isLoggedIn()
            _uiState.value = _uiState.value.copy(isLoggedIn = isLoggedIn)
        }
    }

    fun logout() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoggingOut = true)
            
            authRepository.logout()
                .onSuccess {
                    _uiState.value = _uiState.value.copy(
                        isLoggingOut = false,
                        isLoggedIn = false
                    )
                    Timber.d("Logout successful")
                }
                .onError { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoggingOut = false,
                        error = error.message
                    )
                    Timber.e(error, "Logout failed")
                }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

/**
 * UI state for the profile screen
 */
data class ProfileUiState(
    val isLoggedIn: Boolean = false,
    val isLoggingOut: Boolean = false,
    val error: String? = null
)
