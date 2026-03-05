package com.easygym.ui.screens.athletes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AthletesScreen(
    viewModel: AthletesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    when {
        state.isLoading -> CircularProgressIndicator()
        else -> Box(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize(),
        ) {
            state.errorMessage?.let { errorMessage ->
                Text(errorMessage)
            }

            LazyColumn {
                items(state.users.size) { index ->
                    val user = state.users[index]

                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        Row {
                            Text(user.firstName, fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(user.lastName, fontSize = 20.sp)
                        }
                        Text(user.email ?: "Email non disponibile", fontSize = 20.sp)
                        Text(user.phone, fontSize = 20.sp)
                    }
                }
            }
        }
    }
}