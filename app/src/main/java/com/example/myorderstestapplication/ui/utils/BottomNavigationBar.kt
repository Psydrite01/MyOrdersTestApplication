package com.example.myorderstestapplication.ui.utils

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myorderstestapplication.navigation.Screen
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Payment
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale



private val YellowPrimary = Color(0xFFfec803)
private val NavGray       = Color(0xFF0a0a0a)
private val NavBg         = Color.White

data class BottomNavItem(
    val label: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val items = listOf(
        BottomNavItem("Home",     Screen.Home.route,     Icons.Filled.Home,          Icons.Outlined.Home),
        BottomNavItem("Orders",   Screen.Orders.route,   Icons.Filled.AccessTime,  Icons.Outlined.AccessTime),
        BottomNavItem("Payments", Screen.Payments.route, Icons.Filled.Payment,  Icons.Outlined.Payment),
        BottomNavItem("Account",  Screen.Account.route,  Icons.Filled.AccountCircle, Icons.Outlined.AccountCircle)
    )

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 12.dp,
        color = NavBg
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .height(78.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = currentRoute == item.route
                BottomNavItemView(
                    item       = item,
                    isSelected = isSelected,
                    onClick    = {
                        if (currentRoute != item.route) {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState    = true
                            }
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun BottomNavItemView(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // ── Animated icon scale: small bounce when selected ───────────────────────
    val scale by animateFloatAsState(
        targetValue   = if (isSelected) 1.18f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness    = Spring.StiffnessMedium
        ),
        label = "iconScale"
    )

    // ── Animated tint color ───────────────────────────────────────────────────
    val iconTint by animateColorAsState(
        targetValue   = if (isSelected) YellowPrimary else NavGray,
        animationSpec = tween(durationMillis = 200),
        label         = "iconTint"
    )
    val labelColor by animateColorAsState(
        targetValue   = if (isSelected) YellowPrimary else NavGray,
        animationSpec = tween(durationMillis = 200),
        label         = "labelColor"
    )


    Column(
        modifier = modifier
            .fillMaxHeight()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication        = null,   // no ripple — looks cleaner on a bottom bar
                onClick           = onClick
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Pill indicator behind icon
        Box(contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .height(28.dp)
                    .width(48.dp)
                    .clip(RoundedCornerShape(14.dp))
            )
            Icon(
                imageVector     = if (isSelected) item.selectedIcon else item.unselectedIcon,
                contentDescription = item.label,
                tint            = iconTint,
                modifier        = Modifier
                    .size(24.dp)
                    .scale(scale)
            )
        }

        Spacer(Modifier.height(2.dp))

        Text(
            text       = item.label,
            fontSize   = 11.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            color      = labelColor
        )
    }
}