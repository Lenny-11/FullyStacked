package com.deepseek.fullystacked.ui.screens.course



import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

// ─────────────────────────────────────────────────────────────────────────────
// Colours
// ─────────────────────────────────────────────────────────────────────────────
private val Purple500   = Color(0xFF534AB7)
private val Purple100   = Color(0xFFCECBF6)
private val Purple50    = Color(0xFFEEEDFE)
private val GreenAccent = Color(0xFF1D9E75)
private val AmberAccent = Color(0xFFEF9F27)
private val BgDark      = Color(0xFF0F0E1A)

// ─────────────────────────────────────────────────────────────────────────────
// Data models
// ─────────────────────────────────────────────────────────────────────────────
enum class LessonStatus { LOCKED, AVAILABLE, IN_PROGRESS, COMPLETED }

data class CourseLesson(
    val id: Int,
    val title: String,
    val duration: String,
    val status: LessonStatus,
    val startIndex: Int          // index into allLessons carousel window
)

data class CourseModule(
    val title: String,
    val emoji: String,
    val accentColor: Color,
    val lessons: List<CourseLesson>
)

// ─────────────────────────────────────────────────────────────────────────────
// App track curriculum
// ─────────────────────────────────────────────────────────────────────────────
val appModules = listOf(
    CourseModule(
        title = "Kotlin Fundamentals",
        emoji = "🧩",
        accentColor = Purple500,
        lessons = listOf(
            CourseLesson(1,  "Variables & Types",       "8 min",  LessonStatus.COMPLETED,   0),
            CourseLesson(2,  "Functions & Lambdas",     "10 min", LessonStatus.COMPLETED,   0),
            CourseLesson(3,  "Classes & OOP",           "12 min", LessonStatus.IN_PROGRESS, 3),
        )
    ),
    CourseModule(
        title = "Jetpack Compose UI",
        emoji = "🎨",
        accentColor = Color(0xFF1A6FD4),
        lessons = listOf(
            CourseLesson(4,  "Composables & State",     "14 min", LessonStatus.AVAILABLE,   3),
            CourseLesson(5,  "Layouts & Modifiers",     "11 min", LessonStatus.LOCKED,      3),
            CourseLesson(6,  "Navigation & NavHost",    "13 min", LessonStatus.LOCKED,      3),
        )
    ),
    CourseModule(
        title = "Backend with Ktor",
        emoji = "🧱",
        accentColor = Color(0xFF0F766E),
        lessons = listOf(
            CourseLesson(7,  "REST API Basics",         "10 min", LessonStatus.LOCKED,      6),
            CourseLesson(8,  "JWT Authentication",      "15 min", LessonStatus.LOCKED,      6),
            CourseLesson(9,  "Routing & Plugins",       "12 min", LessonStatus.LOCKED,      6),
        )
    ),
    CourseModule(
        title = "Room & Databases",
        emoji = "🗄️",
        accentColor = AmberAccent,
        lessons = listOf(
            CourseLesson(10, "Room ORM Setup",          "9 min",  LessonStatus.LOCKED,      0),
            CourseLesson(11, "DAOs & Queries",          "11 min", LessonStatus.LOCKED,      0),
            CourseLesson(12, "Kotlin Flows",            "13 min", LessonStatus.LOCKED,      0),
        )
    )
)

