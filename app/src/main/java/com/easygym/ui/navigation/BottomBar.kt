package com.easygym.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun BottomBar(
    navController: NavController,
    viewModel: BottomBarViewModel = hiltViewModel()
) {
    val bottomBarState by viewModel.state.collectAsState()

    LaunchedEffect(bottomBarState.bottomDestinations) {
        if (bottomBarState.bottomDestinations.isNotEmpty()) {
            navController.navigate(bottomBarState.bottomDestinations.first().route)
        } else {
            navController.navigate(NavDestination.Common.LOGIN.route)
        }
    }

    if (bottomBarState.bottomDestinations.isNotEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.navigationBars)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                bottomBarState.bottomDestinations.forEachIndexed { index, item ->

                    val isSelected = index == bottomBarState.selectedIndex
                    val tint = if (isSelected) Color.Red else Color.Gray

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                            ) { viewModel.updateSelectedIndex(index) }
                            .padding(horizontal = 12.dp)
                    ) {

                        Icon(
                            imageVector = ImageVector.vectorResource(item.iconId),
                            contentDescription = item.label,
                            tint = tint,
                            modifier = Modifier.size(24.dp)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = item.label,
                            color = tint,
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .background(
                                        color = Color.Red,
                                        shape = CircleShape
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}

