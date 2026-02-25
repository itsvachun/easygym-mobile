package com.easygym.ui.navigation

import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easygym.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import org.json.JSONObject
import javax.inject.Inject

enum class UserRole {
    ADMIN,
    COACH,
    ATHLETE
}

data class BottomBarState(
    val bottomDestinations: List<NavDestination.BottomBar> = listOf(),
    val role: UserRole? = null,
    val selectedIndex: Int = 0
)

@HiltViewModel
class BottomBarViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(BottomBarState())
    val state: StateFlow<BottomBarState> = _state.asStateFlow()

    init {
        repository.jwtToken
            .map { token ->
                getBottomBarStateByToken(token)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = BottomBarState()
            )
    }

    fun updateSelectedIndex(index: Int) = _state.update { currentState ->
        currentState.copy(selectedIndex = index)
    }

    fun getBottomBarStateByToken(token: String?): BottomBarState =
        try {
            val parts = token?.split(".")

            val payload = String(Base64.decode(parts?.get(1), Base64.URL_SAFE))
            val json = JSONObject(payload)

            val role = UserRole.valueOf(json.getString("role"))
            val exp = json.getLong("exp")
            val currentTime = System.currentTimeMillis() / 1000

            if (currentTime < exp) {
                BottomBarState(bottomDestinations = getDestinationsByRole(role), role = role)
            } else {
                BottomBarState()
            }
        } catch (_: Exception) {
            BottomBarState()
        }


    private fun getDestinationsByRole(role: UserRole): List<NavDestination.BottomBar> =
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
        }
}