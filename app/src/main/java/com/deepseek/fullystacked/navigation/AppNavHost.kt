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
<<<<<<< HEAD
import com.deepseek.fullystacked.ui.screens.course.app.AppScreen
import com.deepseek.fullystacked.ui.screens.course.web.WebScreen
=======
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
import com.deepseek.fullystacked.ui.screens.course.CourseScreen
import com.deepseek.fullystacked.ui.screens.lesson.LessonScreen
import com.deepseek.fullystacked.ui.screens.home.HomeScreen

// ─────────────────────────────────────────────────────────────────────────────
<<<<<<< HEAD
// Route constants — single source of truth
// ─────────────────────────────────────────────────────────────────────────────


// ─────────────────────────────────────────────────────────────────────────────
// AppNavHost
=======
// ROUTES (define once, keep consistent everywhere)
// ─────────────────────────────────────────────────────────────────────────────

// ─────────────────────────────────────────────────────────────────────────────
// NAV HOST
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
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
<<<<<<< HEAD
        // Navigates to TrackSelection on sign-in, clearing login from back stack
        composable(ROUTE_LOGIN) {
            LoginScreen(

=======
        composable(ROUTE_LOGIN) {
            LoginScreen(
                navController = navController,
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
                onSignInClick = {
                    navController.navigate(ROUTE_TRACK) {
                        popUpTo(ROUTE_LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // ── TRACK SELECTION ───────────────────────────────────────────────
<<<<<<< HEAD
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
=======
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
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
        composable(
            route = "$ROUTE_COURSE/{track}",
            arguments = listOf(
                navArgument("track") { type = NavType.StringType }
            )
        ) { backStackEntry ->
<<<<<<< HEAD
            val track = backStackEntry.arguments?.getString("track") ?: ROUTE_APP
            CourseScreen(
                navController = navController,
                courseTitle = if (track == ROUTE_APP)
                    "Android Development" else "Web Development",
=======

            val track = backStackEntry.arguments?.getString("track") ?: TRACK_APP

            CourseScreen(
                courseTitle = if (track == TRACK_APP)
                    "Android Development"
                else
                    "Web Development",
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
                onLessonClick = { startIndex ->
                    navController.navigate("$ROUTE_LESSON/$startIndex")
                }
            )
        }

<<<<<<< HEAD
        // ── LESSON SCREEN ─────────────────────────────────────────────────
        // Route: "lessons/0" → opens lesson window starting at index 0
=======
        // ── LESSON SCREEN ────────────────────────────────────────────────
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
        composable(
            route = "$ROUTE_LESSON/{startIndex}",
            arguments = listOf(
                navArgument("startIndex") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->
<<<<<<< HEAD
            val startIndex = backStackEntry.arguments?.getInt("startIndex") ?: 0
            LessonScreen(
                navController = navController,
                startLessonIndex = startIndex
=======

            val startIndex = backStackEntry.arguments?.getInt("startIndex") ?: 0

            LessonScreen(
                navController = navController,
                title = "Lessons ${startIndex + 1}-${startIndex + 3}"
>>>>>>> d7441402e8e1d95ddae610f6baec1eb4bb5d1692
            )
        }

        // ── HOME ──────────────────────────────────────────────────────────
        composable(ROUTE_HOME) {
            HomeScreen(navController = navController)
        }
    }
}