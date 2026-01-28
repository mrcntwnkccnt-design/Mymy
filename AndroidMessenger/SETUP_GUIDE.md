# Android Messenger - Setup & Build Guide

## 🎯 Project Overview
A fully-functional Android messenger application with:
- ✅ Firebase Authentication (Email/Password)
- ✅ User Registration with Unique ID
- ✅ User Search by ID
- ✅ Real-time Messaging
- ✅ Chat List with Last Message
- ✅ Modern Material Design UI

## 📋 Prerequisites

Before building, ensure you have:
1. **Android Studio** 2022.1 or later
2. **Android SDK** API 24+ installed
3. **Gradle** 7.4+ (included with Android Studio)
4. **Kotlin** 1.8.10+ (included with Android Studio)
5. **Firebase Account** (free tier available at https://firebase.google.com)
6. **Device or Emulator** with Android 7.0+ (API 24+)

## 🔧 Firebase Setup (Required)

### Step 1: Create Firebase Project
1. Go to https://console.firebase.google.com
2. Click **Create a project**
3. Name: `android-messenger-app`
4. Accept terms and create

### Step 2: Enable Authentication
1. In Firebase Console, go to **Authentication**
2. Click **Get Started**
3. Click **Email/Password**
4. Enable and Save

### Step 3: Create Firestore Database
1. Go to **Firestore Database**
2. Click **Create Database**
3. Select **Start in test mode** (for development)
4. Choose location closest to you
5. Click **Create**

### Step 4: Download google-services.json
1. In Firebase Console, click **Project Settings** (gear icon)
2. Go to **General** tab
3. Scroll to **Your apps** section
4. Click **Add app** > **Android**
5. Enter Package name: `com.example.androidmessenger`
6. Click **Register app**
7. Click **Download google-services.json**
8. Place file in: `AndroidMessenger/app/google-services.json`

### Step 5: Set Firestore Rules
1. In Firebase Console, go to **Firestore Database** > **Rules**
2. Replace with:
```firestore
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /users/{userId} {
      allow create: if true;
      allow read, write: if request.auth.uid == userId;
    }
    match /chats/{chatId} {
      allow read, write: if request.auth.uid == resource.data.userId || 
                           request.auth.uid == resource.data.otherUserId;
    }
    match /chats/{chatId}/messages/{messageId} {
      allow create: if request.auth.uid == request.resource.data.senderId;
      allow read, write: if request.auth.uid == resource.data.senderId || 
                           request.auth.uid == resource.data.recipientId;
    }
  }
}
```
3. Click **Publish**

## 🚀 Building the APK

### Option 1: Using Android Studio (Recommended)

1. **Open Project**
   - Launch Android Studio
   - File > Open > Select `/workspaces/Mymy/AndroidMessenger`
   - Wait for Gradle sync to complete

2. **Build APK**
   - Menu: Build > Build Bundle(s) / APK(s) > Build APK(s)
   - Wait for build to complete
   - Output: `app/build/outputs/apk/debug/app-debug.apk`

3. **Run on Emulator/Device**
   - Run > Run 'app'
   - Select device/emulator
   - Click OK

### Option 2: Using Command Line

```bash
cd /workspaces/Mymy/AndroidMessenger

# Clean build (optional)
./gradlew clean

# Build debug APK
./gradlew assembleDebug

# Or use build script
chmod +x build.sh
./build.sh
```

Output APKs:
- Debug: `app/build/outputs/apk/debug/app-debug.apk`
- Release: `app/build/outputs/apk/release/app-release-unsigned.apk`

### Option 3: Build Release APK (with signing)

Create keystore for signing:
```bash
keytool -genkey -v -keystore ~/android-release.keystore \
  -keyalg RSA -keysize 2048 -validity 10000 \
  -alias androidmessenger
```

Add to `app/build.gradle`:
```gradle
android {
    signingConfigs {
        release {
            storeFile file(System.getenv("HOME") + "/android-release.keystore")
            storePassword "your-password"
            keyAlias "androidmessenger"
            keyPassword "your-password"
        }
    }
    
    buildTypes {
        release {
            signingConfig signingConfigs.release
        }
    }
}
```

Build:
```bash
./gradlew assembleRelease
```

## 📱 Installing on Device

### Install via ADB (Android Debug Bridge)

1. **Enable Developer Mode on Device**
   - Settings > About phone
   - Tap "Build number" 7 times
   - Go back, enable "USB Debugging"

2. **Connect Device**
   ```bash
   adb devices  # Should show your device
   ```

3. **Install APK**
   ```bash
   adb install -r app/build/outputs/apk/debug/app-debug.apk
   
   # Or use install script
   chmod +x install.sh
   ./install.sh
   ```

4. **Launch App**
   ```bash
   adb shell am start -n com.example.androidmessenger/.ui.activity.SplashActivity
   ```

### Install via Android Studio

- After building, Android Studio shows "Install and Run" button
- Click to install and launch

## 🧪 Testing the App

### Test Account 1
- Email: `user1@example.com`
- Password: `password123`
- Username: `User One`

### Test Account 2
- Email: `user2@example.com`
- Password: `password123`
- Username: `User Two`

### Testing Flow
1. **Register Account 1**
   - Tap "Create Account"
   - Fill email, username, password
   - Note the User ID displayed
   - Tap "Register"

2. **Register Account 2** (on another device/emulator)
   - Repeat with different email/username
   - Note the User ID

3. **Search & Message**
   - In Account 1, go to "Search" tab
   - Enter Account 2's User ID
   - Tap "Start Chat"
   - Type message and send

4. **View Messages**
   - Go to "Chats" tab
   - See conversation
   - Messages update in real-time

## 📁 Project Structure

```
AndroidMessenger/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/androidmessenger/
│   │   │   ├── ui/
│   │   │   │   ├── activity/
│   │   │   │   │   ├── SplashActivity.kt
│   │   │   │   │   ├── AuthActivity.kt
│   │   │   │   │   └── MainActivity.kt
│   │   │   │   ├── fragment/
│   │   │   │   │   ├── ChatsFragment.kt
│   │   │   │   │   ├── SearchFragment.kt
│   │   │   │   │   └── ChatFragment.kt
│   │   │   │   └── adapter/
│   │   │   │       ├── ChatsAdapter.kt
│   │   │   │       └── MessagesAdapter.kt
│   │   │   ├── data/
│   │   │   │   ├── models/
│   │   │   │   │   ├── User.kt
│   │   │   │   │   ├── Message.kt
│   │   │   │   │   └── Chat.kt
│   │   │   │   └── repository/
│   │   │   │       ├── AuthRepository.kt
│   │   │   │       └── ChatRepository.kt
│   │   │   └── databinding/
│   │   │       └── ViewBindings.kt
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   ├── values/
│   │   │   ├── menu/
│   │   │   └── drawable/
│   │   └── AndroidManifest.xml
│   ├── build.gradle
│   ├── proguard-rules.pro
│   └── google-services.json (ADD THIS)
├── build.gradle
├── settings.gradle
├── gradle.properties
├── build.sh
├── install.sh
└── README.md (THIS FILE)
```

## 🔍 Troubleshooting

### "google-services.json not found"
- Make sure file is in: `app/google-services.json`
- Rebuild project: `./gradlew clean build`

### "Build failed - Cannot resolve com.google.firebase"
- Gradle not synced properly
- In Android Studio: File > Sync Now
- Or terminal: `./gradlew sync`

### "APK installation failed"
- Device storage full: `adb shell pm clear com.example.androidmessenger`
- USB debugging disabled: Re-enable in device settings
- Wrong APK: Use debug APK for testing

### "Firebase authentication not working"
- Check `google-services.json` is valid
- Verify Firebase project is set up
- Check internet connection on device

### "Messages not syncing"
- Check Firestore rules are correct
- Verify user IDs are correct
- Check Firebase console for errors

### "App crashes on startup"
- Check Logcat in Android Studio
- Verify all dependencies are installed
- Clear cache: `./gradlew clean build`

## 📚 Key Features Implementation

### 1. Registration & Login (AuthRepository.kt)
- Uses Firebase Authentication
- Stores user data in Firestore
- Auto-generates unique User ID (Firebase UID)

### 2. User Search (ChatRepository.kt)
- Search by User ID (Firebase UID)
- Retrieves user profile from Firestore
- Shows username and ID

### 3. Messaging (ChatRepository.kt)
- Sends messages to Firestore
- Stores in `/chats/{userId}_{recipientId}/messages` collection
- Auto-creates chat on first message

### 4. Chat List (ChatsAdapter.kt)
- Lists all user chats
- Shows last message and timestamp
- Displays unread count

## 🛠️ Development Tips

### Debug App
```bash
# View logs
adb logcat

# Filtered logs
adb logcat | grep androidmessenger
```

### Clean Build
```bash
./gradlew clean build
```

### Update Dependencies
Edit `app/build.gradle` and update version numbers

### Add New Features
1. Create data model in `data/models/`
2. Add repository method in `data/repository/`
3. Create UI fragment in `ui/fragment/`
4. Add to navigation

## 📝 License & Credits

This project is created for educational purposes. Feel free to modify and distribute.

## 🎓 Learning Resources

- [Android Development Guide](https://developer.android.com)
- [Firebase Documentation](https://firebase.google.com/docs)
- [Kotlin Documentation](https://kotlinlang.org)
- [Material Design](https://material.io)

---

**Created:** January 28, 2026  
**Platform:** Android 7.0+ (API 24+)  
**Language:** Kotlin  
**Backend:** Firebase (Auth + Firestore)
