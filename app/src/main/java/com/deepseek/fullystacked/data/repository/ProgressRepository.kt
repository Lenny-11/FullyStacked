package com.deepseek.fullystacked.data.repository

import com.deepseek.fullystacked.data.model.DbPaths
import com.deepseek.fullystacked.data.model.UserProgress
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class ProgressRepository(
    private val db: FirebaseDatabase = FirebaseDatabase.getInstance()
) {

    // ── Get progress for one course ───────────────────────────────────────
    suspend fun getProgress(uid: String, courseId: String): UserProgress? {
        return try {
            val snapshot = db.getReference(DbPaths.userCourseProgress(uid, courseId)).get().await()
            snapshot.getValue(UserProgress::class.java)
        } catch (e: Exception) {
            null
        }
    }

    // ── Mark a lesson complete ────────────────────────────────────────────
    suspend fun markLessonComplete(
        uid: String,
        courseId: String,
        track: String,
        lessonId: String,
        totalLessons: Int
    ) {
        val ref      = db.getReference(DbPaths.userCourseProgress(uid, courseId))
        val snapshot = ref.get().await()
        val existing = snapshot.getValue(UserProgress::class.java)
            ?: UserProgress(courseId = courseId, track = track)

        val updatedIds  = (existing.completedLessonIds + lessonId).distinct()
        val nextIndex   = updatedIds.size
        val percent     = updatedIds.size.toFloat() / totalLessons.coerceAtLeast(1)

        val updated = existing.copy(
            completedLessonIds = updatedIds,
            currentLessonIndex = nextIndex,
            progressPercent    = percent,
            lastUpdated        = System.currentTimeMillis()
        )
        ref.setValue(updated).await()
    }

    // ── Get all progress entries for a user ───────────────────────────────
    suspend fun getAllProgress(uid: String): List<UserProgress> {
        return try {
            val snapshot = db.getReference(DbPaths.userProgress(uid)).get().await()
            snapshot.children.mapNotNull { it.getValue(UserProgress::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
