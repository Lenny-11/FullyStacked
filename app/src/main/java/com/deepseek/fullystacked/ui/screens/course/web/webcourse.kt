package com.deepseek.fullystacked.ui.screens.course.web

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.deepseek.fullystacked.navigation.ROUTE_COURSE
import com.deepseek.fullystacked.navigation.ROUTE_WEB
import com.deepseek.fullystacked.ui.screens.pdf.PdfUtils
import com.deepseek.fullystacked.ui.theme.FullyStackedTheme

// ─────────────────────────────────────────────────────────────────────────────
// Colours (dark-mode safe)
// ─────────────────────────────────────────────────────────────────────────────
private val Teal700   = Color(0xFF0D9488)
private val Teal500   = Color(0xFF2DD4BB)
private val Teal300   = Color(0xFF5EEAD4)
private val Teal100   = Color(0xFF1A4A42)   // dark chip border
private val Teal50    = Color(0xFF0D2420)   // dark chip bg
private val RedAccent = Color(0xFFE24B4A)
private val BgWarm    = Color(0xFF081210)

private data class WebTechItem(val emoji: String, val name: String, val desc: String, val color: Color)
private val webTechStack = listOf(
    WebTechItem("🧱", "HTML5",      "Semantic markup",         RedAccent),
    WebTechItem("🎨", "CSS3",       "Styles & animations",     Color(0xFF1A6FD4)),
    WebTechItem("⚡", "JavaScript", "Dynamic behaviour",       Color(0xFFD4A017)),
    WebTechItem("🐘", "PHP/MySQL",  "Server-side & databases", Color(0xFF4F7DC9)),
    WebTechItem("⚛️", "React JS",   "Component UI library",    Color(0xFF06B6D4)),
    WebTechItem("🟢", "Node.js",    "Backend runtime",         Color(0xFF1D9E75)),
)

private val webSkills = listOf(
    "Responsive Design", "REST APIs", "MVC Pattern", "jQuery",
    "Bootstrap", "MongoDB", "HTTP Protocol", "AJAX"
)

// PDF chapter page indices for web roadmap steps (0-based)
private val roadmapPdfPages = listOf(114, 14, 260, 114, 169, 236)

