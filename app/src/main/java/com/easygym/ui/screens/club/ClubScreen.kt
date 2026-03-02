package com.easygym.ui.screens.club

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.easygym.ui.components.PrimaryButton

@Composable
fun ClubScreen(viewModel: ClubViewModel = hiltViewModel()) {
    Box(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Club Screen")
        PrimaryButton(text = "Logout", onClick = viewModel::logout)
    }
}