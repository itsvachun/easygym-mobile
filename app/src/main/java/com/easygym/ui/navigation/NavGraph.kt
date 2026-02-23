package com.easygym.ui.navigation

import androidx.compose.runtime.Composable
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

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo(0)
                    }
                }
            )
        }

        composable("home") {
            HomeScreen(
                onLogoutClick = {
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                }
            )
        }
    }
}