// ─────────────────────────────────────────────────────────────────────────────
// Web track curriculum
// ─────────────────────────────────────────────────────────────────────────────
val webModules = listOf(
    CourseModule(
        title = "HTML5 & CSS3",
        emoji = "🧱",
        accentColor = Color(0xFFE24B4A),
        lessons = listOf(
            CourseLesson(1,  "HTML5 Basics",            "8 min",  LessonStatus.COMPLETED,   0),
            CourseLesson(2,  "CSS3 Box Model",          "10 min", LessonStatus.COMPLETED,   0),
            CourseLesson(3,  "Responsive Design",       "12 min", LessonStatus.IN_PROGRESS, 0),
        )
    ),
    CourseModule(
        title = "JavaScript",
        emoji = "⚡",
        accentColor = Color(0xFFD4A017),
        lessons = listOf(
            CourseLesson(4,  "JS Fundamentals",         "14 min", LessonStatus.AVAILABLE,   3),
            CourseLesson(5,  "DOM Manipulation",        "11 min", LessonStatus.LOCKED,      3),
            CourseLesson(6,  "Async & Promises",        "13 min", LessonStatus.LOCKED,      3),
        )
    ),
    CourseModule(
        title = "PHP & MySQL",
        emoji = "🐘",
        accentColor = Color(0xFF4F7DC9),
        lessons = listOf(
            CourseLesson(7,  "PHP Crash Course",        "10 min", LessonStatus.LOCKED,      6),
            CourseLesson(8,  "MySQL Queries",           "12 min", LessonStatus.LOCKED,      6),
            CourseLesson(9,  "MVC with Laravel",        "15 min", LessonStatus.LOCKED,      6),
        )
    ),
    CourseModule(
        title = "MEAN Stack",
        emoji = "🟢",
        accentColor = GreenAccent,
        lessons = listOf(
            CourseLesson(10, "Node.js & Express",       "11 min", LessonStatus.LOCKED,      0),
            CourseLesson(11, "MongoDB Basics",          "10 min", LessonStatus.LOCKED,      0),
            CourseLesson(12, "Angular 7 Intro",         "14 min", LessonStatus.LOCKED,      0),
        )
    )
)

