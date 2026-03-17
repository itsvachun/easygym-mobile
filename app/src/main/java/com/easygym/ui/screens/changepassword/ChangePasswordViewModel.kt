package com.easygym.ui.screens.changepassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easygym.domain.usecase.UpdatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val updatePasswordUseCase: UpdatePasswordUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(ChangePasswordState())
    val state: StateFlow<ChangePasswordState> = _state.asStateFlow()

    fun updateState(
        oldPassword: String? = null,
        newPassword: String? = null,
        confirmNewPassword: String? = null,
    ) = _state.update { currentState ->
        currentState.copy(
            updatePassword = currentState.updatePassword.copy(
                oldPassword = oldPassword ?: currentState.updatePassword.oldPassword,
                newPassword = newPassword ?: currentState.updatePassword.newPassword,
            ),
            confirmNewPassword = confirmNewPassword ?: currentState.confirmNewPassword,
        )
    }

    fun changePassword() = viewModelScope.launch {
        updatePasswordUseCase(updatePassword = _state.value.updatePassword)
            .onFailure { error -> _state.update { it.copy(errorMessage = error.message) } }
    }
}