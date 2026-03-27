package com.easygym.ui.screens.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easygym.domain.model.User
import com.easygym.domain.usecase.user.FetchUsersUseCase
import com.easygym.domain.usecase.user.UsersUseCase
import com.easygym.utils.enums.UserRole
import com.easygym.utils.pagination.PaginationHandler
import com.easygym.utils.pagination.PaginationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class UsersState(
    val pagination: PaginationState<User> = PaginationState(),
    val selectedRole: UserRole? = null
)

@OptIn(FlowPreview::class)
@HiltViewModel
class UsersViewModel @Inject constructor(
    private val usersUseCase: UsersUseCase,
    private val fetchUsersUseCase: FetchUsersUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(UsersState())
    val state: StateFlow<UsersState> = _state.asStateFlow()

    private val paginationHandler = PaginationHandler(
        scope = viewModelScope,
        fetchItems = fetchUsersUseCase::invoke,
        localItemsFlow = usersUseCase(),
        extraFilter = state.map { s ->
            { user: User -> s.selectedRole == null || user.role == s.selectedRole }
        }.distinctUntilChanged(),
        onStateUpdate = { pagination ->
            _state.update { it.copy(pagination = pagination) }
        }
    )

    fun onSearchChanged(search: String) = paginationHandler.onSearchChanged(search)

    fun loadNextPage() = paginationHandler.loadNextPage()

    fun onRoleSelected(role: UserRole?) = _state.update { it.copy(selectedRole = role) }
}
