package com.easygym.ui.screens.users

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
import com.easygym.ui.components.EasyGymFAB
import com.easygym.ui.components.EasyGymFilterButton
import com.easygym.ui.components.EasyGymSearchField
import com.easygym.ui.theme.LocalColors
import com.easygym.utils.enums.UserRole

@Composable
fun UsersScreen(
    viewModel: UsersViewModel = hiltViewModel(),
    onNavigateToUserCreate: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
                .padding(horizontal = 18.dp),
            contentAlignment = Alignment.TopCenter
        ) {

            when {
                state.pagination.isLoading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )

                else -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        )
                        {
                            Text(
                                text = "Utenti",
                                style = MaterialTheme.typography.headlineLarge
                            )

                            EasyGymFAB(
                                hasShadow = true,
                                onClick = onNavigateToUserCreate,
                            )
                        }
                        EasyGymSearchField(
                            query = state.pagination.search,
                            onQueryChange = viewModel::onSearchChanged,
                            placeholder = "Cerca per nome",
                            modifier = Modifier.fillMaxWidth()
                        )

                        state.pagination.errorMessage?.let { errorMessage ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = errorMessage,
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                androidx.compose.material3.TextButton(
                                    onClick = { viewModel.loadNextPage() }
                                ) {
                                    Text("Riprova")
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {

                            Box(modifier = Modifier.weight(1f)) {
                                EasyGymFilterButton(
                                    text = "Tutti",
                                    selected = state.selectedRole == null,
                                    onClick = { viewModel.onRoleSelected(null) }
                                )

                            }

                            Box(modifier = Modifier.weight(1f)) {
                                EasyGymFilterButton(
                                    text = "Allenatori",
                                    selected = state.selectedRole == UserRole.COACH,
                                    onClick = { viewModel.onRoleSelected(UserRole.COACH) }
                                )

                            }

                            Box(modifier = Modifier.weight(1f)) {
                                EasyGymFilterButton(
                                    text = "Atleti",
                                    selected = state.selectedRole == UserRole.ATHLETE,
                                    onClick = { viewModel.onRoleSelected(UserRole.ATHLETE) }
                                )

                            }
                        }

                        LazyColumn {
                            items(state.pagination.items.size) { index ->
                                val user = state.pagination.items[index]

                                if (index >= state.pagination.items.size - 1 &&
                                    !state.pagination.isFetchingNextPage &&
                                    !state.pagination.isLastPage &&
                                    state.pagination.errorMessage == null
                                ) {
                                    viewModel.loadNextPage()
                                }

                                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        Box(
                                            modifier = Modifier
                                                .size(40.dp)
                                                .background(
                                                    color = when (user.role) {
                                                        UserRole.COACH -> LocalColors.current.purpleDim.copy(
                                                            alpha = 0.6f
                                                        )

                                                        UserRole.ATHLETE -> LocalColors.current.blueDim.copy(
                                                            alpha = 0.6f
                                                        )

                                                        else -> LocalColors.current.redDim
                                                    },
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

                                                Text(
                                                    user.firstName,
                                                    style = MaterialTheme.typography.titleLarge
                                                )
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text(user.lastName, style = MaterialTheme.typography.titleLarge)
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text(
                                                    user.role.label,
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                            }

                                            Text(
                                                user.email ?: "Email non disponibile",
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                    }
                                }
                            }

                            if (state.pagination.isFetchingNextPage) {
                                item {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
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