@Composable
fun WebScreen(navController: NavController) {
    val context = LocalContext.current

    val infiniteTransition = rememberInfiniteTransition(label = "glow")
    val glowScale by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 1.14f,
        animationSpec = infiniteRepeatable(tween(2000, easing = EaseInOutSine), RepeatMode.Reverse),
        label = "webGlow"
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
                .height(300.dp)
                .background(Brush.radialGradient(
                    colors = listOf(Color(0xFF0D2E2B), BgWarm), radius = 1000f))
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val spacing = 28.dp.toPx()
                var x = 0f
                while (x < size.width) {
                    var y = 0f
                    while (y < size.height) {
                        drawCircle(Color.White.copy(alpha = 0.04f), 1.5.dp.toPx(),
                            androidx.compose.ui.geometry.Offset(x, y))
                        y += spacing
                    }
                    x += spacing
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
                    Box(modifier = Modifier.size(96.dp).scale(glowScale).background(
                        Brush.radialGradient(listOf(Teal500.copy(0.35f), Color.Transparent)),
                        RoundedCornerShape(28.dp)))
                    Box(modifier = Modifier.size(72.dp).background(
                        Brush.linearGradient(listOf(Teal700, Teal500)),
                        RoundedCornerShape(22.dp)),
                        contentAlignment = Alignment.Center) {
                        Text("🌐", fontSize = 32.sp)
                    }
                }
                Spacer(Modifier.height(14.dp))
                Text("Web Development", fontSize = 22.sp,
                    fontWeight = FontWeight.Bold, color = Color.White)
                Text("From HTML basics to MEAN stack mastery", fontSize = 13.sp,
                    color = Teal300, modifier = Modifier.padding(top = 4.dp))
            }

            Row(modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                WebStatChip("12", "Lessons")
                WebStatChip("4",  "Modules")
                WebStatChip("8h", "Content")
            }
        }

        Spacer(Modifier.height(24.dp))

        // ── Roadmap ───────────────────────────────────────────────────────
        WebSectionLabel("Course roadmap")
        Spacer(Modifier.height(6.dp))

        // PDF hint banner
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Teal500.copy(alpha = 0.10f))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("📖", fontSize = 13.sp)
            Spacer(Modifier.width(6.dp))
            Text("Tap a roadmap step to open the PDF chapter", fontSize = 11.sp, color = Teal500)
        }

        Spacer(Modifier.height(12.dp))

        val steps = listOf(
            Triple("1. Front-End",     "HTML5 · CSS3 · Bootstrap · jQuery",  RedAccent),
            Triple("2. Foundation",    "OOP · Design Patterns · AJAX · JSON", Color(0xFF7C3AED)),
            Triple("3. PHP & MySQL",   "CRUD · MVC (Laravel) · DB Design",    Color(0xFF4F7DC9)),
            Triple("4. React JS",      "Components · Hooks · Routing · Auth", Color(0xFF06B6D4)),
            Triple("5. MEAN Stack",    "MongoDB · Express · Angular · Node",  Teal500),
            Triple("6. Data Exchange", "HTTP methods · Status codes · REST",  Color(0xFF1D9E75)),
        )
        Column(modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)) {
            steps.forEachIndexed { index, (title, subtitle, color) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            val page = roadmapPdfPages.getOrElse(index) { 114 }
                            PdfUtils.openPdfAtPage(context, page)
                        }
                        .padding(vertical = 4.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(32.dp)) {
                        Box(modifier = Modifier.size(28.dp).clip(CircleShape)
                            .background(color.copy(0.15f))
                            .border(1.dp, color, CircleShape),
                            contentAlignment = Alignment.Center) {
                            Text("${index + 1}", fontSize = 11.sp,
                                fontWeight = FontWeight.Bold, color = color)
                        }
                        if (index < steps.lastIndex) {
                            Box(modifier = Modifier.width(1.5.dp).height(28.dp)
                                .background(Brush.verticalGradient(
                                    listOf(color.copy(0.5f), Color.Transparent))))
                        }
                    }
                    Spacer(Modifier.width(12.dp))
                    Column(modifier = Modifier.padding(
                        bottom = if (index < steps.lastIndex) 4.dp else 0.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface)
                            Spacer(Modifier.width(6.dp))
                            Text("📖", fontSize = 11.sp)
                        }
                        Text(subtitle, fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant, lineHeight = 18.sp)
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // ── Tech stack ────────────────────────────────────────────────────
        WebSectionLabel("Tech stack")
        Spacer(Modifier.height(10.dp))
        Column(modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
            webTechStack.chunked(2).forEach { row ->
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()) {
                    row.forEach { WebTechCard(it, Modifier.weight(1f)) }
                    if (row.size == 1) Spacer(Modifier.weight(1f))
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // ── Skills ────────────────────────────────────────────────────────
        WebSectionLabel("Skills you'll gain")
        Spacer(Modifier.height(10.dp))

        // Use a wrapping layout via Column + chunked rows (avoids FlowRow import issues)
        Column(modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
            webSkills.chunked(3).forEach { rowSkills ->
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    rowSkills.forEach { skill ->
                        Box(modifier = Modifier
                            .background(Teal50, RoundedCornerShape(20.dp))
                            .border(0.5.dp, Teal100, RoundedCornerShape(20.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)) {
                            Text(skill, fontSize = 12.sp, color = Teal500,
                                fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // ── Requirements ──────────────────────────────────────────────────
        Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Teal50)
            .border(0.5.dp, Teal100, RoundedCornerShape(14.dp))
            .padding(14.dp)) {
            Column {
                Text("📋  Requirements", fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold, color = Teal300)
                Spacer(Modifier.height(6.dp))
                listOf(
                    "No prior coding experience needed",
                    "Passion for learning web development",
                    "Basic knowledge of programming helps"
                ).forEach {
                    Text("• $it", fontSize = 13.sp,
                        color = Teal300.copy(alpha = 0.8f), lineHeight = 22.sp)
                }
            }
        }

        Spacer(Modifier.height(28.dp))

        // ── CTA → CourseScreen ────────────────────────────────────────────
        Button(
            onClick = { navController.navigate("$ROUTE_COURSE/$ROUTE_WEB") },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Teal500)
        ) {
            Text("Start Web Track  →", fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold, color = Color(0xFF081210))
        }

        Spacer(Modifier.height(32.dp))
    }
}

@Composable private fun WebStatChip(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Text(label, fontSize = 11.sp, color = Teal300)
    }
}

@Composable private fun WebSectionLabel(text: String) {
    Text(text, fontSize = 15.sp, fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(horizontal = 16.dp))
}

@Composable private fun WebTechCard(tech: WebTechItem, modifier: Modifier = Modifier) {
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
fun WebScreenPreview() {
    FullyStackedTheme { WebScreen(navController = rememberNavController()) }
}
