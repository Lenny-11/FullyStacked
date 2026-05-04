package com.deepseek.fullystacked.ui.screens.lesson

import android.R.attr.name
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

// ─────────────────────────────────────────────────────────────────────────────
// Brand colours
// ─────────────────────────────────────────────────────────────────────────────
private val Purple500  = Color(0xFF534AB7)
private val Purple300  = Color(0xFF8B83E0)
private val Purple50   = Color(0xFFEEEDFE)
private val Purple100  = Color(0xFFCECBF6)
private val BgDark     = Color(0xFF0F0E1A)
private val GreenAccent = Color(0xFF1D9E75)
private val AmberAccent = Color(0xFFEF9F27)
private val RedAccent   = Color(0xFFE24B4A)

// ─────────────────────────────────────────────────────────────────────────────
// Data models
// ─────────────────────────────────────────────────────────────────────────────
enum class ContentType { TEXT, CODE, QUIZ }

data class QuizOption(val text: String, val isCorrect: Boolean)

data class LessonContent(
    val type: ContentType,
    val heading: String,
    val body: String = "",
    val codeSnippet: String = "",
    val quizQuestion: String = "",
    val quizOptions: List<QuizOption> = emptyList()
)

data class Lesson(
    val id: Int,
    val title: String,
    val module: String,
    val emoji: String,
    val accentColor: Color,
    val contents: List<LessonContent>,
    val isCompleted: Boolean = false
)

