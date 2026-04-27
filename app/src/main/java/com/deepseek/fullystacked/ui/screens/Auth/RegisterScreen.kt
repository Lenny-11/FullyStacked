@file:OptIn(ExperimentalMaterial3Api::class)

package com.deepseek.fullystacked.ui.screens.Auth



import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



// ---------------------------------------------------------------------------
// Brand colours
// ---------------------------------------------------------------------------
private val Purple500 = Color(0xFF534AB7)
private val Purple200 = Color(0xFFAFA9EC)
private val Purple100 = Color(0xFFCECBF6)
private val Purple50  = Color(0xFFEEEDFE)

// ---------------------------------------------------------------------------
// Enums & data classes
// ---------------------------------------------------------------------------
enum class RegisterStep { CREDENTIALS, PASSWORD, PROFILE }

enum class ExperienceLevel(val label: String) {
    BEGINNER("Beginner"),
    INTERMEDIATE("Intermediate"),
    ADVANCED("Advanced")
}

data class AvatarOption(val initials: String, val bg: Color, val text: Color)

val avatarOptions = listOf(
    AvatarOption("AX", Color(0xFFCECBF6), Color(0xFF3C3489)),
    AvatarOption("BK", Color(0xFF9FE1CB), Color(0xFF085041)),
    AvatarOption("CJ", Color(0xFFFAC775), Color(0xFF633806)),
    AvatarOption("DL", Color(0xFFF4C0D1), Color(0xFF72243E))
)

