package com.easygym.ui.navigation

import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easygym.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.json.JSONObject
import javax.inject.Inject

enum class UserRole {
    ADMIN,
    COACH,
    ATHLETE
}

data class NavState(
    val isLoading: Boolean = true,
    val availableDestinations: List<NavDestination> = listOf(),
    val bottomDestinations: List<NavDestination.BottomBar> = availableDestinations.filterIsInstance<NavDestination.BottomBar>(),
    val role: UserRole? = null,
)

@HiltViewModel
class NavViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    val state: StateFlow<NavState> =
        repository.jwtToken
            .map { token -> getNavStateByToken(token) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = NavState()
            )

    private fun getNavStateByToken(token: String?): NavState {
        if (token.isNullOrBlank()) {
            return NavState(
                isLoading = false,
                availableDestinations = getDestinationsByRole()
            )
        }

        return try {
            val parts = token.split(".")
            val payload = String(Base64.decode(parts[1], Base64.URL_SAFE))
            val json = JSONObject(payload)

            val role = UserRole.valueOf(json.getString("role"))
            val exp = json.getLong("exp")
            val currentTime = System.currentTimeMillis() / 1000

            if (currentTime < exp) {
                NavState(
                    isLoading = false,
                    availableDestinations = getDestinationsByRole(role),
                    role = role
                )
            } else {
                NavState(
                    isLoading = false,
                    availableDestinations = getDestinationsByRole()
                )
            }
        } catch (e: Exception) {
            NavState(
                isLoading = false,
                availableDestinations = getDestinationsByRole()
            )
        }
    }


    private fun getDestinationsByRole(role: UserRole? = null): List<NavDestination> =
        when (role) {
            UserRole.ADMIN -> listOf<NavDestination>(
                NavDestination.BottomBar.HOME,
                NavDestination.BottomBar.ATHLETES,
                NavDestination.BottomBar.CALENDAR,
                NavDestination.BottomBar.PAYMENTS,
                NavDestination.BottomBar.CLUB,
            )

            UserRole.COACH -> listOf<NavDestination>(
                NavDestination.BottomBar.ATHLETES,
                NavDestination.BottomBar.CALENDAR,
                NavDestination.BottomBar.CLUB,
            )

            UserRole.ATHLETE -> listOf<NavDestination>(
                NavDestination.BottomBar.CALENDAR,
                NavDestination.BottomBar.CLUB,
            )

            else -> listOf<NavDestination>(
                NavDestination.Common.LOGIN
            )
        }
}