package com.easygym.domain.usecase.navigation

import com.easygym.ui.navigation.NavDestination
import com.easygym.utils.enums.UserRole
import javax.inject.Inject

class GetNavDestinationsUseCase @Inject constructor() {
    operator fun invoke(role: UserRole?): List<NavDestination.BottomBar> =
        when (role) {
            UserRole.ADMIN -> listOf(
                NavDestination.BottomBar.Home,
                NavDestination.BottomBar.Users,
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

            else -> emptyList()
        }
}
