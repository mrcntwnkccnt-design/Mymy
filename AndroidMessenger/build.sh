#!/bin/bash

# Android Messenger Build Script

echo "Building Android Messenger APK..."

# Navigate to project directory
cd "$(dirname "$0")"

# Clean build
echo "Cleaning previous builds..."
./gradlew clean

# Build debug APK
echo "Building debug APK..."
./gradlew assembleDebug

# Check if build was successful
if [ -f "app/build/outputs/apk/debug/app-debug.apk" ]; then
    echo "✓ Debug APK built successfully!"
    echo "Location: app/build/outputs/apk/debug/app-debug.apk"
    echo ""
    echo "To install on device:"
    echo "adb install app/build/outputs/apk/debug/app-debug.apk"
else
    echo "✗ Build failed. Check logs above."
    exit 1
fi

# Build release APK
echo ""
echo "Building release APK..."
./gradlew assembleRelease

if [ -f "app/build/outputs/apk/release/app-release-unsigned.apk" ]; then
    echo "✓ Release APK built successfully!"
    echo "Location: app/build/outputs/apk/release/app-release-unsigned.apk"
    echo ""
    echo "Note: Release APK needs to be signed before installation."
else
    echo "✗ Release build failed."
fi
