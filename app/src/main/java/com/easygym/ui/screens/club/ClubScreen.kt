package com.easygym.ui.screens.club

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.easygym.ui.components.PrimaryButton
import com.easygym.ui.components.SecondaryButton

@Composable
fun ClubScreen(
    viewModel: ClubViewModel = hiltViewModel(),
    onNavigateToChangePassword: () -> Unit,
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Text("Club Screen")
            SecondaryButton(text = "Cambia Password", onClick = onNavigateToChangePassword)
            PrimaryButton(text = "Logout", onClick = viewModel::logout)
        }
    }
}