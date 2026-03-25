package com.easygym.ui.navigation

import com.easygym.R

sealed interface NavDestination {
    val route: String

    sealed class Common(override val route: String) : NavDestination {
        object Loading : Common(route = "loading")
        object Login : Common(route = "login")
        object Bottom : Common(route = "bottom")
        object CreateUser : Common(route = "createUser")
        object ChangePassword : Common(route = "changePassword")
        object Users : Common(route = "users")
    }

    sealed class BottomBar(override val route: String, val label: String, val iconId: Int) : NavDestination {
        object Home : BottomBar(route = "home", label = "Home", iconId = R.drawable.home_icon)
        object Athletes : BottomBar(route = "atlethes", label = "Atleti", iconId = R.drawable.athletes_icon)
        object Calendar : BottomBar(route = "calendar", label = "Calendario", iconId = R.drawable.calendar_icon)
        object Payments : BottomBar(route = "payments", label = "Pagamenti", iconId = R.drawable.payments_icon)
        object Club : BottomBar(route = "club", label = "Club", iconId = R.drawable.club_icon)
    }
}