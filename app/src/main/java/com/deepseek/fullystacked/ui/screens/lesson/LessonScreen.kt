package com.deepseek.fullystacked.ui.screens.lesson

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.deepseek.fullystacked.ui.theme.Purple500

@Composable
fun LessonScreen(title: String = "Lesson") {
    Column(modifier = Modifier.padding(24.dp)) {

        Text(title, fontSize = 22.sp, fontWeight = FontWeight.Medium)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "This is where your lesson content will go. Add text, code examples, or videos here.",
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Purple500),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Mark as Complete", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LessonPreview() {
    MaterialTheme { LessonScreen() }
}