// ─────────────────────────────────────────────────────────────────────────────
// CourseScreen
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun CourseScreen(
    navController: NavController,
    courseTitle: String = "Android Development",
    onLessonClick: (Int) -> Unit = {}
) {
    val isApp = courseTitle.contains("Android", ignoreCase = true)
    val modules = if (isApp) appModules else webModules
    val accentColor = if (isApp) Purple500 else Color(0xFFE24B4A)
    val trackEmoji = if (isApp) "📱" else "🌐"

    val totalLessons = modules.sumOf { it.lessons.size }
    val completedLessons = modules.sumOf { m -> m.lessons.count { it.status == LessonStatus.COMPLETED } }
    val progress = completedLessons.toFloat() / totalLessons

    var selectedModuleIndex by remember { mutableStateOf(0) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        // ── Hero banner ───────────────────────────────────────────────────
        item {
            CourseHero(
                title = courseTitle,
                emoji = trackEmoji,
                accentColor = accentColor,
                progress = progress,
                completedLessons = completedLessons,
                totalLessons = totalLessons,
                onBack = { navController.popBackStack() }
            )
        }

        // ── Module tabs ───────────────────────────────────────────────────
        item {
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(modules) { index, module ->
                    val isSelected = selectedModuleIndex == index
                    val bgColor by animateColorAsState(
                        if (isSelected) module.accentColor else MaterialTheme.colorScheme.surfaceVariant,
                        animationSpec = tween(250), label = "tab"
                    )
                    val txtColor by animateColorAsState(
                        if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                        animationSpec = tween(250), label = "tabTxt"
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(bgColor)
                            .clickable { selectedModuleIndex = index }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Text(module.emoji, fontSize = 13.sp)
                            Text(
                                text = module.title,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = txtColor,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }

        // ── Lesson list for selected module ───────────────────────────────
        item { Spacer(modifier = Modifier.height(16.dp)) }

        val selectedModule = modules[selectedModuleIndex]
        itemsIndexed(selectedModule.lessons) { index, lesson ->
            LessonRow(
                lesson = lesson,
                moduleAccent = selectedModule.accentColor,
                index = index,
                onClick = {
                    if (lesson.status != LessonStatus.LOCKED) {
                        onLessonClick(lesson.startIndex)
                        navController.navigate("lessons/${lesson.startIndex}")
                    }
                }
            )
        }

        // ── Other modules summary ─────────────────────────────────────────
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "All modules",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

        itemsIndexed(modules) { index, module ->
            ModuleSummaryRow(
                module = module,
                isActive = index == selectedModuleIndex,
                onClick = { selectedModuleIndex = index }
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Hero banner
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun CourseHero(
    title: String,
    emoji: String,
    accentColor: Color,
    progress: Float,
    completedLessons: Int,
    totalLessons: Int,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .background(
                Brush.linearGradient(listOf(BgDark, accentColor.copy(alpha = 0.8f)))
            )
    ) {
        // Back button
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 48.dp, start = 16.dp)
                .size(36.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.15f))
                .clickable(onClick = onBack),
            contentAlignment = Alignment.Center
        ) {
            Text("←", fontSize = 16.sp, color = Color.White)
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(emoji, fontSize = 36.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Progress bar
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(5.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(Color.White.copy(alpha = 0.25f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progress)
                            .height(5.dp)
                            .background(Color.White)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "$completedLessons / $totalLessons done",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.85f)
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Lesson row
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun LessonRow(
    lesson: CourseLesson,
    moduleAccent: Color,
    index: Int,
    onClick: () -> Unit
) {
    val isLocked = lesson.status == LessonStatus.LOCKED
    val alpha = if (isLocked) 0.45f else 1f

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = if (isLocked) 0.3f else 0.6f))
            .border(
                width = if (lesson.status == LessonStatus.IN_PROGRESS) 1.dp else 0.5.dp,
                color = if (lesson.status == LessonStatus.IN_PROGRESS) moduleAccent
                else MaterialTheme.colorScheme.outlineVariant.copy(alpha = alpha),
                shape = RoundedCornerShape(14.dp)
            )
            .clickable(enabled = !isLocked, onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Status icon
        val (statusIcon, statusBg, statusTint) = when (lesson.status) {
            LessonStatus.COMPLETED    -> Triple("✓", GreenAccent.copy(alpha = 0.15f), GreenAccent)
            LessonStatus.IN_PROGRESS  -> Triple("▶", moduleAccent.copy(alpha = 0.15f), moduleAccent)
            LessonStatus.AVAILABLE    -> Triple("○", moduleAccent.copy(alpha = 0.1f), moduleAccent)
            LessonStatus.LOCKED       -> Triple("🔒", Color.Gray.copy(alpha = 0.1f), Color.Gray)
        }
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(statusBg),
            contentAlignment = Alignment.Center
        ) {
            Text(statusIcon, fontSize = 14.sp, color = statusTint)
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "L${lesson.id}  ${lesson.title}",
                fontSize = 14.sp,
                fontWeight = if (lesson.status == LessonStatus.IN_PROGRESS) FontWeight.SemiBold else FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = alpha),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = lesson.duration,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = alpha)
            )
        }

        if (lesson.status == LessonStatus.IN_PROGRESS) {
            Box(
                modifier = Modifier
                    .background(moduleAccent, RoundedCornerShape(20.dp))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text("Resume", fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.Medium)
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Module summary row
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun ModuleSummaryRow(
    module: CourseModule,
    isActive: Boolean,
    onClick: () -> Unit
) {
    val completed = module.lessons.count { it.status == LessonStatus.COMPLETED }
    val total = module.lessons.size
    val progress = completed.toFloat() / total

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(
                if (isActive) module.accentColor.copy(alpha = 0.08f)
                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
            )
            .border(
                0.5.dp,
                if (isActive) module.accentColor.copy(alpha = 0.5f)
                else MaterialTheme.colorScheme.outlineVariant,
                RoundedCornerShape(14.dp)
            )
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(module.emoji, fontSize = 22.sp)
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = module.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(MaterialTheme.colorScheme.outlineVariant)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress)
                        .height(3.dp)
                        .background(module.accentColor)
                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "$completed/$total",
            fontSize = 12.sp,
            color = module.accentColor,
            fontWeight = FontWeight.Medium
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Preview
// ─────────────────────────────────────────────────────────────────────────────
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CourseScreenPreview() {
    MaterialTheme {
        CourseScreen(
            navController = rememberNavController(),
            courseTitle = "Android Development"
        )
    }
}
@Composable
fun CourseScreen(track: String) {
    Text(text = "Selected track: $track")
}