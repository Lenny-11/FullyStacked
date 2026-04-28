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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.deepseek.fullystacked.navigation.ROUTE_COURSE

// ─────────────────────────────────────────────────────────────────────────────
// Colours
// ─────────────────────────────────────────────────────────────────────────────
private val Purple700  = Color(0xFF3730A3)
private val Purple500  = Color(0xFF534AB7)
private val Purple300  = Color(0xFF8B83E0)
private val Purple100  = Color(0xFFCECBF6)
private val Purple50   = Color(0xFFEEEDFE)
private val BgDark     = Color(0xFF0F0E1A)
private val Accent     = Color(0xFF7C6FF7)

// ─────────────────────────────────────────────────────────────────────────────
// Tech stack items shown on the screen
// ─────────────────────────────────────────────────────────────────────────────
private data class TechItem(val emoji: String, val name: String, val desc: String, val color: Color)

private val appTechStack = listOf(
    TechItem("🟣", "Kotlin",         "Primary language",          Purple500),
    TechItem("🎨", "Jetpack Compose","Modern Android UI",         Color(0xFF1A6FD4)),
    TechItem("🧱", "Ktor",           "Backend REST APIs",         Color(0xFF0F766E)),
    TechItem("🗄️", "Room ORM",       "Local database",            Color(0xFFEF9F27)),
    TechItem("🔄", "Coroutines",     "Async & concurrency",       Color(0xFF7C3AED)),
    TechItem("🔥", "Firebase",       "Auth & cloud storage",      Color(0xFFEA580C)),
)

private data class SkillChip(val label: String)
private val appSkills = listOf(
    SkillChip("MVVM Architecture"), SkillChip("Navigation Component"),
    SkillChip("Dependency Injection"), SkillChip("REST APIs"),
    SkillChip("Unit Testing"), SkillChip("CI/CD"),
    SkillChip("Material Design 3"), SkillChip("State Management"),
)

// ─────────────────────────────────────────────────────────────────────────────
// AppScreen
// ─────────────────────────────────────────────────────────────────────────────
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
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color(0xFF1E1B3A), BgDark),
                        radius = 900f
                    )
                )
        ) {
            // Subtle grid
            Canvas(modifier = Modifier.fillMaxSize()) {
                val step = 44.dp.toPx()
                val lineColor = Color.White.copy(alpha = 0.03f)
                var x = 0f
                while (x < size.width) {
                    drawLine(lineColor, androidx.compose.ui.geometry.Offset(x, 0f),
                        androidx.compose.ui.geometry.Offset(x, size.height), strokeWidth = 1f)
                    x += step
                }
                var y = 0f
                while (y < size.height) {
                    drawLine(lineColor, androidx.compose.ui.geometry.Offset(0f, y),
                        androidx.compose.ui.geometry.Offset(size.width, y), strokeWidth = 1f)
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

            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Glowing logo
                Box(contentAlignment = Alignment.Center) {
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .scale(glowScale)
                            .background(
                                Brush.radialGradient(
                                    listOf(Purple500.copy(alpha = 0.3f), Color.Transparent)
                                ),
                                shape = RoundedCornerShape(26.dp)
                            )
                    )
                    Box(
                        modifier = Modifier
                            .size(68.dp)
                            .background(
                                Brush.linearGradient(listOf(Purple700, Accent)),
                                shape = RoundedCornerShape(20.dp)
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
                    color = Purple300,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Stats row
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

        // ── What you'll learn ─────────────────────────────────────────────
        SectionLabel("What you'll build")
        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(
                "📱 A production-ready Android app",
                "🔐 Firebase auth & user accounts",
                "🌐 REST API integration with Ktor",
                "🗄️ Offline-first database with Room",
                "🧭 Multi-screen navigation flows",
                "🎨 Custom Jetpack Compose UI"
            ).forEach { item ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(item, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 22.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ── Tech stack ────────────────────────────────────────────────────
        SectionLabel("Tech stack")
        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            appTechStack.chunked(2).forEach { row ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    row.forEach { tech ->
                        TechCard(tech = tech, modifier = Modifier.weight(1f))
                    }
                    if (row.size == 1) Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ── Skills ────────────────────────────────────────────────────────
        SectionLabel("Skills you'll gain")
        Spacer(modifier = Modifier.height(10.dp))

        FlowRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            appSkills.forEach { skill ->
                Box(
                    modifier = Modifier
                        .background(Purple50, RoundedCornerShape(20.dp))
                        .border(0.5.dp, Purple100, RoundedCornerShape(20.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(skill.label, fontSize = 12.sp, color = Purple500, fontWeight = FontWeight.Medium)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // ── CTA ───────────────────────────────────────────────────────────
        Button(
            onClick = { navController.navigate("$ROUTE_COURSE/app") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple500
            )
        ) {
            Text(
                text = "Start Android Track  →",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Shared sub-components
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun StatChip(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Text(label, fontSize = 11.sp, color = Purple300)
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        fontSize = 15.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Composable
private fun TechCard(tech: TechItem, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(tech.color.copy(alpha = 0.08f))
            .border(0.5.dp, tech.color.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(tech.emoji, fontSize = 20.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(tech.name, fontSize = 12.sp, fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface)
            Text(tech.desc, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Preview
// ─────────────────────────────────────────────────────────────────────────────
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppScreenPreview() {
    MaterialTheme { AppScreen(navController = rememberNavController()) }
}