package com.easygym.ui.screens.athletes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.easygym.domain.model.MedicalStatus
import com.easygym.ui.components.EasyGymSearchField
import com.easygym.ui.theme.LocalColors

@Composable
fun AthletesScreen(
    viewModel: AthletesViewModel = hiltViewModel(),
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
                state.pagination.isLoading -> CircularProgressIndicator()
                else -> {
                    Spacer(Modifier.height(8.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth()
                                .padding(vertical = 8.dp),
                            text = "Atleti",
                            style = MaterialTheme.typography.headlineLarge
                        )

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
                                TextButton(
                                    onClick = { viewModel.loadNextPage() }
                                ) {
                                    Text("Riprova")
                                }
                            }
                        }

                        LazyColumn {
                            items(state.pagination.items.size) { index ->
                                val athlete = state.pagination.items[index]

                                if (index >= state.pagination.items.size - 1 && !state.pagination.isFetchingNextPage && state.pagination.errorMessage == null) {
                                    viewModel.loadNextPage()
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row {
                                        Box(
                                            modifier = Modifier
                                                .size(40.dp)
                                                .background(
                                                    color = LocalColors.current.blueDim.copy(alpha = 0.6f),
                                                    shape = MaterialTheme.shapes.small
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "${athlete.firstName.first()}${athlete.lastName.first()}",
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
                                                    athlete.firstName,
                                                    style = MaterialTheme.typography.titleLarge
                                                )
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text(
                                                    athlete.lastName,
                                                    style = MaterialTheme.typography.titleLarge
                                                )
                                            }

                                            Text(
                                                athlete.email,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                    }

                                    Box(
                                        modifier = Modifier
                                            .background(
                                                color = when (athlete.medicalStatus) {
                                                    MedicalStatus.VALID -> LocalColors.current.greenDim.copy(alpha = 0.3f)
                                                    MedicalStatus.EXPIRING -> LocalColors.current.amberDim.copy(alpha = 0.3f)
                                                    MedicalStatus.EXPIRED -> LocalColors.current.redDim.copy(alpha = 0.3f)
                                                },
                                                shape = MaterialTheme.shapes.small
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            modifier = Modifier.padding(6.dp),
                                            text = athlete.medicalStatus.label,
                                            color = LocalColors.current.text,
                                            style = MaterialTheme.typography.labelLarge
                                        )
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