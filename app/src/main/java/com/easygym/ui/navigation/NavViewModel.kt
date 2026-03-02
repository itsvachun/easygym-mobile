package com.easygym.ui.navigation

import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easygym.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
            repository.jwtToken.collect { token ->
                modifyNavStateByToken(token)
            }
        }
    }

    private fun modifyNavStateByToken(token: String?) {
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
                    return
                }
            }

        _state.update { currentState ->
            currentState.copy(
                bottomDestinations = getBottomDestinationsByRole(),
                isLoading = false
            )
        }
    }


    private fun getBottomDestinationsByRole(role: UserRole? = null): List<NavDestination.BottomBar> =
        when (role) {
            UserRole.ADMIN -> listOf(
                NavDestination.BottomBar.HOME,
                NavDestination.BottomBar.ATHLETES,
                NavDestination.BottomBar.CALENDAR,
                NavDestination.BottomBar.PAYMENTS,
                NavDestination.BottomBar.CLUB,
            )

            UserRole.COACH -> listOf(
                NavDestination.BottomBar.ATHLETES,
                NavDestination.BottomBar.CALENDAR,
                NavDestination.BottomBar.CLUB,
            )

            UserRole.ATHLETE -> listOf(
                NavDestination.BottomBar.CALENDAR,
                NavDestination.BottomBar.CLUB,
            )

            else -> listOf()
        }
}