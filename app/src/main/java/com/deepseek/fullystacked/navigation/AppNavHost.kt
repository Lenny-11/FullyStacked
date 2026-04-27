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



// ------------------------------------------------------------------
// NAV HOST
// ------------------------------------------------------------------
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

        // ── LOGIN ─────────────────────────────
        composable(ROUTE_LOGIN) {
            LoginScreen(
                onSignInClick = {
                    navController.navigate(ROUTE_TRACK) {
                        popUpTo(ROUTE_LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // ── TRACK SELECTION ───────────────────
        composable(ROUTE_TRACK) {
            TrackSelectionScreen(
                onAppClick = {
                    navController.navigate("$ROUTE_COURSE/app")
                },
                onWebClick = {
                    navController.navigate("$ROUTE_COURSE/web")
                }
            )
        }

        // ── COURSE SCREEN ─────────────────────
        composable(
            route = "$ROUTE_COURSE/{track}",
            arguments = listOf(
                navArgument("track") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val track = backStackEntry.arguments?.getString("track") ?: "app"

            CourseScreen(
                courseTitle = if (track == "app")
                    "Android Development"
                else
                    "Web Development",

                onLessonClick = { lessonId ->
                    navController.navigate("$ROUTE_LESSON/$lessonId")
                }
            )
        }

        // ── LESSON SCREEN ─────────────────────
        composable(
            route = "$ROUTE_LESSON/{lessonId}",
            arguments = listOf(
                navArgument("lessonId") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val lessonId = backStackEntry.arguments?.getString("lessonId") ?: ""

            LessonScreen(
                title = "Lesson $lessonId"
            )
        }

        // ── HOME (optional dashboard screen) ───
        composable(route = ROUTE_HOME) {
            HomeScreen()
        }
    }
}