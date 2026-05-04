package com.deepseek.fullystacked

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
<<<<<<< HEAD
import androidx.compose.ui.Modifier
import com.deepseek.fullystacked.navigation.AppNavHost
=======
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
import com.deepseek.fullystacked.ui.theme.FullyStackedTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FullyStackedTheme {
<<<<<<< HEAD
                AppNavHost(modifier = Modifier.fillMaxSize())
=======
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
            }
        }
    }
}
<<<<<<< HEAD
=======

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FullyStackedTheme {
        Greeting("Android")
    }
}
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
