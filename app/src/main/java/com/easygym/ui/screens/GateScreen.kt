package com.easygym.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.easygym.ui.navigation.AuthNavGraph
import com.easygym.ui.navigation.UnAuthNavGraph

@Composable
fun GateScreen(viewModel: GateViewModel = hiltViewModel()) {

    val gateState = viewModel.state.collectAsState()
    val navController = rememberNavController()

    when (gateState.value) {
        true -> AuthNavGraph(navController)
        false -> UnAuthNavGraph(navController)
    }

}