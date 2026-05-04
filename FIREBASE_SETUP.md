# 🔥 Firebase Setup Guide for FullyStacked (Realtime Database)

Follow these steps **before building** the app.

---

## Step 1 — Create a Firebase Project

1. Go to [https://console.firebase.google.com](https://console.firebase.google.com)
2. Click **Add project** → name it `FullyStacked`
3. Enable Google Analytics (optional)

---

## Step 2 — Register the Android App

1. In your Firebase project, click **Add app → Android**
2. Package name: `com.deepseek.fullystacked`
3. Nickname: `FullyStacked`
4. Click **Register app**
5. Download `google-services.json`
6. Place it at: `app/google-services.json` (replaces the placeholder)

---

## Step 3 — Enable Authentication

1. Go to **Build → Authentication → Sign-in method**
2. Enable **Email/Password**
3. Enable **Google** (set a support email)

---

## Step 4 — Enable Realtime Database

1. Go to **Build → Realtime Database**
2. Click **Create Database**
3. Choose your region (e.g. `europe-west1` for Africa)
4. Start in **locked mode** (we'll add rules below)

### Realtime Database Rules

Paste these under **Realtime Database → Rules**:

```json
{
  "rules": {
    "users": {
      "$uid": {
        ".read":  "$uid === auth.uid",
        ".write": "$uid === auth.uid",
        "progress": {
          "$courseId": {
            ".read":  "$uid === auth.uid",
            ".write": "$uid === auth.uid"
          }
        }
      }
    }
  }
}
```

---

## Step 5 — Build & Run

Open the project in Android Studio, wait for Gradle sync, then run on any device/emulator running API 24+.

---

## Realtime Database Structure

```
users/
  {uid}/
    uid: "abc123"
    fullName: "Alex Mwangi"
    email: "alex@example.com"
    username: "alex_mwangi"
    avatarIndex: 0
    experienceLevel: "Beginner"
    selectedTrack: "app"
    level: 1
    streakDays: 3
    lastActiveDate: "2026-04-30"
    createdAt: 1714000000000

    progress/
      {courseId}/
        courseId: "android_basics"
        track: "app"
        completedLessonIds: ["lesson_0", "lesson_1"]
        currentLessonIndex: 2
        progressPercent: 0.25
        lastUpdated: 1714000000000
```

---

## Files Changed vs Previous Version

| File | Change |
|---|---|
| `app/build.gradle.kts` | Replaced `firebase-firestore-ktx` → `firebase-database-ktx` |
| `data/model/Models.kt` | Replaced Firestore `Timestamp` with `Long` (epoch ms) + added `DbPaths` helper |
| `data/repository/AuthRepository.kt` | Fully rewritten using `FirebaseDatabase` |
| `data/repository/ProgressRepository.kt` | Fully rewritten using `FirebaseDatabase` |
