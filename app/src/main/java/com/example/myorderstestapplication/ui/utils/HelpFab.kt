package com.example.myorderstestapplication.ui.utils

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val YellowPrimary = Color(0xFFFFC107)
private val TextDark      = Color(0xFF1A1A1A)

@Composable
fun HelpFab(onClick: () -> Unit = {}) {

    Box(
        modifier = Modifier
            .size(64.dp)
            .shadow(elevation = 6.dp, shape = CircleShape)
            .clip(CircleShape)
            .background(YellowPrimary)
            .clickable(
                onClick           = onClick
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector        = Icons.Filled.HeadsetMic,
                contentDescription = "Help",
                tint               = TextDark,
                modifier           = Modifier.size(22.dp)
            )
            Text(
                text       = "Help",
                fontSize   = 11.sp,
                fontWeight = FontWeight.Bold,
                color      = TextDark
            )
        }
    }
}