package com.easygym.ui.screens.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easygym.domain.model.User
import com.easygym.domain.repository.UserRepository
import com.easygym.ui.navigation.UserRole
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UsersState(
    val isLoading: Boolean = true,
    val users: List<User> = listOf(),
    val selectedRole: UserRole? = null,
    val errorMessage: String? = null,
    val search: String = ""
) {
    val filteredUsers = users
        .filter {
            println(it.role)
            it.role == selectedRole
        }
}

sealed class UsersEvent {
    object NavigateToCreateUser : UsersEvent()
}

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UsersState())
    val state: StateFlow<UsersState> = _state.asStateFlow()


    init {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            try {
                userRepository.getAll()
                userRepository.users.collect { users ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            users = users
                        )
                    }
                }

            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message
                    )
                }
            }
        }
    }

    fun onRoleSelected(role: UserRole?) = _state.update { it.copy(selectedRole = role) }

    fun onSearchChanged(search: String) = _state.update { it.copy(search = search) }
}