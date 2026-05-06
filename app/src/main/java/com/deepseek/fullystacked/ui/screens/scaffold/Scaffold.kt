package com.deepseek.fullystacked.ui.screens.scaffold

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.deepseek.fullystacked.ui.theme.Purple500

// FIX: Renamed from LessonScreen to ScaffoldLessonScreen to avoid clash with
// the real LessonScreen in ui/screens/lesson/LessonScreen.kt
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldLessonScreen(
    navController: NavController,
    title: String = "Lesson"
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                colors = topAppBarColors(
                    containerColor = Purple500,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp)
        ) {
            Text(
                text = "This is where your lesson content will go.",
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = Purple500),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Mark as Complete",
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScaffoldLessonPreview() {
    MaterialTheme {
        ScaffoldLessonScreen(navController = rememberNavController())
    }
}
