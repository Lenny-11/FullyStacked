package com.deepseek.fullystacked.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.deepseek.fullystacked.ui.screens.splash.SplashScreen
import com.deepseek.fullystacked.ui.screens.Auth.LoginScreen
import com.deepseek.fullystacked.ui.screens.track.TrackSelectionScreen
import com.deepseek.fullystacked.ui.screens.course.app.AppScreen
import com.deepseek.fullystacked.ui.screens.course.web.WebScreen
import com.deepseek.fullystacked.ui.screens.course.CourseScreen
import com.deepseek.fullystacked.ui.screens.lesson.LessonScreen
import com.deepseek.fullystacked.ui.screens.home.HomeScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_SPLASH   // FIX: start at Splash, not Login
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination
    ) {

        // ── SPLASH ────────────────────────────────────────────────────────
        composable(ROUTE_SPLASH) {
            SplashScreen(navController = navController)
        }

        // ── LOGIN ─────────────────────────────────────────────────────────
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
        composable(ROUTE_TRACK) {
            TrackSelectionScreen(
                onAppClick = { navController.navigate(ROUTE_APP) },
                onWebClick = { navController.navigate(ROUTE_WEB) }
            )
        }

        // ── APP TRACK LANDING ─────────────────────────────────────────────
        composable(ROUTE_APP) {
            AppScreen(navController = navController)
        }

        // ── WEB TRACK LANDING ─────────────────────────────────────────────
        composable(ROUTE_WEB) {
            WebScreen(navController = navController)
        }

        // ── COURSE SCREEN ─────────────────────────────────────────────────
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
