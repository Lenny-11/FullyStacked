package com.deepseek.fullystacked.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.deepseek.fullystacked.ui.screens.Auth.LoginScreen
import com.deepseek.fullystacked.ui.screens.course.CourseScreen
import com.deepseek.fullystacked.ui.theme.Purple100
import com.deepseek.fullystacked.ui.theme.Purple50

@Composable
fun HomeScreen( navController: NavController,
    onCourseClick: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {

        Text(
            text = "Welcome back 👋",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = "What do you want to learn today?",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        CourseCard("Android Development", "Kotlin, Jetpack Compose") {
            onCourseClick("android")
        }

        Spacer(modifier = Modifier.height(16.dp))

        CourseCard("Web Development", "HTML, CSS, JavaScript") {
            onCourseClick("web")
        }

        Spacer(modifier = Modifier.height(16.dp))

        CourseCard("Backend Development", "Node.js, APIs, Databases") {
            onCourseClick("backend")
        }
    }
}

@Composable
fun CourseCard(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Purple50, RoundedCornerShape(16.dp))
            .border(0.5.dp, Purple100, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Column {
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Text(
                subtitle,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        LoginScreen(
            navController = rememberNavController(),

            )
    }
}