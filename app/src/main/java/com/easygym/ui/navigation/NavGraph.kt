package com.easygym.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
import com.easygym.ui.screens.loadinggate.LoadingGateScreen
import com.easygym.ui.screens.login.LoginScreen
import com.easygym.ui.screens.payments.PaymentsScreen
import com.easygym.ui.screens.users.CreateUserScreen
import com.easygym.ui.screens.users.UsersScreen

@Composable
fun NavGraph(
    viewModel: NavViewModel = hiltViewModel()
) {
    val navState by viewModel.state.collectAsState()
    val navController = rememberNavController()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val pagerState = rememberPagerState(pageCount = { navState.bottomDestinations.size })

    Scaffold(
        bottomBar = {
            if (currentRoute == NavDestination.Common.BOTTOM.route)
                BottomBar(
                    bottomDestinations = navState.bottomDestinations,
                    pagerState = pagerState,
                )
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = when {
                navState.isLoading -> NavDestination.Common.LOADING.route
                navState.bottomDestinations.isNotEmpty() -> NavDestination.Common.BOTTOM.route
                else -> NavDestination.Common.LOGIN.route
            },
            modifier = Modifier.padding(padding)
        ) {
            composable(NavDestination.Common.LOADING.route) {
                LoadingGateScreen()
            }
            composable(NavDestination.Common.LOGIN.route) {
                LoginScreen()
            }
            composable(NavDestination.Common.BOTTOM.route) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    when (navState.bottomDestinations[page].route) {
                        NavDestination.BottomBar.HOME.route -> UsersScreen(navController = navController)
                        NavDestination.BottomBar.ATHLETES.route -> AthletesScreen()
                        NavDestination.BottomBar.CALENDAR.route -> CalendarScreen()
                        NavDestination.BottomBar.PAYMENTS.route -> PaymentsScreen()
                        NavDestination.BottomBar.CLUB.route -> ClubScreen()
                    }
                }
            }
            composable(NavDestination.Common.CreateUser.route) {
                CreateUserScreen(onNavigateBack = { navController.popBackStack() })
            }
        }
    }
}