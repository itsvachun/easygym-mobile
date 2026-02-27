package com.easygym.ui.navigation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
            .background(MaterialTheme.colorScheme.surface)
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 5.dp)
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomDestinations.forEachIndexed { index, item ->

                val isSelected = index == pagerState.currentPage

                val scale by animateFloatAsState(
                    targetValue = if (isSelected) 1.2f else 1f,
                    animationSpec = spring(
                        dampingRatio = 0.5f,
                        stiffness = 400f
                    ),
                    label = "icon_scale_$index"
                )

                val indicatorSize by animateDpAsState(
                    targetValue = if (isSelected) 6.dp else 0.dp,
                    animationSpec = spring(
                        dampingRatio = 0.6f,
                        stiffness = 500f
                    ),
                    label = "indicator_size_$index"
                )

                val tint = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1F)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) {
                            scope.launch {
                                pagerState.scrollToPage(index)
                            }
                        }
                        .padding(vertical = 8.dp)
                ) {

                    Icon(
                        imageVector = ImageVector.vectorResource(item.iconId),
                        contentDescription = item.label,
                        tint = tint,
                        modifier = Modifier
                            .size(24.dp)
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                            }
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = item.label,
                        color = tint,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Box(
                        modifier = Modifier
                            .size(indicatorSize)
                            .background(
                                color = if (isSelected) tint else Color.Transparent,
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}