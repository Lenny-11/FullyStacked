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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deepseek.fullystacked.ui.screens.pdf.PdfUtils
import com.deepseek.fullystacked.ui.theme.FullyStackedTheme

// ─────────────────────────────────────────────────────────────────────────────
// Dark-mode accent colours
// ─────────────────────────────────────────────────────────────────────────────
private val Purple500  = Color(0xFF7C74F0)
private val Purple200  = Color(0xFF3D3870)
private val Purple900  = Color(0xFF1E1B3A)
private val Teal500    = Color(0xFF2DD4BB)
private val Teal200    = Color(0xFF1A4A42)
private val Teal900    = Color(0xFF0D2420)

// PDF page indices (0-based) that best introduce each track
private const val APP_TRACK_PDF_PAGE = 14   // Chapter 1: The Modern Web
private const val WEB_TRACK_PDF_PAGE = 114  // Chapter 6: Front End

// ─────────────────────────────────────────────────────────────────────────────
// TrackSelectionScreen
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun TrackSelectionScreen(
    onAppClick: () -> Unit = {},
    onWebClick: () -> Unit = {}
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        // ── Header ────────────────────────────────────────────────────────
        Text(
            text = "Choose your path",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Start your full-stack journey",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 6.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // PDF hint
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Purple500.copy(alpha = 0.10f))
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("📖", fontSize = 16.sp)
            Spacer(Modifier.width(8.dp))
            Text(
                "Tap a track card to also open the relevant chapter in your learning PDF",
                fontSize = 12.sp,
                color = Purple500,
                lineHeight = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // ── App Development card ──────────────────────────────────────────
        TrackCard(
            emoji = "📱",
            title = "App Development",
            subtitle = "Kotlin · Android · Jetpack Compose · Ktor",
            highlights = listOf("12 lessons", "4 modules", "6h content"),
            bgColor    = Purple900,
            borderColor = Purple200,
            accentColor = Purple500,
            pdfHint    = "Opens: Chapter 1 – The Modern Web",
            onClick = {
                PdfUtils.openPdfAtPage(context, APP_TRACK_PDF_PAGE)
                onAppClick()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ── Web Development card ──────────────────────────────────────────
        TrackCard(
            emoji = "🌐",
            title = "Web Development",
            subtitle = "HTML · CSS · JavaScript · PHP · React · Node",
            highlights = listOf("12 lessons", "4 modules", "8h content"),
            bgColor    = Teal900,
            borderColor = Teal200,
            accentColor = Teal500,
            pdfHint    = "Opens: Chapter 6 – Front End",
            onClick = {
                PdfUtils.openPdfAtPage(context, WEB_TRACK_PDF_PAGE)
                onWebClick()
            }
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// TrackCard
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun TrackCard(
    emoji: String,
    title: String,
    subtitle: String,
    highlights: List<String>,
    bgColor: Color,
    borderColor: Color,
    accentColor: Color,
    pdfHint: String = "",
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(bgColor, RoundedCornerShape(16.dp))
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(20.dp)
    ) {
        Column {
            // Title row
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(emoji, fontSize = 28.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = subtitle,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Highlight chips
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                highlights.forEach { chip ->
                    Box(
                        modifier = Modifier
                            .background(accentColor.copy(alpha = 0.15f), RoundedCornerShape(20.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(chip, fontSize = 11.sp, color = accentColor, fontWeight = FontWeight.Medium)
                    }
                }
            }

            if (pdfHint.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("📖", fontSize = 12.sp)
                    Spacer(Modifier.width(4.dp))
                    Text(pdfHint, fontSize = 11.sp, color = accentColor.copy(alpha = 0.8f))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // CTA row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "View track  →",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = accentColor
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TrackSelectionScreenPreview() {
    FullyStackedTheme {
        TrackSelectionScreen()
    }
}
