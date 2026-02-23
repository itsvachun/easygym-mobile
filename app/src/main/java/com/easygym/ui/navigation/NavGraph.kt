package com.easygym.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.easygym.ui.screens.HomeScreen
import com.easygym.ui.screens.LoginScreen
import com.easygym.ui.screens.LoginViewModel

@Composable
fun NavGraph(loginViewModel: LoginViewModel = viewModel()) {

    val navController = rememberNavController()

    // Osserva lo stato di login dal ViewModel
    val isLoggedIn by loginViewModel.isLoggedIn.collectAsState()

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) "home" else "login"
    ) {

        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    loginViewModel.login()
                    navController.navigate("home") {
                        popUpTo(0)
                    }
                }
            )
        }

        composable("home") {
            HomeScreen(
                onLogoutClick = {
                    loginViewModel.logout()
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                }
            )
        }
    }
}