// ─────────────────────────────────────────────────────────────────────────────
// Curriculum data from the PDF — grouped into sets of 3
// ─────────────────────────────────────────────────────────────────────────────
val allLessons by lazy {
    listOf(

        // ── Module 1: Front-End ───────────────────────────────────────────────
        Lesson(
            id = 1,
            title = "HTML5 Basics",
            module = "Module 1 · Front-End",
            emoji = "🧱",
            accentColor = Color(0xFFE24B4A),
            contents = listOf(
                LessonContent(
                    type = ContentType.TEXT,
                    heading = "What is HTML5?",
                    body = "HTML5 is the latest standard for structuring web content. It introduces semantic elements like <header>, <section>, <article>, and <footer> that give meaning to your markup and improve accessibility and SEO."
                ),
                LessonContent(
                    type = ContentType.CODE,
                    heading = "Your first HTML5 page",
                    codeSnippet = """<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<title>Fully Stacked</title>
</head>
<body>
<header>
  <h1>Hello, World!</h1>
</header>
<section>
  <p>Welcome to Full-Stack Dev.</p>
</section>
</body>
</html>"""
                ),
                LessonContent(
                    type = ContentType.QUIZ,
                    heading = "Quick Check",
                    quizQuestion = "Which HTML5 element is used for the main navigation links of a page?",
                    quizOptions = listOf(
                        QuizOption("<div>", false),
                        QuizOption("<nav>", true),
                        QuizOption("<section>", false),
                        QuizOption("<header>", false)
                    )
                )
            )
        ),

        Lesson(
            id = 2,
            title = "CSS3 Box Model",
            module = "Module 1 · Front-End",
            emoji = "🎨",
            accentColor = Color(0xFF1A6FD4),
            contents = listOf(
                LessonContent(
                    type = ContentType.TEXT,
                    heading = "Understanding the Box Model",
                    body = "Every HTML element is a rectangular box. The CSS Box Model describes the content area, padding (space inside the border), border, and margin (space outside the border). Mastering it is essential for precise layouts."
                ),
                LessonContent(
                    type = ContentType.CODE,
                    heading = "Box Model in action",
                    codeSnippet = """.card {
width: 300px;
padding: 16px;       /* inner spacing */
border: 2px solid #534AB7;
margin: 24px auto;   /* outer spacing */
border-radius: 12px;
box-sizing: border-box; /* width includes padding+border */
}"""
                ),
                LessonContent(
                    type = ContentType.QUIZ,
                    heading = "Quick Check",
                    quizQuestion = "Which CSS property ensures that padding and border are included in an element's total width?",
                    quizOptions = listOf(
                        QuizOption("box-model: include", false),
                        QuizOption("padding: internal", false),
                        QuizOption("box-sizing: border-box", true),
                        QuizOption("width: auto", false)
                    )
                )
            )
        ),

        Lesson(
            id = 3,
            title = "JavaScript Fundamentals",
            module = "Module 1 · Front-End",
            emoji = "⚡",
            accentColor = Color(0xFFD4A017),
            contents = listOf(
                LessonContent(
                    type = ContentType.TEXT,
                    heading = "Variables & Scope",
                    body = "Modern JavaScript uses let and const instead of var. let is block-scoped and mutable. const is block-scoped and cannot be reassigned. Understanding scope prevents bugs and makes your code predictable."
                ),
                LessonContent(
                    type = ContentType.CODE,
                    heading = "let, const, and arrow functions",
                    codeSnippet = """// const — cannot be reassigned
const appName = "Fully Stacked";

// let — block-scoped, mutable
let score = 0;

// Arrow function
const greet = (name) => {
return `Hello, \${name}!`;
};

console.log(greet(appName)); // Hello, Fully Stacked!
score += 10;
console.log(score); // 10"""
                ),
                LessonContent(
                    type = ContentType.QUIZ,
                    heading = "Quick Check",
                    quizQuestion = "Which keyword declares a block-scoped variable that cannot be reassigned?",
                    quizOptions = listOf(
                        QuizOption("var", false),
                        QuizOption("let", false),
                        QuizOption("const", true),
                        QuizOption("static", false)
                    )
                )
            )
        ),

        // ── Module 2: Foundation Paradigms ───────────────────────────────────
        Lesson(
            id = 4,
            title = "OOP in JavaScript",
            module = "Module 2 · Foundation Paradigm",
            emoji = "🔷",
            accentColor = Purple500,
            contents = listOf(
                LessonContent(
                    type = ContentType.TEXT,
                    heading = "Classes & Objects",
                    body = "Object-Oriented Programming organises code into reusable classes. A class is a blueprint; an object is an instance of that blueprint. JS ES6 classes support constructors, methods, and inheritance with the extends keyword."
                ),
                LessonContent(
                    type = ContentType.CODE,
                    heading = "Class with inheritance",
                    codeSnippet = """class Developer {
constructor(name, stack) {
this.name = name;
this.stack = stack;
}
introduce() {
return `I'm Lenny, a Stack dev.`;
}
}

class FullStackDev extends Developer {
constructor(name) {
super(name, "full-stack");
}
skills() {
return ["HTML", "Kotlin", "Ktor", "Room"];
}
}

const dev = new FullStackDev("Alex");
console.log(dev.introduce());"""
                ),
                LessonContent(
                    type = ContentType.QUIZ,
                    heading = "Quick Check",
                    quizQuestion = "Which keyword is used to inherit from a parent class in JavaScript?",
                    quizOptions = listOf(
                        QuizOption("implements", false),
                        QuizOption("super", false),
                        QuizOption("extends", true),
                        QuizOption("inherit", false)
                    )
                )
            )
        ),

        // ── Module 3: PHP & MySQL ────────────────────────────────────────────
        Lesson(
            id = 5,
            title = "PHP & MySQL Basics",
            module = "Module 3 · Back-End",
            emoji = "🐘",
            accentColor = Color(0xFF4F7DC9),
            contents = listOf(
                LessonContent(
                    type = ContentType.TEXT,
                    heading = "Connecting PHP to MySQL",
                    body = "PHP is a server-side scripting language. MySQL is a relational database. Together they power the back-end of millions of web apps. PHP's PDO extension provides a safe, consistent API for database access."
                ),
                LessonContent(
                    type = ContentType.CODE,
                    heading = "PDO query example",
                    codeSnippet = $$"""<?php
\$dsn = "mysql:host=localhost;dbname=fullystacked";
\$pdo = new PDO(\$dsn, "root", "password");

\$stmt = \$pdo->prepare(
"SELECT * FROM users WHERE id = ?"
);
\$stmt->execute([1]);
\$user = \$stmt->fetch(PDO::FETCH_ASSOC);

echo \$user['name']; // Alex
?>"""
                ),
                LessonContent(
                    type = ContentType.QUIZ,
                    heading = "Quick Check",
                    quizQuestion = "What does PDO stand for in PHP?",
                    quizOptions = listOf(
                        QuizOption("PHP Data Objects", true),
                        QuizOption("PHP Database Operator", false),
                        QuizOption("Protocol Data Output", false),
                        QuizOption("PHP Dynamic Objects", false)
                    )
                )
            )
        ),

        // ── Module 5: MEAN Stack ─────────────────────────────────────────────
        Lesson(
            id = 6,
            title = "Node.js & Express",
            module = "Module 5 · MEAN Stack",
            emoji = "🟢",
            accentColor = GreenAccent,
            contents = listOf(
                LessonContent(
                    type = ContentType.TEXT,
                    heading = "Building REST APIs with Express",
                    body = "Node.js lets JavaScript run on the server. Express is a minimal web framework for Node that makes it easy to define routes, handle HTTP methods, and return JSON responses — the backbone of any REST API."
                ),
                LessonContent(
                    type = ContentType.CODE,
                    heading = "Express REST endpoint",
                    codeSnippet = """const express = require("express");
const app = express();
app.use(express.json());

const users = [
{ id: 1, name: "Alex", level: "Beginner" }
];

// GET all users
app.get("/api/users", (req, res) => {
res.json(users);
});

// POST new user
app.post("/api/users", (req, res) => {
const user = { id: Date.now(), ...req.body };
users.push(user);
res.status(201).json(user);
});

app.listen(3000, () =>
console.log("Server running on port 3000")
);"""
                ),
                LessonContent(
                    type = ContentType.QUIZ,
                    heading = "Quick Check",
                    quizQuestion = "Which HTTP status code should a REST API return when a resource is successfully created?",
                    quizOptions = listOf(
                        QuizOption("200 OK", false),
                        QuizOption("201 Created", true),
                        QuizOption("204 No Content", false),
                        QuizOption("301 Moved", false)
                    )
                )
            )
        ),

        // ── Module 6: HTTP ────────────────────────────────────────────────────
        Lesson(
            id = 7,
            title = "HTTP Fundamentals",
            module = "Module 6 · Data Exchange",
            emoji = "🌐",
            accentColor = Color(0xFF0F766E),
            contents = listOf(
                LessonContent(
                    type = ContentType.TEXT,
                    heading = "HTTP Methods & Status Codes",
                    body = "HTTP is the protocol of the web. The main methods are GET (read), POST (create), PUT/PATCH (update), and DELETE (remove). Status codes tell clients what happened: 2xx = success, 4xx = client error, 5xx = server error."
                ),
                LessonContent(
                    type = ContentType.CODE,
                    heading = "Kotlin Ktor HTTP client call",
                    codeSnippet = """import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

val client = HttpClient()

suspend fun fetchUsers(): String {
val response: HttpResponse = client.get(
    "https://api.fullystacked.dev/users"
)
return response.bodyAsText()
}

// Call from a coroutine scope:
// val json = fetchUsers()"""
                ),
                LessonContent(
                    type = ContentType.QUIZ,
                    heading = "Quick Check",
                    quizQuestion = "Which HTTP method is used to retrieve data without modifying it?",
                    quizOptions = listOf(
                        QuizOption("POST", false),
                        QuizOption("PUT", false),
                        QuizOption("GET", true),
                        QuizOption("DELETE", false)
                    )
                )
            )
        )
    )
}

