package com.deepseek.fullystacked.ui.screens.course

<<<<<<< HEAD
=======


>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
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
<<<<<<< HEAD
import androidx.compose.ui.platform.LocalContext
=======
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
<<<<<<< HEAD
import com.deepseek.fullystacked.navigation.ROUTE_LESSON
import com.deepseek.fullystacked.ui.screens.pdf.PdfUtils
=======
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692

// ─────────────────────────────────────────────────────────────────────────────
// Colours
// ─────────────────────────────────────────────────────────────────────────────
<<<<<<< HEAD
private val Purple500   = Color(0xFF7C74F0)
private val GreenAccent = Color(0xFF2DD4BB)
=======
private val Purple500   = Color(0xFF534AB7)
private val Purple100   = Color(0xFFCECBF6)
private val Purple50    = Color(0xFFEEEDFE)
private val GreenAccent = Color(0xFF1D9E75)
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
private val AmberAccent = Color(0xFFEF9F27)
private val BgDark      = Color(0xFF0F0E1A)

// ─────────────────────────────────────────────────────────────────────────────
// Data models
// ─────────────────────────────────────────────────────────────────────────────
enum class LessonStatus { LOCKED, AVAILABLE, IN_PROGRESS, COMPLETED }

<<<<<<< HEAD
/**
 * @param pdfPageIndex  0-based page number in the bundled PDF that best
 *                      matches this lesson's topic. -1 = no PDF chapter.
 */
=======
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
data class CourseLesson(
    val id: Int,
    val title: String,
    val duration: String,
    val status: LessonStatus,
<<<<<<< HEAD
    val startIndex: Int,
    val pdfPageIndex: Int = -1
=======
    val startIndex: Int          // index into allLessons carousel window
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
)

data class CourseModule(
    val title: String,
    val emoji: String,
    val accentColor: Color,
    val lessons: List<CourseLesson>
)

// ─────────────────────────────────────────────────────────────────────────────
<<<<<<< HEAD
// PDF page map  (0-based)
//  Ch 1  The Modern Web        → 14
//  Ch 2  Planning Your Work    → 23
//  Ch 3  User Experience       → 59
//  Ch 4  Designing Systems     → 79
//  Ch 5  Ethics                → 106
//  Ch 6  Front End             → 114
//  Ch 7  Testing               → 152
//  Ch 8  JavaScript            → 169
//  Ch 9  Accessibility         → 219
//  Ch 10 APIs                  → 236
//  Ch 11 Storing Data          → 260
//  Ch 12 Security              → 275
//  Ch 13 Deployment            → 302
//  Ch 14 In Production         → 325
//  Ch 15 Constant Learning     → 337
// ─────────────────────────────────────────────────────────────────────────────

// App track curriculum
val appModules = listOf(
    CourseModule("Kotlin Fundamentals", "🧩", Purple500, listOf(
        CourseLesson(1,  "Variables & Types",    "8 min",  LessonStatus.COMPLETED,   0, 14),
        CourseLesson(2,  "Functions & Lambdas",  "10 min", LessonStatus.COMPLETED,   0, 14),
        CourseLesson(3,  "Classes & OOP",        "12 min", LessonStatus.IN_PROGRESS, 3, 79),
    )),
    CourseModule("Jetpack Compose UI", "🎨", Color(0xFF1A6FD4), listOf(
        CourseLesson(4,  "Composables & State",  "14 min", LessonStatus.AVAILABLE,   3, 114),
        CourseLesson(5,  "Layouts & Modifiers",  "11 min", LessonStatus.LOCKED,      3, 114),
        CourseLesson(6,  "Navigation & NavHost", "13 min", LessonStatus.LOCKED,      3, 59),
    )),
    CourseModule("Backend with Ktor", "🧱", Color(0xFF0F766E), listOf(
        CourseLesson(7,  "REST API Basics",      "10 min", LessonStatus.LOCKED,      6, 236),
        CourseLesson(8,  "JWT Authentication",   "15 min", LessonStatus.LOCKED,      6, 275),
        CourseLesson(9,  "Routing & Plugins",    "12 min", LessonStatus.LOCKED,      6, 302),
    )),
    CourseModule("Room & Databases", "🗄️", AmberAccent, listOf(
        CourseLesson(10, "Room ORM Setup",       "9 min",  LessonStatus.LOCKED,      0, 260),
        CourseLesson(11, "DAOs & Queries",       "11 min", LessonStatus.LOCKED,      0, 260),
        CourseLesson(12, "Kotlin Flows",         "13 min", LessonStatus.LOCKED,      0, 337),
    ))
)

