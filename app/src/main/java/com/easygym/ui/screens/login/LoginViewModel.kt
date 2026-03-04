package com.easygym.ui.screens.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easygym.data.remote.model.auth.LoginRequest
import com.easygym.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginState(
    val loginRequest: LoginRequest = LoginRequest("", ""),
    val isLoggedIn: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun login() {
        val currentState = _state.value
        when {
            currentState.loginRequest.email.isBlank() -> {
                _state.update { it.copy(errorMessage = "Compila tutti i campi") }
                return
            }

            !Patterns.EMAIL_ADDRESS.matcher(currentState.loginRequest.email).matches() -> {
                _state.update { it.copy(errorMessage = "Email non valida") }
                return
            }
        }
        _state.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch {
            try {
                authRepository.login(_state.value.loginRequest)
                _state.update { it.copy(isLoading = false, errorMessage = null) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, errorMessage = "Errore: ${e.message}") }
            }
        }
    }

    fun togglePasswordVisibility() = _state.update { currentState ->
        currentState.copy(isPasswordVisible = !currentState.isPasswordVisible)
    }

    fun onEmailChanged(email: String) = _state.update { currentState ->
        currentState.copy(loginRequest = currentState.loginRequest.copy(email = email))
    }

    fun onPasswordChanged(password: String) = _state.update { currentState ->
        currentState.copy(loginRequest = currentState.loginRequest.copy(password = password))
    }
}