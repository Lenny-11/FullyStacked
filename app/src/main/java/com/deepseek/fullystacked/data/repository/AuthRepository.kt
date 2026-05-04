package com.deepseek.fullystacked.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.deepseek.fullystacked.data.model.AuthResult
import com.deepseek.fullystacked.data.model.DbPaths
import com.deepseek.fullystacked.data.model.UserProfile
import com.google.firebase.auth.FirebaseAuth


class AuthRepository(
    private val auth: FirebaseAuth      = FirebaseAuth.getInstance(),
    private val db: FirebaseDatabase    = FirebaseDatabase.getInstance()
) {

    val currentUser: FirebaseUser? get() = auth.currentUser
    val isLoggedIn: Boolean get() = auth.currentUser != null

    // ── Register with email & password ────────────────────────────────────
    suspend fun register(
        email: String,
        password: String,
        profile: UserProfile
    ): AuthResult {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val uid    = result.user?.uid ?: return AuthResult.Error("Registration failed")
            val userWithUid = profile.copy(uid = uid, email = email)
            // Write to Realtime Database: users/{uid}
            db.getReference(DbPaths.user(uid)).setValue(userWithUid).await()
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    // ── Login with email & password ───────────────────────────────────────
    suspend fun login(email: String, password: String): AuthResult {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Error(e.localizedMessage ?: "Login failed")
        }
    }

    // ── Google Sign-In ────────────────────────────────────────────────────
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun signInWithGoogle(idToken: String): AuthResult {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result     = auth.signInWithCredential(credential).await()
            val user       = result.user ?: return AuthResult.Error("Google sign-in failed")

            val ref      = db.getReference(DbPaths.user(user.uid))
            val snapshot = ref.get().await()

            // Only create profile on first sign-in
            if (!snapshot.exists()) {
                val profile = UserProfile(
                    uid      = user.uid,
                    fullName = user.displayName ?: "",
                    email    = user.email ?: "",
                    username = (user.displayName ?: "user").lowercase().replace(" ", "_")
                )
                ref.setValue(profile).await()
            }
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Error(e.localizedMessage ?: "Google sign-in error")
        }
    }

    // ── Forgot password ───────────────────────────────────────────────────
    suspend fun sendPasswordReset(email: String): AuthResult {
        return try {
            auth.sendPasswordResetEmail(email).await()
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Error(e.localizedMessage ?: "Failed to send reset email")
        }
    }

    // ── Sign out ──────────────────────────────────────────────────────────
    fun signOut() = auth.signOut()

    // ── Fetch user profile ────────────────────────────────────────────────
    suspend fun getUserProfile(uid: String): UserProfile? {
        return try {
            val snapshot = db.getReference(DbPaths.user(uid)).get().await()
            snapshot.getValue(UserProfile::class.java)
        } catch (e: Exception) {
            null
        }
    }

    // ── Update specific fields on user node ───────────────────────────────
    suspend fun updateUserProfile(uid: String, updates: Map<String, Any>): AuthResult {
        return try {
            db.getReference(DbPaths.user(uid)).updateChildren(updates).await()
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Error(e.localizedMessage ?: "Update failed")
        }
    }
}
