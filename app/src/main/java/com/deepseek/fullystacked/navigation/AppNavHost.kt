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
import com.deepseek.fullystacked.ui.screens.course.app.AppScreen
import com.deepseek.fullystacked.ui.screens.course.web.WebScreen
import com.deepseek.fullystacked.ui.screens.course.CourseScreen
import com.deepseek.fullystacked.ui.screens.lesson.LessonScreen
import com.deepseek.fullystacked.ui.screens.home.HomeScreen

// ─────────────────────────────────────────────────────────────────────────────
// Route constants — single source of truth
// ─────────────────────────────────────────────────────────────────────────────


// ─────────────────────────────────────────────────────────────────────────────
// AppNavHost
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
        // Navigates to TrackSelection on sign-in, clearing login from back stack
        composable(ROUTE_LOGIN) {
            LoginScreen(

                onSignInClick = {
                    navController.navigate(ROUTE_TRACK) {
                        popUpTo(ROUTE_LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // ── TRACK SELECTION ───────────────────────────────────────────────
        // User picks App or Web. Each goes to the track landing screen first,
        // NOT directly to CourseScreen — so users see what they're signing up for.
        composable(ROUTE_TRACK) {
            TrackSelectionScreen(
                onAppClick = { navController.navigate(ROUTE_APP) },
                onWebClick = { navController.navigate(ROUTE_WEB) }
            )
        }

        // ── APP TRACK LANDING ─────────────────────────────────────────────
        // Shows overview of Android/Kotlin track with CTA → CourseScreen
        composable(ROUTE_APP) {
            AppScreen(navController = navController)
        }

        // ── WEB TRACK LANDING ─────────────────────────────────────────────
        // Shows overview of web track with CTA → CourseScreen
        composable(ROUTE_WEB) {
            WebScreen(navController = navController)
        }

        // ── COURSE SCREEN ─────────────────────────────────────────────────
        // Route: "course/app"  or  "course/web"
        composable(
            route = "$ROUTE_COURSE/{track}",
            arguments = listOf(
                navArgument("track") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val track = backStackEntry.arguments?.getString("track") ?: ROUTE_APP
            CourseScreen(
                navController = navController,
                courseTitle = if (track == ROUTE_APP)
                    "Android Development" else "Web Development",
                onLessonClick = { startIndex ->
                    navController.navigate("$ROUTE_LESSON/$startIndex")
                }
            )
        }

        // ── LESSON SCREEN ─────────────────────────────────────────────────
        // Route: "lessons/0" → opens lesson window starting at index 0
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
                startLessonIndex = startIndex
            )
        }

        // ── HOME ──────────────────────────────────────────────────────────
        composable(ROUTE_HOME) {
            HomeScreen(navController = navController)
        }
    }
}