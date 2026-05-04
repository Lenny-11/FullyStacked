package com.deepseek.fullystacked.ui.screens.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.deepseek.fullystacked.navigation.ROUTE_TRACK

private val Purple500 = Color(0xFF534AB7)
private val Purple50  = Color(0xFFEEEDFE)
private val Purple100 = Color(0xFFCECBF6)

@Composable
fun OnboardingScreen(
    navController: NavController,
    userName: String = "there"
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(72.dp))

        AnimatedVisibility(visible = visible, enter = fadeIn() + slideInVertically()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Logo
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(Purple50, RoundedCornerShape(18.dp))
                        .border(0.5.dp, Purple100, RoundedCornerShape(18.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(Purple500, RoundedCornerShape(9.dp))
                    )
                }

                Spacer(Modifier.height(20.dp))

                Text(
                    "Welcome, $userName! 🎉",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                Text(
                    "You're all set. Choose your learning path to get started.",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)
                )

                Spacer(Modifier.height(48.dp))

                Text(
                    "What do you want to build?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(16.dp))

                // App dev card
                OnboardingCard(
                    emoji = "📱",
                    title = "Android Development",
                    subtitle = "Kotlin, Jetpack Compose, Android SDK",
                    onClick = {
                        navController.navigate(ROUTE_TRACK) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )

                Spacer(Modifier.height(14.dp))

                // Web dev card
                OnboardingCard(
                    emoji = "🌐",
                    title = "Web Development",
                    subtitle = "HTML, CSS, JavaScript, React",
                    onClick = {
                        navController.navigate(ROUTE_TRACK) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )

                Spacer(Modifier.height(14.dp))

                // Backend card
                OnboardingCard(
                    emoji = "⚙️",
                    title = "Backend Development",
                    subtitle = "Node.js, APIs, Databases",
                    onClick = {
                        navController.navigate(ROUTE_TRACK) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun OnboardingCard(
    emoji: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Purple50, RoundedCornerShape(16.dp))
            .border(1.dp, Purple100, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(emoji, fontSize = 28.sp)
            Spacer(Modifier.width(14.dp))
            Column {
                Text(title, fontSize = 15.sp, fontWeight = FontWeight.Medium)
                Text(
                    subtitle,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
