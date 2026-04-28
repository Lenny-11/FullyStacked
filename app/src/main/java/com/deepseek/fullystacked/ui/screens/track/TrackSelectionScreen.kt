package com.deepseek.fullystacked.ui.screens.track



import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.deepseek.fullystacked.ui.screens.Auth.LoginScreen
import com.deepseek.fullystacked.ui.theme.Purple100
import com.deepseek.fullystacked.ui.theme.Purple50

@Composable
fun TrackSelectionScreen(
    navController: NavHostController,
    onWebClick: () -> Unit = {},
    onAppClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = "Choose your path",
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = "Start your full-stack journey",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // ── App Development ─────────────────────
        TrackCard(
            title = "App Development",
            subtitle = "Kotlin, Android, Jetpack Compose",
            onClick = onAppClick
        )

        Spacer(modifier = Modifier.height(20.dp))

        // ── Web Development ─────────────────────
        TrackCard(
            title = "Web Development",
            subtitle = "HTML, CSS, JavaScript, React",
            onClick = onWebClick
        )
    }
}

@Composable
fun TrackCard(
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
            .padding(20.dp)
    ) {
        Column {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = subtitle,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TrackSelectionScreenPreview() {
    MaterialTheme {
        TrackSelectionScreen(
            navController = rememberNavController(),

            )
    }
}
