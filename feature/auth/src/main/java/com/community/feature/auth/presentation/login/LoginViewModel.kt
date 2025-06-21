package com.community.feature.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.community.core.common.result.Result
import com.community.domain.auth.model.LoginCredentials
import com.community.domain.auth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * ViewModel for the login screen
 * 
 * This ViewModel manages the state for user authentication,
 * including login form validation and API calls.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChanged(email: String) {
        _uiState.value = _uiState.value.copy(
            email = email,
            emailError = null
        )
    }

    fun onPasswordChanged(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password,
            passwordError = null
        )
    }

    fun onPasswordVisibilityToggle() {
        _uiState.value = _uiState.value.copy(
            isPasswordVisible = !_uiState.value.isPasswordVisible
        )
    }

    fun login() {
        val currentState = _uiState.value
        
        // Validate input
        val emailError = validateEmail(currentState.email)
        val passwordError = validatePassword(currentState.password)
        
        if (emailError != null || passwordError != null) {
            _uiState.value = currentState.copy(
                emailError = emailError,
                passwordError = passwordError
            )
            return
        }

        // Proceed with login
        viewModelScope.launch {
            _uiState.value = currentState.copy(isLoading = true, error = null)
            
            val credentials = LoginCredentials(
                email = currentState.email,
                password = currentState.password
            )
            
            authRepository.login(credentials)
                .onSuccess { user ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isLoginSuccessful = true
                    )
                    Timber.d("Login successful for user: ${user.email}")
                }
                .onError { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message ?: "Login failed"
                    )
                    Timber.e(error, "Login failed")
                }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    private fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> "Email is required"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email format"
            else -> null
        }
    }

    private fun validatePassword(password: String): String? {
        return when {
            password.isBlank() -> "Password is required"
            password.length < 6 -> "Password must be at least 6 characters"
            else -> null
        }
    }
}

/**
 * UI state for the login screen
 */
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isLoginSuccessful: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val error: String? = null
)
