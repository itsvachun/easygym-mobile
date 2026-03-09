package com.easygym.ui.screens.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easygym.domain.model.User
import com.easygym.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UsersState(
    val isLoading: Boolean = true,
    val users: List<User> = listOf(),
    val selectedRole: String = "Tutti",
    val errorMessage: String? = null
)

sealed class UsersEvent {
    object NavigateToCreateUser : UsersEvent()
}

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UsersState())
    val state: StateFlow<UsersState> = _state.asStateFlow()

    private var _allUsers: List<User> = emptyList()

    private val _events = MutableSharedFlow<UsersEvent>()
    val events = _events.asSharedFlow()

    init {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            try {
                userRepository.getAll()

                userRepository.users.collect { users ->

                    _allUsers = users

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

    fun onRoleSelected(role: String) {

        val filteredUsers = when (role) {

            "Coach" -> _allUsers.filter {
                it.role.toString() == "COACH"
            }

            "Atleti" -> _allUsers.filter {
                it.role.toString() == "ATHLETE"
            }

            else -> _allUsers
        }

        _state.update {
            it.copy(
                selectedRole = role,
                users = filteredUsers
            )
        }

        fun addUserOnClick() {
            viewModelScope.launch {
                _events.emit(UsersEvent.NavigateToCreateUser)
            }
        }
    }
}