data class RegisterUiState(
    val step: RegisterStep = RegisterStep.CREDENTIALS,
    // Step 1
    val fullName: String = "",
    val email: String = "",
    // Step 2
    val password: String = "",
    val confirmPassword: String = "",
    // Step 3
    val username: String = "",
    val selectedAvatar: Int = 0,
    val experienceLevel: ExperienceLevel = ExperienceLevel.BEGINNER,
    // Meta
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

// ---------------------------------------------------------------------------
// Password strength helpers
// ---------------------------------------------------------------------------
enum class PasswordStrength(val label: String, val color: Color, val filledSegments: Int) {
    WEAK("Weak — add more characters", Color(0xFFE24B4A), 1),
    MEDIUM("Medium — add symbols to improve", Color(0xFFEF9F27), 2),
    STRONG("Strong password", Color(0xFF1D9E75), 3),
    VERY_STRONG("Very strong", Color(0xFF1D9E75), 4)
}

fun evaluateStrength(password: String): PasswordStrength? {
    if (password.isEmpty()) return null
    var score = 0
    if (password.length >= 8) score++
    if (password.any { it.isUpperCase() }) score++
    if (password.any { it.isDigit() }) score++
    if (password.any { !it.isLetterOrDigit() }) score++
    return when (score) {
        1 -> PasswordStrength.WEAK
        2 -> PasswordStrength.MEDIUM
        3 -> PasswordStrength.STRONG
        else -> PasswordStrength.VERY_STRONG
    }
}

// ---------------------------------------------------------------------------
// Root composable
// ---------------------------------------------------------------------------
@Composable
fun RegisterScreen(
    uiState: RegisterUiState = RegisterUiState(),
    onFullNameChange: (String) -> Unit = {},
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onConfirmPasswordChange: (String) -> Unit = {},
    onUsernameChange: (String) -> Unit = {},
    onAvatarSelect: (Int) -> Unit = {},
    onExperienceLevelChange: (ExperienceLevel) -> Unit = {},
    onNextStep: () -> Unit = {},
    onBackStep: () -> Unit = {},
    onFinish: () -> Unit = {},
    onSignInClick: () -> Unit = {}
) {
    val currentStepIndex = uiState.step.ordinal
    val totalSteps = RegisterStep.entries.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        // ── Top bar with back button ──────────────────────────────────────
        Box(modifier = Modifier.fillMaxWidth()) {
            if (currentStepIndex > 0) {
                IconButton(
                    onClick = onBackStep,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = "Go back",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // ── Logo ──────────────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .size(52.dp)
                .background(color = Purple50, shape = RoundedCornerShape(14.dp))
                .border(0.5.dp, Purple100, RoundedCornerShape(14.dp)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .background(color = Purple500, shape = RoundedCornerShape(7.dp))
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ── Step title ────────────────────────────────────────────────────
        val stepTitle = when (uiState.step) {
            RegisterStep.CREDENTIALS -> "Create account"
            RegisterStep.PASSWORD    -> "Secure your account"
            RegisterStep.PROFILE     -> "Set up your profile"
        }
        val stepSubtitle = when (uiState.step) {
            RegisterStep.CREDENTIALS -> "Start your dev journey"
            RegisterStep.PASSWORD    -> "Choose a strong password"
            RegisterStep.PROFILE     -> "Almost there, step 3 of 3"
        }

        Text(stepTitle, fontSize = 20.sp, fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground)
        Text(stepSubtitle, fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 3.dp))

        Spacer(modifier = Modifier.height(20.dp))

        // ── Step progress dots ────────────────────────────────────────────
        StepProgressBar(currentStep = currentStepIndex, totalSteps = totalSteps)

        Spacer(modifier = Modifier.height(24.dp))

        // ── Animated step content ─────────────────────────────────────────
        AnimatedContent(
            targetState = uiState.step,
            transitionSpec = {
                val forward = targetState.ordinal > initialState.ordinal
                (slideInHorizontally { if (forward) it else -it } + fadeIn()) togetherWith
                        (slideOutHorizontally { if (forward) -it else it } + fadeOut())
            },
            label = "register_step_anim"
        ) { step ->
            when (step) {
                RegisterStep.CREDENTIALS -> StepCredentials(
                    fullName = uiState.fullName,
                    email = uiState.email,
                    onFullNameChange = onFullNameChange,
                    onEmailChange = onEmailChange,
                    onNext = onNextStep,
                    isLoading = uiState.isLoading,
                    errorMessage = uiState.errorMessage,
                    onSignInClick = onSignInClick
                )
                RegisterStep.PASSWORD -> StepPassword(
                    password = uiState.password,
                    confirmPassword = uiState.confirmPassword,
                    onPasswordChange = onPasswordChange,
                    onConfirmPasswordChange = onConfirmPasswordChange,
                    onNext = onNextStep,
                    isLoading = uiState.isLoading,
                    errorMessage = uiState.errorMessage
                )
                RegisterStep.PROFILE -> StepProfile(
                    username = uiState.username,
                    selectedAvatar = uiState.selectedAvatar,
                    experienceLevel = uiState.experienceLevel,
                    onUsernameChange = onUsernameChange,
                    onAvatarSelect = onAvatarSelect,
                    onExperienceLevelChange = onExperienceLevelChange,
                    onFinish = onFinish,
                    isLoading = uiState.isLoading,
                    errorMessage = uiState.errorMessage
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Step 1 — Credentials
// ---------------------------------------------------------------------------
@Composable
private fun StepCredentials(
    fullName: String,
    email: String,
    onFullNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onNext: () -> Unit,
    isLoading: Boolean,
    errorMessage: String?,
    onSignInClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier.fillMaxWidth()) {

        RegFieldLabel("Full name")
        OutlinedTextField(
            value = fullName,
            onValueChange = onFullNameChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Alex Mwangi", fontSize = 14.sp) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            colors = regTextFieldColors()
        )

        Spacer(modifier = Modifier.height(14.dp))

        RegFieldLabel("Email")
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("you@example.com", fontSize = 14.sp) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus(); onNext()
            }),
            colors = regTextFieldColors()
        )

        ErrorText(errorMessage)
        Spacer(modifier = Modifier.height(20.dp))

        RegPrimaryButton(label = "Continue", isLoading = isLoading, onClick = onNext)

        Spacer(modifier = Modifier.height(12.dp))
        TermsText()

        Spacer(modifier = Modifier.height(24.dp))
        SignInRow(onSignInClick = onSignInClick)
    }
}

// ---------------------------------------------------------------------------
// Step 2 — Password
// ---------------------------------------------------------------------------
@Composable
private fun StepPassword(
    password: String,
    confirmPassword: String,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onNext: () -> Unit,
    isLoading: Boolean,
    errorMessage: String?
) {
    val focusManager = LocalFocusManager.current
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmVisible by remember { mutableStateOf(false) }
    val strength = remember(password) { evaluateStrength(password) }

    Column(modifier = Modifier.fillMaxWidth()) {

        RegFieldLabel("Password")
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Min. 8 characters", fontSize = 14.sp) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            visualTransformation = if (passwordVisible)
                VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            trailingIcon = {
                PasswordToggle(visible = passwordVisible) { passwordVisible = !passwordVisible }
            },
            colors = regTextFieldColors()
        )

        // Strength bar
        if (strength != null) {
            Spacer(modifier = Modifier.height(6.dp))
            PasswordStrengthBar(strength = strength)
            Text(
                text = strength.label,
                fontSize = 11.sp,
                color = strength.color,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        RegFieldLabel("Confirm password")
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Re-enter password", fontSize = 14.sp) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            visualTransformation = if (confirmVisible)
                VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus(); onNext()
            }),
            trailingIcon = {
                PasswordToggle(visible = confirmVisible) { confirmVisible = !confirmVisible }
            },
            colors = regTextFieldColors()
        )

        ErrorText(errorMessage)
        Spacer(modifier = Modifier.height(20.dp))

        RegPrimaryButton(label = "Continue", isLoading = isLoading, onClick = onNext)
    }
}

// ---------------------------------------------------------------------------
// Step 3 — Profile
// ---------------------------------------------------------------------------
@Composable
private fun StepProfile(
    username: String,
    selectedAvatar: Int,
    experienceLevel: ExperienceLevel,
    onUsernameChange: (String) -> Unit,
    onAvatarSelect: (Int) -> Unit,
    onExperienceLevelChange: (ExperienceLevel) -> Unit,
    onFinish: () -> Unit,
    isLoading: Boolean,
    errorMessage: String?
) {
    var levelDropdownExpanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {

        // Avatar picker
        Text(
            text = "Pick an avatar",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            avatarOptions.forEachIndexed { index, avatar ->
                val isSelected = selectedAvatar == index
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(avatar.bg)
                        .then(
                            if (isSelected) Modifier.border(2.dp, Purple500, CircleShape)
                            else Modifier
                        )
                        .clickable { onAvatarSelect(index) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = avatar.initials,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = avatar.text
                    )
                }
                if (index < avatarOptions.lastIndex) Spacer(modifier = Modifier.width(10.dp))
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        RegFieldLabel("Username")
        OutlinedTextField(
            value = username,
            onValueChange = onUsernameChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("@your_handle", fontSize = 14.sp) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            supportingText = {
                Text("This is how others will find you", fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            colors = regTextFieldColors()
        )

        Spacer(modifier = Modifier.height(10.dp))

        RegFieldLabel("Experience level")
        ExposedDropdownMenuBox(
            expanded = levelDropdownExpanded,
            onExpandedChange = { levelDropdownExpanded = it }
        ) {
            OutlinedTextField(
                value = experienceLevel.label,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                shape = RoundedCornerShape(12.dp),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = levelDropdownExpanded)
                },
                colors = regTextFieldColors()
            )
            ExposedDropdownMenu(
                expanded = levelDropdownExpanded,
                onDismissRequest = { levelDropdownExpanded = false }
            ) {
                ExperienceLevel.entries.forEach { level ->
                    DropdownMenuItem(
                        text = { Text(level.label, fontSize = 14.sp) },
                        onClick = {
                            onExperienceLevelChange(level)
                            levelDropdownExpanded = false
                        }
                    )
                }
            }
        }

        ErrorText(errorMessage)
        Spacer(modifier = Modifier.height(20.dp))

        RegPrimaryButton(label = "Finish setup", isLoading = isLoading, onClick = onFinish)
    }
}

// ---------------------------------------------------------------------------
// Shared sub-components
// ---------------------------------------------------------------------------

@Composable
private fun StepProgressBar(currentStep: Int, totalSteps: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        repeat(totalSteps) { index ->
            Box(
                modifier = Modifier
                    .width(if (index == currentStep) 28.dp else 20.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(
                        when {
                            index < currentStep  -> Purple500
                            index == currentStep -> Purple200
                            else                 -> MaterialTheme.colorScheme.surfaceVariant
                        }
                    )
            )
        }
    }
}

@Composable
private fun PasswordStrengthBar(strength: PasswordStrength) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        repeat(4) { index ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(
                        if (index < strength.filledSegments) strength.color
                        else MaterialTheme.colorScheme.surfaceVariant
                    )
            )
        }
    }
}

