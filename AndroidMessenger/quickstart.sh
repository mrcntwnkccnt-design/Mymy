#!/bin/bash
# Quick Start Script for Android Messenger

echo "========================================="
echo "Android Messenger - Quick Start Setup"
echo "========================================="
echo ""

# Check if in correct directory
if [ ! -f "settings.gradle" ]; then
    echo "❌ Error: Please run this script from AndroidMessenger directory"
    echo "   cd /workspaces/Mymy/AndroidMessenger"
    exit 1
fi

echo "✓ Project directory found"
echo ""

# Check Java
if ! command -v java &> /dev/null; then
    echo "❌ Java not found. Install Java 11 or later."
    exit 1
fi
echo "✓ Java installed: $(java -version 2>&1 | head -1)"

# Check Android SDK
if [ -z "$ANDROID_HOME" ]; then
    echo "⚠️  ANDROID_HOME not set. Android Studio should handle this."
else
    echo "✓ Android SDK: $ANDROID_HOME"
fi

echo ""
echo "========================================="
echo "Setup Steps:"
echo "========================================="
echo ""
echo "1. Firebase Configuration (REQUIRED)"
echo "   • Go to https://console.firebase.google.com"
echo "   • Create project: 'android-messenger-app'"
echo "   • Enable Email/Password Authentication"
echo "   • Create Firestore Database"
echo "   • Download google-services.json"
echo "   • Save to: app/google-services.json"
echo ""

echo "2. Build APK"
read -p "   Build now? (y/n) " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    echo "   Building..."
    ./gradlew clean assembleDebug
    if [ -f "app/build/outputs/apk/debug/app-debug.apk" ]; then
        echo "   ✓ APK built successfully!"
        APK_PATH="app/build/outputs/apk/debug/app-debug.apk"
        APK_SIZE=$(ls -lh $APK_PATH | awk '{print $5}')
        echo "   📦 APK: $APK_PATH ($APK_SIZE)"
    else
        echo "   ❌ Build failed"
        exit 1
    fi
else
    echo "   Skipped build"
fi

echo ""
echo "3. Install on Device/Emulator"
read -p "   Install APK? (y/n) " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    if [ -z "$APK_PATH" ]; then
        APK_PATH="app/build/outputs/apk/debug/app-debug.apk"
    fi
    
    # Check for connected devices
    DEVICES=$(adb devices | grep -v "List of devices" | grep -v "^$" | wc -l)
    if [ "$DEVICES" -eq 0 ]; then
        echo "   ❌ No devices connected"
        echo "   • Enable USB Debugging on device"
        echo "   • Connect device via USB"
        echo "   • Or open Android Emulator"
        echo ""
        echo "   Manual install:"
        echo "   adb install -r $APK_PATH"
    else
        echo "   Found $DEVICES device(s). Installing..."
        adb install -r "$APK_PATH"
        echo "   ✓ Installation complete!"
    fi
fi

echo ""
echo "4. Launch App"
read -p "   Launch now? (y/n) " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    echo "   Launching Android Messenger..."
    adb shell am start -n com.example.androidmessenger/.ui.activity.SplashActivity
    echo "   ✓ App launched!"
else
    echo "   Manual launch:"
    echo "   adb shell am start -n com.example.androidmessenger/.ui.activity.SplashActivity"
fi

echo ""
echo "========================================="
echo "✓ Setup Complete!"
echo "========================================="
echo ""
echo "Next Steps:"
echo "1. Create an account and note your User ID"
echo "2. Search for other users by their ID"
echo "3. Send messages in real-time!"
echo ""
echo "For troubleshooting, see SETUP_GUIDE.md"
echo ""