// Web track curriculum
val webModules = listOf(
    CourseModule("HTML5 & CSS3", "🧱", Color(0xFFE24B4A), listOf(
        CourseLesson(1,  "HTML5 Basics",         "8 min",  LessonStatus.COMPLETED,   0, 114),
        CourseLesson(2,  "CSS3 Box Model",       "10 min", LessonStatus.COMPLETED,   0, 122),
        CourseLesson(3,  "Responsive Design",    "12 min", LessonStatus.IN_PROGRESS, 0, 136),
    )),
    CourseModule("JavaScript", "⚡", Color(0xFFD4A017), listOf(
        CourseLesson(4,  "JS Fundamentals",      "14 min", LessonStatus.AVAILABLE,   3, 169),
        CourseLesson(5,  "DOM Manipulation",     "11 min", LessonStatus.LOCKED,      3, 179),
        CourseLesson(6,  "Async & Promises",     "13 min", LessonStatus.LOCKED,      3, 170),
    )),
    CourseModule("PHP & MySQL", "🐘", Color(0xFF4F7DC9), listOf(
        CourseLesson(7,  "PHP Crash Course",     "10 min", LessonStatus.LOCKED,      6, 260),
        CourseLesson(8,  "MySQL Queries",        "12 min", LessonStatus.LOCKED,      6, 260),
        CourseLesson(9,  "MVC with Laravel",     "15 min", LessonStatus.LOCKED,      6, 79),
    )),
    CourseModule("MEAN Stack", "🟢", GreenAccent, listOf(
        CourseLesson(10, "Node.js & Express",    "11 min", LessonStatus.LOCKED,      0, 180),
        CourseLesson(11, "MongoDB Basics",       "10 min", LessonStatus.LOCKED,      0, 260),
        CourseLesson(12, "Angular 7 Intro",      "14 min", LessonStatus.LOCKED,      0, 114),
    ))
=======
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
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
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
<<<<<<< HEAD
    val context = LocalContext.current
    val isApp     = courseTitle.contains("Android", ignoreCase = true)
    val modules   = if (isApp) appModules else webModules
    val accent    = if (isApp) Purple500 else Color(0xFFE24B4A)
    val emoji     = if (isApp) "📱" else "🌐"

    val total     = modules.sumOf { it.lessons.size }
    val completed = modules.sumOf { m -> m.lessons.count { it.status == LessonStatus.COMPLETED } }
    val progress  = completed.toFloat() / total

    var selectedModule by remember { mutableStateOf(0) }
=======
    val isApp = courseTitle.contains("Android", ignoreCase = true)
    val modules = if (isApp) appModules else webModules
    val accentColor = if (isApp) Purple500 else Color(0xFFE24B4A)
    val trackEmoji = if (isApp) "📱" else "🌐"

    val totalLessons = modules.sumOf { it.lessons.size }
    val completedLessons = modules.sumOf { m -> m.lessons.count { it.status == LessonStatus.COMPLETED } }
    val progress = completedLessons.toFloat() / totalLessons

    var selectedModuleIndex by remember { mutableStateOf(0) }
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
<<<<<<< HEAD
        item {
            CourseHero(
                title = courseTitle, emoji = emoji, accentColor = accent,
                progress = progress, completedLessons = completed, totalLessons = total,
=======
        // ── Hero banner ───────────────────────────────────────────────────
        item {
            CourseHero(
                title = courseTitle,
                emoji = trackEmoji,
                accentColor = accentColor,
                progress = progress,
                completedLessons = completedLessons,
                totalLessons = totalLessons,
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
                onBack = { navController.popBackStack() }
            )
        }

<<<<<<< HEAD
        // Module tabs
=======
        // ── Module tabs ───────────────────────────────────────────────────
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
        item {
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(modules) { index, module ->
<<<<<<< HEAD
                    val isSelected = selectedModule == index
                    val bg by animateColorAsState(
                        if (isSelected) module.accentColor
                        else MaterialTheme.colorScheme.surfaceVariant,
                        tween(250), label = "tab"
                    )
                    val txt by animateColorAsState(
                        if (isSelected) Color.White
                        else MaterialTheme.colorScheme.onSurfaceVariant,
                        tween(250), label = "tabTxt"
=======
                    val isSelected = selectedModuleIndex == index
                    val bgColor by animateColorAsState(
                        if (isSelected) module.accentColor else MaterialTheme.colorScheme.surfaceVariant,
                        animationSpec = tween(250), label = "tab"
                    )
                    val txtColor by animateColorAsState(
                        if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                        animationSpec = tween(250), label = "tabTxt"
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
<<<<<<< HEAD
                            .background(bg)
                            .clickable { selectedModule = index }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                            Text(module.emoji, fontSize = 13.sp)
                            Text(module.title, fontSize = 12.sp,
                                fontWeight = FontWeight.Medium, color = txt, maxLines = 1)
=======
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
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
                        }
                    }
                }
            }
        }

<<<<<<< HEAD
        item { Spacer(modifier = Modifier.height(16.dp)) }

        // PDF reading hint
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(accent.copy(alpha = 0.10f))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("📖", fontSize = 14.sp)
                Spacer(Modifier.width(8.dp))
                Text(
                    "Tap any lesson card to open the reference chapter in the PDF book",
                    fontSize = 11.sp,
                    color = accent,
                    lineHeight = 16.sp
                )
            }
            Spacer(Modifier.height(8.dp))
        }

        // Lessons for selected module
        val currentModule = modules[selectedModule]
        itemsIndexed(currentModule.lessons) { _, lesson ->
            LessonRow(
                lesson = lesson,
                moduleAccent = currentModule.accentColor,
                onClick = {
                    if (lesson.status != LessonStatus.LOCKED) {
                        // Open lesson screen
                        onLessonClick(lesson.startIndex)
                    }
                    // Tapping the PDF icon always opens the book
                },
                onPdfClick = {
                    if (lesson.pdfPageIndex >= 0) {
                        PdfUtils.openPdfAtPage(context, lesson.pdfPageIndex)
=======
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
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
                    }
                }
            )
        }

