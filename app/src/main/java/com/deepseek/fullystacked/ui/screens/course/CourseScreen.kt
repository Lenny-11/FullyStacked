package com.deepseek.fullystacked.ui.screens.course

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.deepseek.fullystacked.ui.theme.*

@Composable
fun CourseScreen(
    courseTitle: String = "Android Development",
    progress: Float = 0.4f,
    onLessonClick: (String) -> Unit = {}
) {
    Column(modifier = Modifier.padding(24.dp)) {

        Text(courseTitle, fontSize = 22.sp, fontWeight = FontWeight.Medium)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Your Progress")

        LinearProgressIndicator(
            progress = progress,
            color = Purple500,
            modifier = Modifier.fillMaxWidth()
        )

        Text("${(progress * 100).toInt()}% completed", fontSize = 12.sp)

        Spacer(modifier = Modifier.height(20.dp))

        Text("Lessons", fontWeight = FontWeight.Medium)

        LessonItem("Intro to Kotlin", true) { onLessonClick("1") }
        LessonItem("Variables", true) { onLessonClick("2") }
        LessonItem("Compose Basics", false) { onLessonClick("3") }
    }
}

@Composable
fun LessonItem(title: String, done: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(Purple50, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(14.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(title)
            Text(if (done) "✓" else "•", color = Purple500)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CoursePreview() {
    MaterialTheme { CourseScreen() }
}