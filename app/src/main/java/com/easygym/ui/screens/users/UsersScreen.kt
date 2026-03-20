package com.easygym.ui.screens.users

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.easygym.ui.components.EasyGymFAB
import com.easygym.ui.components.EasyGymSearchField
import com.easygym.ui.components.PrimaryFilterButton
import com.easygym.ui.components.SecondaryFilterButton
import com.easygym.ui.navigation.NavDestination
import com.easygym.ui.navigation.UserRole
import com.easygym.ui.theme.LocalColors

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UsersScreen(
    viewModel: UsersViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    navController: NavHostController

) {
    val state by viewModel.state.collectAsState()

    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
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
                EasyGymSearchField(
                    query = state.search,
                    onQueryChange = viewModel::onSearchChanged,
                    placeholder = "Cerca per nome",
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Box(modifier = Modifier.weight(1f)) {
                        if (state.selectedRole == null) {
                            PrimaryFilterButton(
                                text = "Tutti",
                                onClick = { }
                            )
                        } else {
                            SecondaryFilterButton(
                                text = "Tutti",
                                onClick = { viewModel.onRoleSelected(null) }
                            )
                        }
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        if (state.selectedRole == UserRole.COACH) {
                            PrimaryFilterButton(
                                text = "Coach",
                                onClick = { }
                            )
                        } else {
                            SecondaryFilterButton(
                                text = "Coach",
                                onClick = { viewModel.onRoleSelected(UserRole.COACH) }
                            )
                        }
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        if (state.selectedRole == UserRole.ATHLETE) {
                            PrimaryFilterButton(
                                text = "Atleti",
                                onClick = { }
                            )
                        } else {
                            SecondaryFilterButton(
                                text = "Atleti",
                                onClick = { viewModel.onRoleSelected(UserRole.ATHLETE) }
                            )
                        }
                    }
                }

                when {
                    state.isLoading -> CircularProgressIndicator()
                    else -> Box(
                        modifier = Modifier
                            .padding(6.dp)
                            .fillMaxSize(),
                    ) {
                        state.errorMessage?.let { errorMessage ->
                            Text(errorMessage)
                        }

                        LazyColumn {
                            items(state.filteredUsers.size) { index ->
                                val user = state.filteredUsers[index]

                                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        Box(
                                            modifier = Modifier
                                                .size(40.dp)
                                                .background(
                                                    color = LocalColors.current.redDim,
                                                    shape = MaterialTheme.shapes.small
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "${user.firstName.first()}${user.lastName.first()}",
                                                color = LocalColors.current.text,
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
}