package com.easygym.ui.navigation

import com.easygym.R

sealed interface NavDestination {
    val route: String

    sealed class Common(override val route: String) : NavDestination {
        object LOADING : Common(route = "loading")
        object LOGIN : Common(route = "login")
        object BOTTOM : Common(route = "bottom")
    }

    sealed class BottomBar(override val route: String, val label: String, val iconId: Int) : NavDestination {
        object HOME : BottomBar(route = "home", label = "Home", iconId = R.drawable.home_icon)
        object ATHLETES : BottomBar(route = "atlethes", label = "Atleti", iconId = R.drawable.athletes_icon)
        object CALENDAR : BottomBar(route = "calendar", label = "Calendario", iconId = R.drawable.calendar_icon)
        object PAYMENTS : BottomBar(route = "payments", label = "Pagamenti", iconId = R.drawable.payments_icon)
        object CLUB : BottomBar(route = "club", label = "Club", iconId = R.drawable.club_icon)
    }
}