#!/bin/bash

# Android Messenger Installation Script

APK_PATH="app/build/outputs/apk/debug/app-debug.apk"

if [ ! -f "$APK_PATH" ]; then
    echo "APK not found. Build first using: ./build.sh"
    exit 1
fi

echo "Installing APK to connected Android device..."
adb install -r "$APK_PATH"

if [ $? -eq 0 ]; then
    echo "✓ Installation successful!"
    echo ""
    echo "To launch the app:"
    echo "adb shell am start -n com.example.androidmessenger/.ui.activity.SplashActivity"
else
    echo "✗ Installation failed."
    echo "Make sure device is connected: adb devices"
fi
