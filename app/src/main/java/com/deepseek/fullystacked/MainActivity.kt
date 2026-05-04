package com.deepseek.fullystacked

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.deepseek.fullystacked.navigation.AppNavHost
import com.deepseek.fullystacked.ui.theme.FullyStackedTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FullyStackedTheme {
                AppNavHost(modifier = Modifier.fillMaxSize())
            }
        }
    }
}
