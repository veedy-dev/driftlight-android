# Driftlight Android

Driftlight is a controller-first, Moonlight-compatible Android streaming client designed for landscape handhelds such as the AYN Odin 3.

It keeps the mature Moonlight/Artemis streaming stack while replacing the legacy client shell with a dark Material 3 interface and fast desktop controls.

## Highlights

- Minimal in-stream quick-control rail
- Full desktop keyboard with Esc, F1–F12, navigation, arrow, Win, Ctrl, Alt, and sticky modifiers
- One-tap Alt+Tab, Android keyboard, and Task Manager shortcuts
- Toggleable quick controls under **Settings → UI Settings**
- Controller-readable host and app libraries
- Modern pairing, loading, error, and settings surfaces
- Custom virtual buttons, gamepad layouts, trackpad modes, clipboard sync, external display, and Apollo integration inherited from Artemis
- Moonlight-compatible host support, including Moonshine, Sunshine, and Apollo

## In-stream controls

When **Show in-stream quick controls** is enabled, tap the small handle on the right edge of a stream. The rail exposes:

1. Full keys
2. Alt + Tab
3. Android keyboard
4. Task manager
5. More

The rail collapses on Back or an outside tap, does not capture gameplay touches while closed, and hides during Picture-in-Picture.

Existing gestures remain available in trackpad mode:

- Three-finger tap: Android software keyboard
- Four-finger tap: full desktop keyboard

## Install

The installable debug-signed APK is produced by the **Debug APK** GitHub Actions workflow. Download the `Driftlight-android-debug` artifact from the latest successful run and install it on Android.

The debug application ID is `com.veedy.driftlight.debug`, so it installs separately from Moonlight and Artemis.

## Build

Requirements:

- JDK 17
- Android SDK 35
- Android NDK `27.0.12077973`

```bash
git submodule update --init --recursive
./gradlew assembleNonRoot_gameDebug
```

The APK is written to:

```text
app/build/outputs/apk/nonRoot_game/debug/app-nonRoot_game-debug.apk
```

The NDK build cannot handle project paths containing spaces. Use a space-free checkout path or the GitHub Actions workflow.

## Lineage and license

Driftlight is based on:

- [Moonlight Android](https://github.com/moonlight-stream/moonlight-android)
- [Artemis / Moonlight Noir](https://github.com/ClassicOldSong/moonlight-android)

Original Moonlight authors include Cameron Gutman, Diego Waxemberg, Aaron Neyer, and Andrew Hennessy. Existing upstream copyright and license terms remain in force.
