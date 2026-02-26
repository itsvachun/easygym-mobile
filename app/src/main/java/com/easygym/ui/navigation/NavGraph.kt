package com.easygym.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.easygym.ui.screens.athletes.AthletesScreen
import com.easygym.ui.screens.calendar.CalendarScreen
import com.easygym.ui.screens.club.ClubScreen
import com.easygym.ui.screens.home.HomeScreen
import com.easygym.ui.screens.loadinggate.LoadingGateScreen
import com.easygym.ui.screens.login.LoginScreen
import com.easygym.ui.screens.payments.PaymentScreen

@Composable
fun NavGraph(
    viewModel: NavViewModel = hiltViewModel()
) {
    val navState by viewModel.state.collectAsState()
    val navController = rememberNavController()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    LaunchedEffect(navState.availableDestinations) {
        navController.navigate(navState.availableDestinations.first().route)
    }

    Scaffold(
        bottomBar = {
            if (navState.bottomDestinations.map { it.route }.contains(currentRoute))
                BottomBar(bottomDestinations = navState.bottomDestinations)
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = navState.availableDestinations.first().route,
            modifier = Modifier.padding(padding)
        ) {
            composable(NavDestination.Common.LOADING.route) {
                LoadingGateScreen()
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