// ─────────────────────────────────────────────────────────────────────────────
// LessonScreen — shows a window of 3 lessons as a carousel
// ─────────────────────────────────────────────────────────────────────────────
@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun LessonScreen(
    navController: NavController,
    startLessonIndex: Int = 0,          // which lesson to open first
    lessonWindowSize: Int = 3           // carousel window (always 3)
) {
    // Clamp window to the 3 lessons starting at startLessonIndex
    val windowStart = (startLessonIndex).coerceIn(0, maxOf(0, allLessons.size - lessonWindowSize))
    val windowLessons = allLessons.drop(windowStart).take(lessonWindowSize)

    var completedIds by remember { mutableStateOf(setOf<Int>()) }
    val pagerState = rememberPagerState(pageCount = { windowLessons.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // ── Top bar ───────────────────────────────────────────────────────
        LessonTopBar(
            currentPage = pagerState.currentPage,
            total = windowLessons.size,
            lesson = windowLessons[pagerState.currentPage],
            onBack = { navController.popBackStack() }
        )

        // ── Lesson pager ──────────────────────────────────────────────────
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
            pageSpacing = 0.dp
        ) { page ->
            val lesson = windowLessons[page]
            LessonPage(
                lesson = lesson,
                isCompleted = lesson.id in completedIds
            )
        }

        // ── Bottom navigation ─────────────────────────────────────────────
        LessonBottomBar(
            currentPage = pagerState.currentPage,
            total = windowLessons.size,
            currentLesson = windowLessons[pagerState.currentPage],
            isCompleted = windowLessons[pagerState.currentPage].id in completedIds,
            onPrev = {
                scope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                }
            },
            onNext = {
                scope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            },
            onMarkComplete = {
                val id = windowLessons[pagerState.currentPage].id
                completedIds = completedIds + id
            },
            onFinish = { navController.popBackStack() }
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Top bar
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun LessonTopBar(
    currentPage: Int,
    total: Int,
    lesson: Lesson,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(top = 48.dp, bottom = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back button
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable(onClick = onBack),
                contentAlignment = Alignment.Center
            ) {
                Text("←", fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = lesson.module,
                    fontSize = 11.sp,
                    color = lesson.accentColor,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = lesson.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Lesson counter pill
            Box(
                modifier = Modifier
                    .background(Purple50, RoundedCornerShape(20.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "${currentPage + 1} / $total",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Purple500
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Progress bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            val progress by animateFloatAsState(
                targetValue = (currentPage + 1f) / total,
                animationSpec = tween(400),
                label = "progress"
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .height(3.dp)
                    .background(
                        Brush.horizontalGradient(listOf(Purple500, lesson.accentColor))
                    )
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Single lesson page (scrollable content)
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun LessonPage(lesson: Lesson, isCompleted: Boolean) {
    var activeContentIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp)
    ) {
        // ── Lesson hero ───────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(
                    Brush.linearGradient(
                        listOf(BgDark, lesson.accentColor.copy(alpha = 0.7f))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(lesson.emoji, fontSize = 40.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = lesson.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            if (isCompleted) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .background(GreenAccent, RoundedCornerShape(20.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text("✓ Done", fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.Medium)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ── Content type tabs ─────────────────────────────────────────────
        ContentTypeTabs(
            contents = lesson.contents,
            activeIndex = activeContentIndex,
            accentColor = lesson.accentColor,
            onSelect = { activeContentIndex = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ── Active content ────────────────────────────────────────────────
        AnimatedContent(
            targetState = activeContentIndex,
            transitionSpec = {
                val forward = targetState > initialState
                (slideInHorizontally { if (forward) it else -it } + fadeIn()) togetherWith
                        (slideOutHorizontally { if (forward) -it else it } + fadeOut())
            },
            label = "content_anim"
        ) { idx ->
            val content = lesson.contents[idx]
            when (content.type) {
                ContentType.TEXT -> TextContent(content)
                ContentType.CODE -> CodeContent(content)
                ContentType.QUIZ -> QuizContent(content, lesson.accentColor)
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Content tabs (Text / Code / Quiz)
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun ContentTypeTabs(
    contents: List<LessonContent>,
    activeIndex: Int,
    accentColor: Color,
    onSelect: (Int) -> Unit
) {
    val labels = contents.map {
        when (it.type) {
            ContentType.TEXT -> "📖 Read"
            ContentType.CODE -> "💻 Code"
            ContentType.QUIZ -> "🧩 Quiz"
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        labels.forEachIndexed { index, label ->
            val isActive = index == activeIndex
            val bg by animateColorAsState(
                if (isActive) accentColor else MaterialTheme.colorScheme.surfaceVariant,
                label = "tab_bg"
            )
            val textColor by animateColorAsState(
                if (isActive) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                label = "tab_text"
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(bg)
                    .clickable { onSelect(index) }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(label, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = textColor)
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Text content card
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun TextContent(content: LessonContent) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = content.heading,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            ),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Text(
                text = content.body,
                fontSize = 15.sp,
                lineHeight = 24.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Code content card
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun CodeContent(content: LessonContent) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = content.heading,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(Color(0xFF1A1830))
                .border(0.5.dp, Purple300.copy(alpha = 0.3f), RoundedCornerShape(14.dp))
        ) {
            Column {
                // Code editor top bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF252340))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(RedAccent))
                        Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(AmberAccent))
                        Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(GreenAccent))
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text("main.kt", fontSize = 11.sp, color = Purple300.copy(alpha = 0.7f))
                }
                // Code body
                Text(
                    text = content.codeSnippet,
                    fontSize = 13.sp,
                    fontFamily = FontFamily.Monospace,
                    color = Color(0xFFD4D0FF),
                    lineHeight = 20.sp,
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(16.dp)
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Quiz content card
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun QuizContent(content: LessonContent, accentColor: Color) {
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    val submitted = selectedIndex != null

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = content.heading,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(12.dp))

        // Question card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Purple50),
            border = BorderStroke(0.5.dp, Purple100)
        ) {
            Text(
                text = content.quizQuestion,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF2D2870),
                modifier = Modifier.padding(16.dp),
                lineHeight = 22.sp
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        content.quizOptions.forEachIndexed { index, option ->
            val isSelected = selectedIndex == index
            val isCorrect = option.isCorrect

            val bgColor = when {
                !submitted -> if (isSelected)
                    accentColor.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                isCorrect -> GreenAccent.copy(alpha = 0.15f)
                isSelected && !isCorrect -> RedAccent.copy(alpha = 0.12f)
                else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
            }
            val borderColor = when {
                !submitted -> if (isSelected) accentColor else MaterialTheme.colorScheme.outlineVariant
                isCorrect -> GreenAccent
                isSelected && !isCorrect -> RedAccent
                else -> MaterialTheme.colorScheme.outlineVariant
            }
            val indicator = when {
                submitted && isCorrect -> "✓"
                submitted && isSelected && !isCorrect -> "✗"
                else -> ('A' + index).toString()
            }
            val indicatorColor = when {
                submitted && isCorrect -> GreenAccent
                submitted && isSelected && !isCorrect -> RedAccent
                isSelected -> accentColor
                else -> MaterialTheme.colorScheme.onSurfaceVariant
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(bgColor)
                    .border(0.5.dp, borderColor, RoundedCornerShape(12.dp))
                    .clickable(enabled = !submitted) { selectedIndex = index }
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(indicatorColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        indicator,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = indicatorColor
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = option.text,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        if (submitted) {
            Spacer(modifier = Modifier.height(12.dp))
            val allCorrect = content.quizOptions[selectedIndex!!].isCorrect
            val resultBg = if (allCorrect) GreenAccent.copy(alpha = 0.1f) else RedAccent.copy(alpha = 0.1f)
            val resultBorder = if (allCorrect) GreenAccent else RedAccent
            val resultMsg = if (allCorrect) "🎉 Correct! Well done." else "❌ Not quite — review and try again."
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(resultBg)
                    .border(0.5.dp, resultBorder, RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Text(resultMsg, fontSize = 14.sp, color = resultBorder, fontWeight = FontWeight.Medium)
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Bottom bar
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun LessonBottomBar(
    currentPage: Int,
    total: Int,
    currentLesson: Lesson,
    isCompleted: Boolean,
    onPrev: () -> Unit,
    onNext: () -> Unit,
    onMarkComplete: () -> Unit,
    onFinish: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .navigationBarsPadding(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Prev button
            if (currentPage > 0) {
                OutlinedButton(
                    onClick = onPrev,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outlineVariant),
                    modifier = Modifier.height(46.dp)
                ) {
                    Text("← Prev", fontSize = 13.sp)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Dot indicators
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                repeat(total) { i ->
                    Box(
                        modifier = Modifier
                            .size(if (i == currentPage) 8.dp else 6.dp)
                            .clip(CircleShape)
                            .background(if (i == currentPage) currentLesson.accentColor else MaterialTheme.colorScheme.outlineVariant)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Next / Complete / Finish button
            val isLast = currentPage == total - 1
            Button(
                onClick = when {
                    isLast && !isCompleted -> onMarkComplete
                    isLast && isCompleted -> onFinish
                    else -> onNext
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isLast && isCompleted) GreenAccent else currentLesson.accentColor
                ),
                modifier = Modifier.height(46.dp)
            ) {
                Text(
                    text = when {
                        isLast && isCompleted -> "Finish ✓"
                        isLast -> "Complete"
                        else -> "Next →"
                    },
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Preview
// ─────────────────────────────────────────────────────────────────────────────
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LessonScreenPreview() {
    MaterialTheme {
        LessonScreen(
            navController = rememberNavController(),
            startLessonIndex = 0
        )
    }
}