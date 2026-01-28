# Project Documentation
# Android Messenger App

## Overview
This is a fully functional Android messenger application built with Kotlin, Firebase, and modern Android development practices.

## Features
1. **User Registration & Login**
   - Email and password authentication using Firebase Auth
   - Users receive a unique ID upon account creation
   - Password must be at least 6 characters

2. **User Search**
   - Search for users by their unique ID
   - View user profile information

3. **Messaging System**
   - Send and receive real-time messages
   - Automatic chat creation when first message is sent
   - Chat list with last message and timestamp

4. **UI Components**
   - Splash screen with auto-login detection
   - Authentication activity for login/register
   - Main activity with bottom navigation
   - Fragments for different sections (Chats, Search, Profile)

## Project Structure
```
AndroidMessenger/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/androidmessenger/
│   │   │   ├── ui/
│   │   │   │   ├── activity/
│   │   │   │   │   ├── SplashActivity
│   │   │   │   │   ├── AuthActivity
│   │   │   │   │   └── MainActivity
│   │   │   │   ├── fragment/
│   │   │   │   │   ├── ChatsFragment
│   │   │   │   │   └── SearchFragment
│   │   │   │   └── adapter/
│   │   │   │       └── ChatsAdapter
│   │   │   ├── data/
│   │   │   │   ├── models/
│   │   │   │   │   ├── User
│   │   │   │   │   ├── Message
│   │   │   │   │   └── Chat
│   │   │   │   └── repository/
│   │   │   │       ├── AuthRepository
│   │   │   │       └── ChatRepository
│   │   │   └── databinding/
│   │   │       └── ViewBindings
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   ├── values/
│   │   │   ├── menu/
│   │   │   └── drawable/
│   │   └── AndroidManifest.xml
│   ├── build.gradle
│   └── proguard-rules.pro
├── build.gradle
├── settings.gradle
└── gradle.properties
```

## Dependencies
- Firebase Authentication: 4.3.15+
- Firebase Firestore: Latest
- Firebase Realtime Database: Latest
- AndroidX: Latest
- Material Design: 1.7.0+
- Kotlin Coroutines: 1.6.4+

## Setup Instructions

### 1. Firebase Setup
1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Create a new project called "android-messenger-app"
3. Enable Email/Password authentication
4. Create a Firestore database
5. Download the `google-services.json` file and place it in `app/`

### 2. Build and Run
1. Open the project in Android Studio
2. Sync Gradle files
3. Build the APK: `./gradlew assembleRelease`
4. Or run on emulator: `./gradlew installDebug`

### 3. Firebase Database Rules
Add these Firestore rules for security:

```firestore
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /users/{userId} {
      allow read, write: if request.auth.uid == userId;
    }
    match /chats/{chatId} {
      allow read, write: if request.auth.uid == resource.data.userId;
    }
    match /chats/{chatId}/messages/{messageId} {
      allow read, write: if request.auth.uid == resource.data.senderId || 
                           request.auth.uid == resource.data.recipientId;
    }
  }
}
```

## How to Use

1. **Register Account**
   - Tap "Create Account"
   - Enter email, username, password
   - Account created with unique ID

2. **Login**
   - Enter email and password
   - Auto-login on next launch if session exists

3. **Search Users**
   - Go to "Search" tab
   - Enter user ID to search
   - View user profile

4. **Send Messages**
   - Click "Start Chat"
   - Type message and send
   - Messages appear in real-time

5. **View Chats**
   - Go to "Chats" tab
   - See all your conversations
   - Click to open chat thread

## Build & Deploy

### Generate APK
```bash
cd /workspaces/Mymy/AndroidMessenger
./gradlew assembleDebug      # Debug APK
./gradlew assembleRelease    # Release APK (requires signing)
```

### Sign APK for Release
1. Create keystore: `keytool -genkey -v -keystore release.keystore -keyalg RSA -keysize 2048 -validity 10000 -alias release`
2. Add to `app/build.gradle`:
```gradle
signingConfigs {
    release {
        storeFile file('release.keystore')
        storePassword 'password'
        keyAlias 'release'
        keyPassword 'password'
    }
}
```

## Requirements
- Android SDK 24+ (API 24)
- Kotlin 1.8.10+
- Firebase Account
- Android Studio 2022.1+

## License
This project is created for educational purposes.

## Troubleshooting
- **Firebase errors**: Ensure `google-services.json` is placed correctly
- **Build errors**: Run `./gradlew clean build`
- **Runtime errors**: Check Logcat for detailed error messages
- **Network issues**: Ensure app has internet permission in manifest
