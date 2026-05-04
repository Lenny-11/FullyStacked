package com.deepseek.fullystacked.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepseek.fullystacked.data.model.UserProfile
import com.deepseek.fullystacked.data.model.UserProgress
import com.deepseek.fullystacked.data.repository.AuthRepository
import com.deepseek.fullystacked.data.repository.ProgressRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authRepo: AuthRepository         = AuthRepository(),
    private val progressRepo: ProgressRepository = ProgressRepository()
) : ViewModel() {

    private val _profile = MutableStateFlow<UserProfile?>(null)
    val profile: StateFlow<UserProfile?> = _profile.asStateFlow()

    private val _allProgress = MutableStateFlow<List<UserProgress>>(emptyList())
    val allProgress: StateFlow<List<UserProgress>> = _allProgress.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val currentUid: String? get() = authRepo.currentUser?.uid

    fun loadProfile() {
        val uid = currentUid ?: return
        viewModelScope.launch {
            _isLoading.value = true
            _profile.value     = authRepo.getUserProfile(uid)
            _allProgress.value = progressRepo.getAllProgress(uid)
            _isLoading.value   = false
        }
    }

    // Computed overall progress across all courses
    val overallProgress: Float
        get() {
            val list = _allProgress.value
            if (list.isEmpty()) return 0f
            return list.map { it.progressPercent }.average().toFloat()
        }

    val totalCompletedLessons: Int
        get() = _allProgress.value.sumOf { it.completedLessonIds.size }
}
