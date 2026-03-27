package com.easygym.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.easygym.ui.components.PrimaryButton
import com.easygym.ui.components.SecondaryButton

@Composable
fun ClubScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onNavigateToChangePassword: () -> Unit,
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Text("Profile Screen", style = MaterialTheme.typography.displaySmall)
            Column {
                Text("Impostazioni Generali", style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(16.dp))
                SecondaryButton(text = "Cambia Password", onClick = onNavigateToChangePassword)
            }
            PrimaryButton(text = "Logout", onClick = viewModel::logout)
        }
    }
}