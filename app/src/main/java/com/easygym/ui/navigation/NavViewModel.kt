package com.easygym.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easygym.domain.usecase.auth.GetSessionUseCase
import com.easygym.domain.usecase.auth.RefreshSessionUseCase
import com.easygym.domain.usecase.navigation.GetNavDestinationsUseCase
import com.easygym.utils.enums.UserRole
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


data class NavState(
    val bottomDestinations: List<NavDestination.BottomBar> = listOf<NavDestination.BottomBar>(),
    val role: UserRole? = null,
    val isLoading: Boolean = true,
)

@HiltViewModel
class NavViewModel @Inject constructor(
    private val getSessionUseCase: GetSessionUseCase,
    private val refreshSessionUseCase: RefreshSessionUseCase,
    private val getNavDestinationsUseCase: GetNavDestinationsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(NavState())
    val state: StateFlow<NavState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getSessionUseCase().collect { session ->
                if (session.isExpired && !session.accessToken.isNullOrBlank()) {
                    _state.update { it.copy(isLoading = true) }
                    refreshSessionUseCase()
                } else {
                    _state.update {
                        NavState(
                            bottomDestinations = getNavDestinationsUseCase(session.role),
                            role = session.role,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}