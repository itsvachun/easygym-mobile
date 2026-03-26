package com.easygym.ui.screens.athletes

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
            contentAlignment = Alignment.Center
        ) {

            when {
                state.isLoading -> CircularProgressIndicator()
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
                            query = "",
                            onQueryChange = {},
                            placeholder = "Cerca per nome",
                            modifier = Modifier.fillMaxWidth()
                        )

                        LazyColumn {
                            item {
                                state.errorMessage?.let { errorMessage ->
                                    Text(errorMessage)
                                }
                            }

                            items(state.athletes.size) { index ->
                                val athlete = state.athletes[index]

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
                                                    color = LocalColors.current.redDim,
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
                                                    MedicalStatus.VALID -> LocalColors.current.greenDim
                                                    MedicalStatus.EXPIRING -> LocalColors.current.amberDim
                                                    MedicalStatus.EXPIRED -> LocalColors.current.redDim
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
                        }
                    }
                }
            }
        }
    }
}