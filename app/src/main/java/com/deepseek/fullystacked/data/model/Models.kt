package com.deepseek.fullystacked.data.model

// ─────────────────────────────────────────────────────────────────────────────
// Realtime Database paths (single source of truth)
// ─────────────────────────────────────────────────────────────────────────────
object DbPaths {
    const val USERS    = "users"
    const val PROGRESS = "progress"
    const val COURSES  = "courses"
    const val LESSONS  = "lessons"

    fun user(uid: String)                        = "$USERS/$uid"
    fun userProgress(uid: String)                = "$USERS/$uid/$PROGRESS"
    fun userCourseProgress(uid: String, cId: String) = "$USERS/$uid/$PROGRESS/$cId"
}

// ─────────────────────────────────────────────────────────────────────────────
// UserProfile  →  users/{uid}
// ─────────────────────────────────────────────────────────────────────────────
data class UserProfile(
    val uid: String             = "",
    val fullName: String        = "",
    val email: String           = "",
    val username: String        = "",
    val avatarIndex: Int        = 0,
    val experienceLevel: String = "Beginner",
    val selectedTrack: String   = "",
    val level: Int              = 1,
    val streakDays: Int         = 0,
    val lastActiveDate: String  = "",
    val createdAt: Long         = System.currentTimeMillis()
)

// ─────────────────────────────────────────────────────────────────────────────
// UserProgress  →  users/{uid}/progress/{courseId}
// ─────────────────────────────────────────────────────────────────────────────
data class UserProgress(
    val courseId: String                  = "",
    val track: String                     = "",
    val completedLessonIds: List<String>  = emptyList(),
    val currentLessonIndex: Int           = 0,
    val progressPercent: Float            = 0f,
    val lastUpdated: Long                 = System.currentTimeMillis()
)

// ─────────────────────────────────────────────────────────────────────────────
// Course  →  courses/{courseId}
// ─────────────────────────────────────────────────────────────────────────────
data class Course(
    val id: String          = "",
    val title: String       = "",
    val track: String       = "",
    val description: String = "",
    val totalLessons: Int   = 0,
    val iconEmoji: String   = "📚"
)

// ─────────────────────────────────────────────────────────────────────────────
// Lesson  →  courses/{courseId}/lessons/{lessonId}
// ─────────────────────────────────────────────────────────────────────────────
data class Lesson(
    val id: String       = "",
    val title: String    = "",
    val content: String  = "",
    val index: Int       = 0,
    val durationMinutes: Int = 5
)

// ─────────────────────────────────────────────────────────────────────────────
// Auth result wrapper
// ─────────────────────────────────────────────────────────────────────────────
sealed class AuthResult {
    object Success : AuthResult()
    data class Error(val message: String) : AuthResult()
    object Loading : AuthResult()
}
