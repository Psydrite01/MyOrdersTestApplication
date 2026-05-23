package com.example.myorderstestapplication.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ─── Shared placeholder layout ────────────────────────────────────────────────
@Composable
private fun PlaceholderScreen(
    title: String,
    icon: ImageVector,
    accentColor: Color = Color(0xFFFFC107)
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(accentColor.copy(alpha = 0.15f),
                        androidx.compose.foundation.shape.CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = accentColor,
                    modifier = Modifier.size(40.dp)
                )
            }
            Text(
                text = title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A)
            )
            Text(
                text = "Coming soon",
                fontSize = 14.sp,
                color = Color(0xFF9E9E9E)
            )
        }
    }
}

// ─── Home ─────────────────────────────────────────────────────────────────────
@Composable
fun HomeScreen() {
    PlaceholderScreen(
        title = "Home",
        icon = Icons.Default.Home,
        accentColor = Color(0xFFFFC107)
    )
}

// ─── Payments ─────────────────────────────────────────────────────────────────
@Composable
fun PaymentsScreen() {
    PlaceholderScreen(
        title = "Payments",
        icon = Icons.Default.ShoppingCart,
        accentColor = Color(0xFF43A047)
    )
}

// ─── Account ──────────────────────────────────────────────────────────────────
@Composable
fun AccountScreen() {
    PlaceholderScreen(
        title = "Account",
        icon = Icons.Default.AccountCircle,
        accentColor = Color(0xFF1565C0)
    )
}