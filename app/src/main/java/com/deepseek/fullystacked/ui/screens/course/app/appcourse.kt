package com.deepseek.fullystacked.ui.screens.course.app

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.FlowRow

import com.deepseek.fullystacked.navigation.ROUTE_COURSE

// Colors
private val Purple700  = Color(0xFF3730A3)
private val Purple500  = Color(0xFF534AB7)
private val Purple300  = Color(0xFF8B83E0)
private val Purple100  = Color(0xFFCECBF6)
private val Purple50   = Color(0xFFEEEDFE)
private val BgDark     = Color(0xFF0F0E1A)
private val Accent     = Color(0xFF7C6FF7)

// Data
private data class TechItem(val emoji: String, val name: String, val desc: String, val color: Color)

private val appTechStack = listOf(
    TechItem("🟣", "Kotlin", "Primary language", Purple500),
    TechItem("🎨", "Jetpack Compose","Modern Android UI", Color(0xFF1A6FD4)),
    TechItem("🧱", "Ktor","Backend REST APIs", Color(0xFF0F766E)),
    TechItem("🗄️", "Room ORM","Local database", Color(0xFFEF9F27)),
    TechItem("🔄", "Coroutines","Async & concurrency", Color(0xFF7C3AED)),
    TechItem("🔥", "Firebase","Auth & cloud storage", Color(0xFFEA580C)),
)

private val appSkills = listOf(
    "MVVM Architecture", "Navigation Component",
    "Dependency Injection", "REST APIs",
    "Unit Testing", "CI/CD",
    "Material Design 3", "State Management"
)

@Composable
fun AppScreen(navController: NavController) {

    val infiniteTransition = rememberInfiniteTransition(label = "glow")
    val glowScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.12f,
        animationSpec = infiniteRepeatable(
            tween(1800, easing = EaseInOutSine),
            RepeatMode.Reverse
        ),
        label = "glowScale"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {

        // Hero Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color(0xFF1E1B3A), BgDark),
                        radius = 900f
                    )
                )
        ) {

            // Background grid
            Canvas(modifier = Modifier.fillMaxSize()) {
                val step = 44.dp.toPx()
                val lineColor = Color.White.copy(alpha = 0.03f)

                var x = 0f
                while (x < size.width) {
                    drawLine(lineColor, androidx.compose.ui.geometry.Offset(x, 0f),
                        androidx.compose.ui.geometry.Offset(x, size.height), 1f)
                    x += step
                }

                var y = 0f
                while (y < size.height) {
                    drawLine(lineColor, androidx.compose.ui.geometry.Offset(0f, y),
                        androidx.compose.ui.geometry.Offset(size.width, y), 1f)
                    y += step
                }
            }

            // Back button
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 48.dp, start = 16.dp)
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.12f))
                    .clickable { navController.popBackStack() },
                contentAlignment = Alignment.Center
            ) {
                Text("←", fontSize = 16.sp, color = Color.White)
            }

            // Center content
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .scale(glowScale)
                            .background(
                                Brush.radialGradient(
                                    listOf(Purple500.copy(alpha = 0.3f), Color.Transparent)
                                ),
                                RoundedCornerShape(26.dp)
                            )
                    )

                    Box(
                        modifier = Modifier
                            .size(68.dp)
                            .background(
                                Brush.linearGradient(listOf(Purple700, Accent)),
                                RoundedCornerShape(20.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("📱", fontSize = 30.sp)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Android Development",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = "Build real Android apps with Kotlin",
                    fontSize = 13.sp,
                    color = Purple300
                )
            }

            // Stats
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                StatChip("12", "Lessons")
                StatChip("4", "Modules")
                StatChip("6h", "Content")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionLabel("What you'll build")

        Column(modifier = Modifier.padding(16.dp)) {
            listOf(
                "📱 A production-ready Android app",
                "🔐 Firebase auth & user accounts",
                "🌐 REST API integration with Ktor",
                "🗄️ Offline-first database with Room",
                "🧭 Multi-screen navigation flows",
                "🎨 Custom Jetpack Compose UI"
            ).forEach {
                Text(it, fontSize = 14.sp)
            }
        }

        SectionLabel("Tech stack")

        Column(modifier = Modifier.padding(16.dp)) {
            appTechStack.chunked(2).forEach { row ->
                Row {
                    row.forEach { tech ->
                        TechCard(tech, Modifier.weight(1f))
                    }
                }
            }
        }

        SectionLabel("Skills you'll gain")

        FlowRow(modifier = Modifier.padding(16.dp)) {
            appSkills.forEach {
                Box(
                    modifier = Modifier
                        .background(Purple50, RoundedCornerShape(20.dp))
                        .padding(8.dp)
                ) {
                    Text(it, fontSize = 12.sp)
                }
            }
        }

        Button(
            onClick = { navController.navigate("$ROUTE_COURSE/app") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Start Android Track →")
        }
    }
}

@Composable
private fun StatChip(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontWeight = FontWeight.Bold, color = Color.White)
        Text(label, fontSize = 12.sp)
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Composable
private fun TechCard(tech: TechItem, modifier: Modifier = Modifier) {
    Row(modifier = modifier.padding(8.dp)) {
        Text(tech.emoji)
        Column {
            Text(tech.name, fontWeight = FontWeight.Bold)
            Text(tech.desc, fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Preview
@Composable
fun PreviewApp() {
    AppScreen(rememberNavController())
}