@Composable
private fun PasswordToggle(visible: Boolean, onToggle: () -> Unit) {
    IconButton(onClick = onToggle) {
        Icon(
            imageVector = if (visible) {
                Icons.Outlined.Visibility
            } else {
                Icons.Outlined.VisibilityOff
            },
            contentDescription = if (visible) "Hide password" else "Show password",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun RegPrimaryButton(label: String, isLoading: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = !isLoading,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Purple500)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(20.dp),
                strokeWidth = 2.dp
            )
        } else {
            Text(label, fontSize = 15.sp, fontWeight = FontWeight.Medium, color = Color.White)
        }
    }
}

@Composable
private fun RegFieldLabel(text: String) {
    Text(
        text = text,
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp)
    )
}

@Composable
private fun ErrorText(message: String?) {
    if (message != null) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun TermsText() {
    Text(
        text = "By continuing you agree to our Terms of Service and Privacy Policy",
        fontSize = 11.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center,
        lineHeight = 16.sp,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun SignInRow(onSignInClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Already have an account? ",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Sign in",
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Purple500,
            modifier = Modifier.clickable(onClick = onSignInClick)
        )
    }
}

@Composable
private fun regTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = Purple500,
    focusedContainerColor = Purple50,
    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
    cursorColor = Purple500
)

// ---------------------------------------------------------------------------
// Preview
// ---------------------------------------------------------------------------
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    MaterialTheme {
        RegisterScreen()
    }
}