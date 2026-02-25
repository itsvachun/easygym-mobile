package com.easygym.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.easygym.ui.screens.athletes.AthletesScreen
import com.easygym.ui.screens.calendar.CalendarScreen
import com.easygym.ui.screens.club.ClubScreen
import com.easygym.ui.screens.home.HomeScreen
import com.easygym.ui.screens.loading.LoadingScreen
import com.easygym.ui.screens.login.LoginScreen
import com.easygym.ui.screens.payments.PaymentScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = NavDestination.Common.LOADING.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(NavDestination.Common.LOADING.route) {
                LoadingScreen()
            }
            composable(NavDestination.Common.LOGIN.route) {
                LoginScreen()
            }
            composable(NavDestination.BottomBar.HOME.route) {
                HomeScreen()
            }
            composable(NavDestination.BottomBar.ATHLETES.route) {
                AthletesScreen()
            }
            composable(NavDestination.BottomBar.CALENDAR.route) {
                CalendarScreen()
            }
            composable(NavDestination.BottomBar.PAYMENTS.route) {
                PaymentScreen()
            }
            composable(NavDestination.BottomBar.CLUB.route) {
                ClubScreen()
            }
        }
    }
}