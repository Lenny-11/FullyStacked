package com.deepseek.fullystacked.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.deepseek.fullystacked.ui.screens.Auth.LoginScreen
import com.deepseek.fullystacked.ui.screens.track.TrackSelectionScreen
import com.deepseek.fullystacked.ui.screens.course.CourseScreen
import com.deepseek.fullystacked.ui.screens.lesson.LessonScreen
import com.deepseek.fullystacked.ui.screens.home.HomeScreen

// ─────────────────────────────────────────────────────────────────────────────
// ROUTES (define once, keep consistent everywhere)
// ─────────────────────────────────────────────────────────────────────────────

// ─────────────────────────────────────────────────────────────────────────────
// NAV HOST
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_LOGIN
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination
    ) {

        // ── LOGIN ─────────────────────────────────────────────────────────
        composable(ROUTE_LOGIN) {
            LoginScreen(
                navController = navController,
                onSignInClick = {
                    navController.navigate(ROUTE_TRACK) {
                        popUpTo(ROUTE_LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // ── TRACK SELECTION ───────────────────────────────────────────────
        composable(ROUTE_TRACK) {
            TrackSelectionScreen(
                onAppClick = {
                    navController.navigate("$ROUTE_COURSE/$TRACK_APP")
                },
                onWebClick = {
                    navController.navigate("$ROUTE_COURSE/$TRACK_WEB")
                }
            )
        }

        // ── COURSE SCREEN (single clean version) ──────────────────────────
        composable(
            route = "$ROUTE_COURSE/{track}",
            arguments = listOf(
                navArgument("track") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val track = backStackEntry.arguments?.getString("track") ?: TRACK_APP

            CourseScreen(
                courseTitle = if (track == TRACK_APP)
                    "Android Development"
                else
                    "Web Development",
                onLessonClick = { startIndex ->
                    navController.navigate("$ROUTE_LESSON/$startIndex")
                }
            )
        }

        // ── LESSON SCREEN ────────────────────────────────────────────────
        composable(
            route = "$ROUTE_LESSON/{startIndex}",
            arguments = listOf(
                navArgument("startIndex") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->

            val startIndex = backStackEntry.arguments?.getInt("startIndex") ?: 0

            LessonScreen(
                navController = navController,
                title = "Lessons ${startIndex + 1}-${startIndex + 3}"
            )
        }

        // ── HOME ──────────────────────────────────────────────────────────
        composable(ROUTE_HOME) {
            HomeScreen(navController = navController)
        }
    }
}