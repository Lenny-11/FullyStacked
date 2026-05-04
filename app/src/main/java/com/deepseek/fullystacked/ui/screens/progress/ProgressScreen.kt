package com.deepseek.fullystacked.ui.screens.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.deepseek.fullystacked.data.model.UserProgress
import com.deepseek.fullystacked.viewmodel.ProfileViewModel

private val Purple500 = Color(0xFF534AB7)
private val Purple50  = Color(0xFFEEEDFE)
private val Purple100 = Color(0xFFCECBF6)

@Composable
fun ProgressScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val profile     by profileViewModel.profile.collectAsState()
    val allProgress by profileViewModel.allProgress.collectAsState()
    val isLoading   by profileViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) { profileViewModel.loadProfile() }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("My Progress", fontSize = 22.sp, fontWeight = FontWeight.Medium)
            Text(
                "Track your learning journey 📈",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
            Spacer(Modifier.height(8.dp))
        }

        if (isLoading) {
            item {
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Purple500)
                }
            }
        } else {
            // Overall summary card
            item {
                OverallSummaryCard(
                    overallPercent = if (allProgress.isEmpty()) 0f
                    else allProgress.map { it.progressPercent }.average().toFloat(),
                    totalLessons = allProgress.sumOf { it.completedLessonIds.size },
                    streakDays = profile?.streakDays ?: 0
                )
            }

            if (allProgress.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "No progress yet.\nStart a course to see your stats here! 🚀",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 22.sp
                        )
                    }
                }
            } else {
                item {
                    Text(
                        "Courses",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                items(allProgress) { progress ->
                    CourseProgressCard(progress)
                }
            }
        }
    }
}

@Composable
private fun OverallSummaryCard(
    overallPercent: Float,
    totalLessons: Int,
    streakDays: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Purple50, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column {
            Text("Overall Progress", fontWeight = FontWeight.Medium, fontSize = 15.sp)
            Spacer(Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { overallPercent },
                modifier = Modifier.fillMaxWidth(),
                color = Purple500,
                trackColor = Purple100
            )
            Spacer(Modifier.height(6.dp))
            Text(
                "${(overallPercent * 100).toInt()}% completed",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(14.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                SummaryChip("$totalLessons", "Lessons Done")
                SummaryChip("$streakDays", "Day Streak 🔥")
            }
        }
    }
}

@Composable
private fun SummaryChip(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Purple500)
        Text(label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun CourseProgressCard(progress: UserProgress) {
    val courseLabel = when (progress.track) {
        "app"     -> "Android Development 📱"
        "web"     -> "Web Development 🌐"
        else      -> progress.courseId
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(14.dp))
            .padding(14.dp)
    ) {
        Column {
            Text(courseLabel, fontWeight = FontWeight.Medium, fontSize = 14.sp)
            Spacer(Modifier.height(6.dp))
            LinearProgressIndicator(
                progress = { progress.progressPercent },
                modifier = Modifier.fillMaxWidth(),
                color = Purple500,
                trackColor = Purple100
            )
            Spacer(Modifier.height(4.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "${progress.completedLessonIds.size} lessons completed",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    "${(progress.progressPercent * 100).toInt()}%",
                    fontSize = 11.sp,
                    color = Purple500
                )
            }
        }
    }
}