<<<<<<< HEAD
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Text("All modules", fontSize = 14.sp, fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(horizontal = 16.dp))
=======
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
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
            Spacer(modifier = Modifier.height(10.dp))
        }

        itemsIndexed(modules) { index, module ->
            ModuleSummaryRow(
                module = module,
<<<<<<< HEAD
                isActive = index == selectedModule,
                onClick = { selectedModule = index }
=======
                isActive = index == selectedModuleIndex,
                onClick = { selectedModuleIndex = index }
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
<<<<<<< HEAD
// Hero
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun CourseHero(
    title: String, emoji: String, accentColor: Color,
    progress: Float, completedLessons: Int, totalLessons: Int,
=======
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
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
<<<<<<< HEAD
            .background(Brush.linearGradient(listOf(BgDark, accentColor.copy(alpha = 0.8f))))
    ) {
=======
            .background(
                Brush.linearGradient(listOf(BgDark, accentColor.copy(alpha = 0.8f)))
            )
    ) {
        // Back button
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 48.dp, start = 16.dp)
                .size(36.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.15f))
                .clickable(onClick = onBack),
            contentAlignment = Alignment.Center
<<<<<<< HEAD
        ) { Text("←", fontSize = 16.sp, color = Color.White) }
=======
        ) {
            Text("←", fontSize = 16.sp, color = Color.White)
        }
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(emoji, fontSize = 36.sp)
<<<<<<< HEAD
            Spacer(Modifier.height(4.dp))
            Text(title, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .weight(1f).height(5.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(Color.White.copy(alpha = 0.25f))
                ) {
                    Box(modifier = Modifier.fillMaxWidth(progress).height(5.dp)
                        .background(Color.White))
                }
                Spacer(Modifier.width(10.dp))
                Text("$completedLessons / $totalLessons done", fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.85f))
=======
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
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
<<<<<<< HEAD
// Lesson row — tapping the card opens the lesson; tapping the 📖 badge opens PDF
=======
// Lesson row
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun LessonRow(
    lesson: CourseLesson,
    moduleAccent: Color,
<<<<<<< HEAD
    onClick: () -> Unit,
    onPdfClick: () -> Unit
) {
    val isLocked = lesson.status == LessonStatus.LOCKED
    val alpha    = if (isLocked) 0.45f else 1f
=======
    index: Int,
    onClick: () -> Unit
) {
    val isLocked = lesson.status == LessonStatus.LOCKED
    val alpha = if (isLocked) 0.45f else 1f
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .clip(RoundedCornerShape(14.dp))
<<<<<<< HEAD
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(
                alpha = if (isLocked) 0.3f else 0.6f))
