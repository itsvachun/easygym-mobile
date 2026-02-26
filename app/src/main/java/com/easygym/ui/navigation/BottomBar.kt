package com.easygym.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun BottomBar(
    bottomDestinations: List<NavDestination.BottomBar>,
    pagerState: PagerState
) {
    val scope = rememberCoroutineScope()

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
            bottomDestinations.forEachIndexed { index, item ->

                val isSelected = index == pagerState.currentPage
                val tint = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) {
                            scope.launch {
                                pagerState.scrollToPage(index)
                            }
                        }
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
                                    color = tint,
                                    shape = CircleShape
                                )
                        )
                    }
                }
            }
        }
    }
}

