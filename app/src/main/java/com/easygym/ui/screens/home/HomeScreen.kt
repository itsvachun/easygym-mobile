package com.easygym.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.easygym.ui.components.PrimaryButton

data class BottomItem(
    val label: String,
    val icon: ImageVector
)

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Home Screen",
                    style = MaterialTheme.typography.headlineLarge
                )

                PrimaryButton(text = "Logout", onClick = viewModel::logout)
            }
        }
    }
}