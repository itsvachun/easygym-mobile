package com.easygym.ui.screens.users

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.easygym.ui.components.EasyGymFAB
import com.easygym.ui.components.EasyGymTextField
import com.easygym.ui.components.PrimaryButton
import com.easygym.ui.components.SecondaryButton
import com.easygym.ui.navigation.NavDestination

@Composable
fun UsersScreen(
    viewModel: UsersViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    navController: NavHostController

) {

    val state by viewModel.state.collectAsState()

    var ricerca by remember { mutableStateOf("") }
    val selectedRole = state.selectedRole


    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(150.dp)
            )
            {
                Text(
                    text = "Utenti",
                    style = MaterialTheme.typography.headlineLarge
                )

                EasyGymFAB(
                    hasShadow = false,
                    onClick = { navController.navigate(NavDestination.Common.CreateUser.route) },

                    )
            }
            EasyGymTextField(
                value = ricerca,
                label = "",
                placeholder = "Cerca per nome",
                onValueChange = {}
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Box(modifier = Modifier.weight(1f)) {
                    if (selectedRole == "Tutti") {
                        PrimaryButton(
                            text = "Tutti",
                            onClick = { }
                        )
                    } else {
                        SecondaryButton(
                            text = "Tutti",
                            onClick = { viewModel.onRoleSelected("Tutti") }
                        )
                    }
                }

                Box(modifier = Modifier.weight(1f)) {
                    if (selectedRole == "Coach") {
                        PrimaryButton(
                            text = "Coach",
                            onClick = { }
                        )
                    } else {
                        SecondaryButton(
                            text = "Coach",
                            onClick = { viewModel.onRoleSelected("Coach") }
                        )
                    }
                }

                Box(modifier = Modifier.weight(1f)) {
                    if (selectedRole == "Atleti") {
                        PrimaryButton(
                            text = "Atleti",
                            onClick = { }
                        )
                    } else {
                        SecondaryButton(
                            text = "Atleti",
                            onClick = { viewModel.onRoleSelected("Atleti") }
                        )
                    }
                }
            }

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
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .background(
                                                color = MaterialTheme.colorScheme.primaryContainer,
                                                shape = MaterialTheme.shapes.small
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "${user.firstName.first()}${user.lastName.first()}",
                                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                                            fontSize = 16.sp
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Column(
                                        verticalArrangement = Arrangement.Center
                                    ) {

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {

                                            Text(user.firstName, style = MaterialTheme.typography.titleLarge)
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(user.lastName, style = MaterialTheme.typography.titleLarge)
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(user.role.toString(), style = MaterialTheme.typography.bodyMedium)
                                        }

                                        Text(
                                            user.email ?: "Email non disponibile",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }

                            }
                        }
                    }
                }
            }

        }
    }
}