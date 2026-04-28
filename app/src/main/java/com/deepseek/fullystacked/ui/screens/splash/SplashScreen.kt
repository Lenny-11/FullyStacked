package com.deepseek.fullystacked.ui.screens.splash

import android.annotation.SuppressLint
import com.deepseek.fullystacked.navigation.ROUTE_LOGIN
import kotlinx.coroutines.launch



import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import kotlinx.coroutines.delay

// ---------------------------------------------------------------------------
// Brand colours
// ---------------------------------------------------------------------------
private val BgDark      = Color(0xFF0F0E1A)   // deep near-black
private val Purple700   = Color(0xFF3730A3)
private val Purple500   = Color(0xFF534AB7)
private val Purple300   = Color(0xFF8B83E0)
private val Purple100   = Color(0xFFCECBF6)
private val Purple50    = Color(0xFFEEEDFE)
private val Accent      = Color(0xFF7C6FF7)

// ---------------------------------------------------------------------------
// SplashScreen
// ---------------------------------------------------------------------------
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SplashScreen(navController: NavController) {

    // ── Navigate to Login after delay ─────────────────────────────────────
    val coroutinescope = rememberCoroutineScope()
    coroutinescope.launch {

        delay(timeMillis=2000)
        navController.navigate(route = ROUTE_LOGIN)
    }
    // ── Animation states ──────────────────────────────────────────────────
    val logoScale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "logoScale"
    )

    // Staggered fade-in for each element
    var showLogo     by remember { mutableStateOf(false) }
    var showName     by remember { mutableStateOf(false) }
    var showTagline  by remember { mutableStateOf(false) }
    var showDots     by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100); showLogo    = true
        delay(300); showName    = true
        delay(200); showTagline = true
        delay(250); showDots    = true
    }

    val logoAlpha    by animateFloatAsState(if (showLogo)    1f else 0f, tween(500), label = "la")
    val nameAlpha    by animateFloatAsState(if (showName)    1f else 0f, tween(500), label = "na")
    val taglineAlpha by animateFloatAsState(if (showTagline) 1f else 0f, tween(500), label = "ta")
    val dotsAlpha    by animateFloatAsState(if (showDots)    1f else 0f, tween(600), label = "da")

    // Pulsing glow ring on logo
    val infiniteTransition = rememberInfiniteTransition(label = "glow")
    val glowScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.18f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowScale"
    )

    // Loading dots pulse
    val dot1Alpha by infiniteTransition.animateFloat(
        initialValue = 0.2f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(600), RepeatMode.Reverse),
        label = "d1"
    )
    val dot2Alpha by infiniteTransition.animateFloat(
        initialValue = 0.2f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(600, delayMillis = 200), RepeatMode.Reverse),
        label = "d2"
    )
    val dot3Alpha by infiniteTransition.animateFloat(
        initialValue = 0.2f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(600, delayMillis = 400), RepeatMode.Reverse),
        label = "d3"
    )

    // ── UI ────────────────────────────────────────────────────────────────
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(Color(0xFF1E1B3A), BgDark),
                    radius = 1200f
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        // Subtle background grid lines for depth
        BackgroundGrid()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // ── Glow ring + Logo ──────────────────────────────────────────
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .alpha(logoAlpha)
                    .scale(logoScale)
            ) {
                // Outer glow ring
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .scale(glowScale)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    Purple500.copy(alpha = 0.25f),
                                    Color.Transparent
                                )
                            ),
                            shape = RoundedCornerShape(28.dp)
                        )
                )
                // Logo card
                Box(
                    modifier = Modifier
                        .size(76.dp)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Purple700, Accent)
                            ),
                            shape = RoundedCornerShape(22.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // Inner stack symbol — three horizontal bars
                    Column(
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        StackBar(width = 32.dp, alpha = 1.0f)
                        StackBar(width = 24.dp, alpha = 0.75f)
                        StackBar(width = 16.dp, alpha = 0.5f)
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ── App name ──────────────────────────────────────────────────
            Text(
                text = "Fully Stacked",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = (-0.5).sp,
                modifier = Modifier.alpha(nameAlpha)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // ── Tagline ───────────────────────────────────────────────────
            Text(
                text = "Master full-stack development\nwith Kotlin",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Purple300,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp,
                modifier = Modifier.alpha(taglineAlpha)
            )

            Spacer(modifier = Modifier.height(56.dp))

            // ── Loading dots ──────────────────────────────────────────────
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.alpha(dotsAlpha)
            ) {
                LoadingDot(alpha = dot1Alpha)
                LoadingDot(alpha = dot2Alpha)
                LoadingDot(alpha = dot3Alpha)
            }
        }

        // ── Version tag at bottom ─────────────────────────────────────────
        Text(
            text = "v1.0.0",
            fontSize = 11.sp,
            color = Purple300.copy(alpha = 0.5f),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 28.dp)
                .alpha(dotsAlpha)
        )
    }
}

// ---------------------------------------------------------------------------
// Sub-components
// ---------------------------------------------------------------------------

@Composable
private fun StackBar(width: androidx.compose.ui.unit.Dp, alpha: Float) {
    Box(
        modifier = Modifier
            .width(width)
            .height(4.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(Color.White.copy(alpha = alpha))
    )
}

@Composable
private fun LoadingDot(alpha: Float) {
    Box(
        modifier = Modifier
            .size(6.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(Purple300.copy(alpha = alpha))
    )
}

@Composable
private fun BackgroundGrid() {
    androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
        val step = 48.dp.toPx()
        val lineColor = Color.White.copy(alpha = 0.03f)
        var x = 0f
        while (x < size.width) {
            drawLine(lineColor, androidx.compose.ui.geometry.Offset(x, 0f),
                androidx.compose.ui.geometry.Offset(x, size.height), strokeWidth = 1f)
            x += step
        }
        var y = 0f
        while (y < size.height) {
            drawLine(lineColor, androidx.compose.ui.geometry.Offset(0f, y),
                androidx.compose.ui.geometry.Offset(size.width, y), strokeWidth = 1f)
            y += step
        }
    }
}

// ---------------------------------------------------------------------------
// Preview
// ---------------------------------------------------------------------------
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(navController = rememberNavController())
}