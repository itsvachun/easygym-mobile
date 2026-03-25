package com.easygym.ui.navigation

import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easygym.data.remote.model.auth.RefreshRequest
import com.easygym.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

enum class UserRole {
    ADMIN,
    COACH,
    ATHLETE
}

data class NavState(
    val bottomDestinations: List<NavDestination.BottomBar> = listOf<NavDestination.BottomBar>(),
    val role: UserRole? = null,
    val isLoading: Boolean = true,
)

@HiltViewModel
class NavViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NavState())
    val state: StateFlow<NavState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.accessToken.collect { token ->
                if (isAccessTokenNotValid(token)) {
                    _state.update { it.copy(isLoading = true) }
                    val refreshToken = repository.refreshToken.first()
                    refreshToken?.let { refreshToken ->
                        repository.refresh(RefreshRequest(refreshToken))
                        return@collect
                    }
                    _state.update { NavState(isLoading = false) }
                }
            }
        }
    }

    private fun isAccessTokenNotValid(token: String?): Boolean {
        if (!token.isNullOrBlank())
            runCatching {
                val parts = token.split(".")
                val payload = String(Base64.decode(parts[1], Base64.URL_SAFE))
                val json = JSONObject(payload)

                val role = UserRole.valueOf(json.getString("role"))
                val exp = json.getLong("exp")
                val currentTime = System.currentTimeMillis() / 1000

                if (currentTime < exp) {
                    _state.update { currentState ->
                        currentState.copy(
                            bottomDestinations = getBottomDestinationsByRole(role),
                            role = role,
                            isLoading = false
                        )
                    }
                    return false
                }
            }
        return true
    }

    private fun getBottomDestinationsByRole(role: UserRole? = null): List<NavDestination.BottomBar> =
        when (role) {
            UserRole.ADMIN -> listOf(
                NavDestination.BottomBar.Home,
                NavDestination.BottomBar.Athletes,
                NavDestination.BottomBar.Calendar,
                NavDestination.BottomBar.Payments,
                NavDestination.BottomBar.Club,
            )

            UserRole.COACH -> listOf(
                NavDestination.BottomBar.Athletes,
                NavDestination.BottomBar.Calendar,
                NavDestination.BottomBar.Club,
            )

            UserRole.ATHLETE -> listOf(
                NavDestination.BottomBar.Calendar,
                NavDestination.BottomBar.Club,
            )

            else -> listOf()
        }
}