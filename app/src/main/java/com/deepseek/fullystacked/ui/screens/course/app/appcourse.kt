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
import com.deepseek.fullystacked.navigation.ROUTE_APP
import com.deepseek.fullystacked.navigation.ROUTE_COURSE
import com.deepseek.fullystacked.ui.theme.FullyStackedTheme

// ─────────────────────────────────────────────────────────────────────────────
// Colours (dark-mode safe)
// ─────────────────────────────────────────────────────────────────────────────
private val Purple700 = Color(0xFF3730A3)
private val Purple500 = Color(0xFF7C74F0)
private val Purple300 = Color(0xFFB4AEFF)
private val Purple100 = Color(0xFF3D3870)
private val Purple50  = Color(0xFF1E1B3A)
private val BgDark    = Color(0xFF0F0E1A)
private val Accent    = Color(0xFF9D97F8)

private data class TechItem(val emoji: String, val name: String, val desc: String, val color: Color)
private val appTechStack = listOf(
    TechItem("🟣", "Kotlin",          "Primary language",    Purple500),
    TechItem("🎨", "Jetpack Compose", "Modern Android UI",   Color(0xFF1A6FD4)),
    TechItem("🧱", "Ktor",            "Backend REST APIs",   Color(0xFF0F766E)),
    TechItem("🗄️", "Room ORM",        "Local database",      Color(0xFFEF9F27)),
    TechItem("🔄", "Coroutines",      "Async & concurrency", Color(0xFF7C3AED)),
    TechItem("🔥", "Firebase",        "Auth & cloud",        Color(0xFFEA580C)),
)

private val appSkills = listOf(
    "MVVM Architecture", "Navigation Component", "Dependency Injection",
    "REST APIs", "Unit Testing", "CI/CD", "Material Design 3", "State Management"
)

@Composable
fun AppScreen(navController: NavController) {

    val infiniteTransition = rememberInfiniteTransition(label = "glow")
    val glowScale by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 1.12f,
        animationSpec = infiniteRepeatable(tween(1800, easing = EaseInOutSine), RepeatMode.Reverse),
        label = "glowScale"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        // ── Hero ──────────────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .background(Brush.radialGradient(
                    colors = listOf(Color(0xFF1E1B3A), BgDark), radius = 900f))
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val step = 44.dp.toPx()
                val lc = Color.White.copy(alpha = 0.03f)
                var x = 0f
                while (x < size.width) {
                    drawLine(lc, androidx.compose.ui.geometry.Offset(x, 0f),
                        androidx.compose.ui.geometry.Offset(x, size.height), 1f); x += step
                }
                var y = 0f
                while (y < size.height) {
                    drawLine(lc, androidx.compose.ui.geometry.Offset(0f, y),
                        androidx.compose.ui.geometry.Offset(size.width, y), 1f); y += step
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 48.dp, start = 16.dp)
                    .size(36.dp).clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.12f))
                    .clickable { navController.popBackStack() },
                contentAlignment = Alignment.Center
            ) { Text("←", fontSize = 16.sp, color = Color.White) }

            Column(modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Box(contentAlignment = Alignment.Center) {
                    Box(modifier = Modifier.size(90.dp).scale(glowScale).background(
                        Brush.radialGradient(listOf(Purple500.copy(0.3f), Color.Transparent)),
                        RoundedCornerShape(26.dp)))
                    Box(modifier = Modifier.size(68.dp).background(
                        Brush.linearGradient(listOf(Purple700, Accent)),
                        RoundedCornerShape(20.dp)),
                        contentAlignment = Alignment.Center) {
                        Text("📱", fontSize = 30.sp)
                    }
                }
                Spacer(Modifier.height(14.dp))
                Text("Android Development", fontSize = 22.sp,
                    fontWeight = FontWeight.Bold, color = Color.White)
                Text("Build real Android apps with Kotlin", fontSize = 13.sp,
                    color = Purple300, modifier = Modifier.padding(top = 4.dp))
            }

            Row(modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                AppStatChip("12", "Lessons")
                AppStatChip("4",  "Modules")
                AppStatChip("6h", "Content")
            }
        }

        Spacer(Modifier.height(24.dp))

        // ── What you'll build ─────────────────────────────────────────────
        AppSectionLabel("What you'll build")
        Spacer(Modifier.height(10.dp))
        Column(modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(
                "📱 A production-ready Android app",
                "🔐 Firebase auth & user accounts",
                "🌐 REST API integration with Ktor",
                "🗄️ Offline-first database with Room",
                "🧭 Multi-screen navigation flows",
                "🎨 Custom Jetpack Compose UI"
            ).forEach { Text(it, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 22.sp) }
        }

        Spacer(Modifier.height(24.dp))

        // ── Tech stack ────────────────────────────────────────────────────
        AppSectionLabel("Tech stack")
        Spacer(Modifier.height(10.dp))
        Column(modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
            appTechStack.chunked(2).forEach { row ->
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()) {
                    row.forEach { AppTechCard(it, Modifier.weight(1f)) }
                    if (row.size == 1) Spacer(Modifier.weight(1f))
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // ── Skills ────────────────────────────────────────────────────────
        AppSectionLabel("Skills you'll gain")
        Spacer(Modifier.height(10.dp))
        Column(modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
            appSkills.chunked(3).forEach { rowSkills ->
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    rowSkills.forEach { skill ->
                        Box(modifier = Modifier
                            .background(Purple50, RoundedCornerShape(20.dp))
                            .border(0.5.dp, Purple100, RoundedCornerShape(20.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)) {
                            Text(skill, fontSize = 12.sp, color = Purple300,
                                fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(32.dp))

        // ── CTA → CourseScreen ────────────────────────────────────────────
        Button(
            onClick = { navController.navigate("$ROUTE_COURSE/$ROUTE_APP") },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Purple500)
        ) {
            Text("Start Android Track  →", fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold, color = Color.White)
        }

        Spacer(Modifier.height(32.dp))
    }
}

@Composable private fun AppStatChip(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Text(label, fontSize = 11.sp, color = Purple300)
    }
}

@Composable private fun AppSectionLabel(text: String) {
    Text(text, fontSize = 15.sp, fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(horizontal = 16.dp))
}

@Composable private fun AppTechCard(tech: TechItem, modifier: Modifier = Modifier) {
    Row(modifier = modifier.clip(RoundedCornerShape(12.dp))
        .background(tech.color.copy(alpha = 0.10f))
        .border(0.5.dp, tech.color.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
        .padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(tech.emoji, fontSize = 20.sp)
        Spacer(Modifier.width(8.dp))
        Column {
            Text(tech.name, fontSize = 12.sp, fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface)
            Text(tech.desc, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppScreenPreview() {
    FullyStackedTheme { AppScreen(navController = rememberNavController()) }
}
