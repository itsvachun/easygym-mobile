package com.easygym.ui.screens

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easygym.data.remote.auth.model.Login
import com.easygym.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginState(
    val loginRequest: Login.Request = Login.Request("", ""),
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
        _state.value = currentState.copy(isLoading = true)
        if (currentState.loginRequest.email.isBlank()) {
            _state.value = currentState.copy(errorMessage = "Compila tutti i campi")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(currentState.loginRequest.email).matches()) {
            _state.value = currentState.copy(errorMessage = "Email non valida")
        } else {
            viewModelScope.launch {
                try {
                    authRepository.login(currentState.loginRequest)
                    _state.value = currentState.copy(errorMessage = null)
                } catch (e: Exception) {
                    _state.value = currentState.copy(errorMessage = "Errore: ${e.message}")
                }
            }
        }
    }

    fun toggleVisibility() {
        val currentState = _state.value
        _state.value = currentState.copy(isPasswordVisible = !currentState.isPasswordVisible)
    }

    fun onEmailChanged(email: String) {
        val currentState = _state.value
        _state.value = currentState.copy(loginRequest = currentState.loginRequest.copy(email = email))
    }

    fun onPasswordChanged(password: String) {
        val currentState = _state.value
        _state.value = currentState.copy(loginRequest = currentState.loginRequest.copy(password = password))
    }

}