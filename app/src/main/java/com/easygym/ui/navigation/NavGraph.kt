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
import androidx.navigation.compose.rememberNavController
import com.easygym.ui.screens.athletes.AthletesScreen
import com.easygym.ui.screens.calendar.CalendarScreen
import com.easygym.ui.screens.changepassword.ChangePasswordScreen
import com.easygym.ui.screens.createuser.CreateUserScreen
import com.easygym.ui.screens.home.HomeScreen
import com.easygym.ui.screens.loadinggate.LoadingGateScreen
import com.easygym.ui.screens.login.LoginScreen
import com.easygym.ui.screens.payments.PaymentsScreen
import com.easygym.ui.screens.profile.ClubScreen
import com.easygym.ui.screens.users.UsersScreen

@Composable
fun NavGraph(
    viewModel: NavViewModel = hiltViewModel()
) {
    val navState by viewModel.state.collectAsState()
    val navController = rememberNavController()

    val pagerState = rememberPagerState(pageCount = { navState.bottomDestinations.size })

    NavHost(
        navController = navController,
        startDestination = when {
            navState.isLoading -> NavDestination.Common.Loading.route
            navState.bottomDestinations.isNotEmpty() -> NavDestination.Common.Bottom.route
            else -> NavDestination.Common.Login.route
        },
    ) {
        composable(NavDestination.Common.Loading.route) {
            LoadingGateScreen()
        }
        composable(NavDestination.Common.Login.route) {
            LoginScreen()
        }
        composable(NavDestination.Common.ChangePassword.route) {
            ChangePasswordScreen()
        }
        composable(NavDestination.Common.Users.route) {
            UsersScreen {
                navController.navigate(NavDestination.Common.CreateUser.route)
            }
        }
        composable(NavDestination.Common.Bottom.route) {
            Scaffold(
                bottomBar = {
                    BottomBar(
                        bottomDestinations = navState.bottomDestinations,
                        pagerState = pagerState,
                    )
                }
            ) { padding ->
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                ) { page ->
                    when (navState.bottomDestinations[page].route) {
                        NavDestination.BottomBar.Home.route -> HomeScreen()
                        NavDestination.BottomBar.Athletes.route -> AthletesScreen()
                        NavDestination.BottomBar.Calendar.route -> CalendarScreen()
                        NavDestination.BottomBar.Payments.route -> PaymentsScreen()
                        NavDestination.BottomBar.Club.route -> ClubScreen(
                            onNavigateToChangePassword = {
                                navController.navigate(NavDestination.Common.ChangePassword.route)
                            },
                            onNavigateToUsers = {
                                navController.navigate(NavDestination.Common.Users.route)
                            }
                        )
                    }
                }
            }
        }
        composable(NavDestination.Common.CreateUser.route) {
            CreateUserScreen(onNavigateBack = { navController.popBackStack() })
        }
    }

}