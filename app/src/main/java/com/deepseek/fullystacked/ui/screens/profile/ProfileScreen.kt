package com.deepseek.fullystacked.ui.screens.profile



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.deepseek.fullystacked.ui.theme.*

@Composable
fun ProfileScreen(
    username: String = "John Doe",
    level: Int = 5,
    streak: Int = 7,
    progress: Float = 0.65f,
    completedLessons: Int = 24
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {

        // ── Header ─────────────────────────────
        Text(
            text = "Profile",
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = "Keep up the momentum 🚀",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ── User Card ──────────────────────────
        ProfileCard(username, level, streak)

        Spacer(modifier = Modifier.height(20.dp))

        // ── Progress Section ───────────────────
        Text("Overall Progress", fontWeight = FontWeight.Medium)

        Spacer(modifier = Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = progress,
            color = Purple500,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "${(progress * 100).toInt()}% completed",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(20.dp))

        // ── Stats Grid ─────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatBox("Lessons", "$completedLessons")
            StatBox("Level", "$level")
            StatBox("Streak", "$streak days")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ── Motivation Card ────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Purple50, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Text(
                text = "🔥 Keep your streak alive! Learn today to level up faster.",
                fontSize = 13.sp
            )
        }
    }
}
@Composable
fun ProfileCard(username: String, level: Int, streak: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Purple50, RoundedCornerShape(16.dp))
            .padding(20.dp)
    ) {
        Column {

            Text(
                text = username,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Level $level", color = Purple500)
                Text("🔥 $streak day streak", color = Purple500)
            }
        }
    }
}
@Composable
fun StatBox(title: String, value: String) {
    Box(
        modifier = Modifier
            .width(100.dp)
            .background(Purple50, RoundedCornerShape(12.dp))
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(value, fontWeight = FontWeight.Medium, fontSize = 16.sp)

            Text(
                title,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfilePreview() {
    MaterialTheme {
        ProfileScreen()
    }
}