=======
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = if (isLocked) 0.3f else 0.6f))
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
            .border(
                width = if (lesson.status == LessonStatus.IN_PROGRESS) 1.dp else 0.5.dp,
                color = if (lesson.status == LessonStatus.IN_PROGRESS) moduleAccent
                else MaterialTheme.colorScheme.outlineVariant.copy(alpha = alpha),
                shape = RoundedCornerShape(14.dp)
            )
<<<<<<< HEAD
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val (icon, iconBg, iconTint) = when (lesson.status) {
            LessonStatus.COMPLETED   -> Triple("✓", GreenAccent.copy(0.15f), GreenAccent)
            LessonStatus.IN_PROGRESS -> Triple("▶", moduleAccent.copy(0.15f), moduleAccent)
            LessonStatus.AVAILABLE   -> Triple("○", moduleAccent.copy(0.1f),  moduleAccent)
            LessonStatus.LOCKED      -> Triple("🔒", Color.Gray.copy(0.1f),   Color.Gray)
        }
        Box(modifier = Modifier.size(36.dp).clip(CircleShape).background(iconBg),
            contentAlignment = Alignment.Center) {
            Text(icon, fontSize = 14.sp, color = iconTint)
        }
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text("L${lesson.id}  ${lesson.title}", fontSize = 14.sp,
                fontWeight = if (lesson.status == LessonStatus.IN_PROGRESS)
                    FontWeight.SemiBold else FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = alpha),
                maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(lesson.duration, fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = alpha))
        }

        // PDF book badge — always visible (dimmed when locked)
        if (lesson.pdfPageIndex >= 0) {
            Spacer(Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(moduleAccent.copy(alpha = if (isLocked) 0.08f else 0.18f))
                    .clickable { onPdfClick() }
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("📖", fontSize = 13.sp)
            }
        }

        if (lesson.status == LessonStatus.IN_PROGRESS) {
            Spacer(Modifier.width(6.dp))
            Box(modifier = Modifier
                .background(moduleAccent, RoundedCornerShape(20.dp))
                .padding(horizontal = 8.dp, vertical = 3.dp)) {
=======
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
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
                Text("Resume", fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.Medium)
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Module summary row
// ─────────────────────────────────────────────────────────────────────────────
@Composable
<<<<<<< HEAD
private fun ModuleSummaryRow(module: CourseModule, isActive: Boolean, onClick: () -> Unit) {
    val done  = module.lessons.count { it.status == LessonStatus.COMPLETED }
    val total = module.lessons.size
=======
private fun ModuleSummaryRow(
    module: CourseModule,
    isActive: Boolean,
    onClick: () -> Unit
) {
    val completed = module.lessons.count { it.status == LessonStatus.COMPLETED }
    val total = module.lessons.size
    val progress = completed.toFloat() / total

>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .clip(RoundedCornerShape(14.dp))
<<<<<<< HEAD
            .background(if (isActive) module.accentColor.copy(0.10f)
            else MaterialTheme.colorScheme.surfaceVariant.copy(0.4f))
            .border(0.5.dp,
                if (isActive) module.accentColor.copy(0.5f)
                else MaterialTheme.colorScheme.outlineVariant,
                RoundedCornerShape(14.dp))
=======
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
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(module.emoji, fontSize = 22.sp)
<<<<<<< HEAD
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(module.title, fontSize = 14.sp, fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface)
            Spacer(Modifier.height(4.dp))
            Box(modifier = Modifier.fillMaxWidth().height(3.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(MaterialTheme.colorScheme.outlineVariant)) {
                Box(modifier = Modifier.fillMaxWidth(done.toFloat() / total)
                    .height(3.dp).background(module.accentColor))
            }
        }
        Spacer(Modifier.width(12.dp))
        Text("$done/$total", fontSize = 12.sp, color = module.accentColor,
            fontWeight = FontWeight.Medium)
=======
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
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Preview
// ─────────────────────────────────────────────────────────────────────────────
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CourseScreenPreview() {
    MaterialTheme {
<<<<<<< HEAD
        CourseScreen(navController = rememberNavController(), courseTitle = "Android Development")
    }
}
=======
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
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
