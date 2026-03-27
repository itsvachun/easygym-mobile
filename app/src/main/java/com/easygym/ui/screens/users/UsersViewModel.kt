package com.easygym.ui.screens.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easygym.domain.model.User
import com.easygym.domain.usecase.user.FetchUsersUseCase
import com.easygym.domain.usecase.user.UsersUseCase
import com.easygym.ui.navigation.UserRole
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UsersState(
    val isLoading: Boolean = false,
    val isFetchingNextPage: Boolean = false,
    val search: String = "",
    val selectedRole: UserRole? = null,
    val users: List<User> = listOf(),
    val errorMessage: String? = null
)

@OptIn(FlowPreview::class)
@HiltViewModel
class UsersViewModel @Inject constructor(
    private val usersUseCase: UsersUseCase,
    private val fetchUsersUseCase: FetchUsersUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(UsersState())
    val state: StateFlow<UsersState> = _state.asStateFlow()

    private var currentPage = 0
    private var isLastPage = false
    private var fetchJob: Job? = null

    init {
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            usersUseCase().combine(_state.map { it.search }.distinctUntilChanged()) { athletes, query ->
                if (query.isBlank()) athletes
                else athletes.filter {
                    it.firstName.contains(query, ignoreCase = true) ||
                            it.lastName.contains(query, ignoreCase = true)
                }
            }.collect { filteredAthletes ->
                _state.update { it.copy(users = filteredAthletes, isLoading = false) }
            }
        }

        _state
            .map { it.search }
            .distinctUntilChanged()
            .debounce(1000)
            .onEach { resetAndFetch() }
            .launchIn(viewModelScope)
    }

    private fun resetAndFetch() {
        currentPage = 0
        isLastPage = false
        fetchJob?.cancel()
        _state.update { it.copy(errorMessage = null) }
        loadNextPage()
    }

    fun onSearchChanged(search: String) = _state.update { it.copy(search = search) }

    fun loadNextPage() {
        if (isLastPage || _state.value.isFetchingNextPage) return

        fetchJob = viewModelScope.launch {
            _state.update { it.copy(isFetchingNextPage = true, errorMessage = null) }

            val result = fetchUsersUseCase(_state.value.search, currentPage)

            result.onSuccess { last ->
                isLastPage = last
                currentPage++
            }.onFailure { e ->
                _state.update { it.copy(errorMessage = e.message) }
            }

            _state.update { it.copy(isFetchingNextPage = false) }
        }
    }

    fun onRoleSelected(role: UserRole?) = _state.update { it.copy(selectedRole = role) }
}
