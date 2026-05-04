package com.deepseek.fullystacked.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepseek.fullystacked.data.model.AuthResult
import com.deepseek.fullystacked.data.model.UserProfile
import com.deepseek.fullystacked.data.repository.AuthRepository
import com.deepseek.fullystacked.ui.screens.Auth.ExperienceLevel
import com.deepseek.fullystacked.ui.screens.Auth.RegisterStep
import com.deepseek.fullystacked.ui.screens.Auth.RegisterUiState
import com.deepseek.fullystacked.ui.screens.Auth.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    // ── Login state ────────────────────────────────────────────────────────
    private val _loginState = MutableStateFlow(LoginUiState())
    val loginState: StateFlow<LoginUiState> = _loginState.asStateFlow()

    // ── Register state ─────────────────────────────────────────────────────
    private val _registerState = MutableStateFlow(RegisterUiState())
    val registerState: StateFlow<RegisterUiState> = _registerState.asStateFlow()

    // ── Auth success event ─────────────────────────────────────────────────
    private val _authSuccess = MutableStateFlow(false)
    val authSuccess: StateFlow<Boolean> = _authSuccess.asStateFlow()

    val isLoggedIn: Boolean get() = repository.isLoggedIn
    val currentUid: String? get() = repository.currentUser?.uid

    // ────────────────────────────────────────────────────────────────────────
    // Login
    // ────────────────────────────────────────────────────────────────────────
    fun onLoginEmailChange(v: String)    = _loginState.update { it.copy(email = v, errorMessage = null) }
    fun onLoginPasswordChange(v: String) = _loginState.update { it.copy(password = v, errorMessage = null) }

    fun login() {
        val state = _loginState.value
        if (state.email.isBlank() || state.password.isBlank()) {
            _loginState.update { it.copy(errorMessage = "Please fill in all fields") }
            return
        }
        viewModelScope.launch {
            _loginState.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = repository.login(state.email.trim(), state.password)) {
                is AuthResult.Success -> _authSuccess.value = true
                is AuthResult.Error   -> _loginState.update {
                    it.copy(isLoading = false, errorMessage = result.message)
                }
                else -> Unit
            }
        }
    }

    fun loginWithGoogle(idToken: String) {
        viewModelScope.launch {
            _loginState.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = repository.signInWithGoogle(idToken)) {
                is AuthResult.Success -> _authSuccess.value = true
                is AuthResult.Error   -> _loginState.update {
                    it.copy(isLoading = false, errorMessage = result.message)
                }
                else -> Unit
            }
        }
    }

    // ────────────────────────────────────────────────────────────────────────
    // Register
    // ────────────────────────────────────────────────────────────────────────
    fun onFullNameChange(v: String)       = _registerState.update { it.copy(fullName = v, errorMessage = null) }
    fun onRegEmailChange(v: String)       = _registerState.update { it.copy(email = v, errorMessage = null) }
    fun onPasswordChange(v: String)       = _registerState.update { it.copy(password = v, errorMessage = null) }
    fun onConfirmPasswordChange(v: String)= _registerState.update { it.copy(confirmPassword = v, errorMessage = null) }
    fun onUsernameChange(v: String)       = _registerState.update { it.copy(username = v, errorMessage = null) }
    fun onAvatarSelect(index: Int)        = _registerState.update { it.copy(selectedAvatar = index) }
    fun onExperienceLevelChange(l: ExperienceLevel) = _registerState.update { it.copy(experienceLevel = l) }

    fun nextStep() {
        val s = _registerState.value
        when (s.step) {
            RegisterStep.CREDENTIALS -> {
                if (s.fullName.isBlank() || s.email.isBlank()) {
                    _registerState.update { it.copy(errorMessage = "Please fill in all fields") }
                    return
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(s.email.trim()).matches()) {
                    _registerState.update { it.copy(errorMessage = "Enter a valid email address") }
                    return
                }
                _registerState.update { it.copy(step = RegisterStep.PASSWORD, errorMessage = null) }
            }
            RegisterStep.PASSWORD -> {
                if (s.password.length < 8) {
                    _registerState.update { it.copy(errorMessage = "Password must be at least 8 characters") }
                    return
                }
                if (s.password != s.confirmPassword) {
                    _registerState.update { it.copy(errorMessage = "Passwords do not match") }
                    return
                }
                _registerState.update { it.copy(step = RegisterStep.PROFILE, errorMessage = null) }
            }
            RegisterStep.PROFILE -> finishRegister()
        }
    }

    fun backStep() {
        val s = _registerState.value
        if (s.step.ordinal > 0) {
            val prev = RegisterStep.entries[s.step.ordinal - 1]
            _registerState.update { it.copy(step = prev, errorMessage = null) }
        }
    }

    fun finishRegister() {
        val s = _registerState.value
        if (s.username.isBlank()) {
            _registerState.update { it.copy(errorMessage = "Please enter a username") }
            return
        }
        val profile = UserProfile(
            fullName        = s.fullName.trim(),
            email           = s.email.trim(),
            username        = s.username.trim().lowercase(),
            avatarIndex     = s.selectedAvatar,
            experienceLevel = s.experienceLevel.label
        )
        viewModelScope.launch {
            _registerState.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = repository.register(s.email.trim(), s.password, profile)) {
                is AuthResult.Success -> _authSuccess.value = true
                is AuthResult.Error   -> _registerState.update {
                    it.copy(isLoading = false, errorMessage = result.message)
                }
                else -> Unit
            }
        }
    }

    // ── Password reset ─────────────────────────────────────────────────────
    private val _resetEmailSent = MutableStateFlow(false)
    val resetEmailSent: StateFlow<Boolean> = _resetEmailSent.asStateFlow()
    private val _resetError = MutableStateFlow<String?>(null)
    val resetError: StateFlow<String?> = _resetError.asStateFlow()

    fun sendPasswordReset(email: String) {
        viewModelScope.launch {
            when (val result = repository.sendPasswordReset(email)) {
                is AuthResult.Success -> _resetEmailSent.value = true
                is AuthResult.Error   -> _resetError.value = result.message
                else -> Unit
            }
        }
    }

    fun resetAuthSuccessHandled() { _authSuccess.value = false }

    // ── Sign out ───────────────────────────────────────────────────────────
    fun signOut() { repository.signOut() }
}
