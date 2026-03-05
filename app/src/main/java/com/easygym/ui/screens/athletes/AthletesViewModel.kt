package com.easygym.ui.screens.athletes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easygym.domain.model.User
import com.easygym.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AthletesState(
    val isLoading: Boolean = true,
    val users: List<User> = listOf(),
    val errorMessage: String? = null
)


@HiltViewModel
class AthletesViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    private val _state = MutableStateFlow(AthletesState())
    val state: StateFlow<AthletesState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                userRepository.getAll()
                _state.update { it.copy(errorMessage = null) }
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = e.message) }
            }

            userRepository.users.collect { users ->
                _state.update { it.copy(isLoading = false, users = users) }
            }
        }
    }

}