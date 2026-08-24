<p align="center">
  <img src="store-assets/driftlight-icon.svg" width="96" alt="Driftlight Signal Relay icon">
</p>

<h1 align="center">Driftlight</h1>

<p align="center">
  Moonlight-compatible streaming for Android, with touch, controller, mouse, keyboard, and desktop controls built into the stream.
</p>

<p align="center">
  <a href="https://github.com/veedy-dev/driftlight-android/releases/latest"><img alt="Latest release" src="https://img.shields.io/github/v/release/veedy-dev/driftlight-android?style=flat-square"></a>
  <a href="https://github.com/veedy-dev/driftlight-android/actions/workflows/build.yml"><img alt="Build status" src="https://img.shields.io/github/actions/workflow/status/veedy-dev/driftlight-android/build.yml?branch=driftlight&style=flat-square"></a>
  <img alt="Android 5.0 and newer" src="https://img.shields.io/badge/Android-5.0%2B-3DDC84?style=flat-square&logo=android&logoColor=white">
  <a href="LICENSE.txt"><img alt="GPL 3.0 license" src="https://img.shields.io/badge/license-GPL--3.0-blue?style=flat-square"></a>
</p>

<p align="center">
  <a href="https://github.com/veedy-dev/driftlight-android/releases/latest"><strong>Download the APK</strong></a>
  ·
  <a href="#build">Build from source</a>
  ·
  <a href="https://github.com/veedy-dev/driftlight-android/issues">Report an issue</a>
</p>

## What it is

Driftlight is an Android client for Moonlight-compatible hosts such as Moonshine, Sunshine, and Apollo. It keeps the mature Moonlight streaming stack and adds the desktop controls that are commonly needed around games and launchers.

- Touchscreen, gamepad, mouse, and physical-keyboard input
- A configurable left/right edge drawer with a draggable handle, desktop keyboards, task switching, process-manager shortcut, and session actions
- A compact full desktop keyboard with Esc, F1–F12, navigation, arrows, Win, Ctrl, and Alt
- Immersive fullscreen, picture-in-picture, external-display support, and clipboard sync
- Moonshine control-channel keepalive and resilient audio/video FEC handling

## Screenshots

These are direct 1920×1080 captures from an AYN Odin 3. No generated scenes, device frames, or reconstructed UI.

| Host library | Quick controls |
| --- | --- |
| <img src="docs/screenshots/host-library.png" alt="Driftlight host library on an AYN Odin 3"> | <img src="docs/screenshots/quick-controls.png" alt="Quick controls over a live Moonshine stream"> |

### Full desktop keyboard

<img src="docs/screenshots/full-keyboard.png" alt="Full desktop keyboard over a live Moonshine stream">

## Install

Download `Driftlight-0.1.0.apk` from the [latest release](https://github.com/veedy-dev/driftlight-android/releases/latest), then open it on Android or install it with ADB:

```bash
adb install -r Driftlight-0.1.0.apk
```

The release package ID is `com.veedy.driftlight`.

Signing certificate SHA-256:

```text
66:C8:97:5E:4C:11:BF:9A:99:34:3F:B4:FE:D7:77:E3:C4:7D:1D:69:3F:F8:CF:BE:3B:3F:91:27:3F:8D:CD:E0
```

## Compatible hosts

- [Moonshine](https://github.com/hgaiser/moonshine)
- [Sunshine](https://github.com/LizardByte/Sunshine)
- [Apollo](https://github.com/ClassicOldSong/Apollo)
- Other Moonlight-compatible GameStream hosts

## Build

Requirements: JDK 17, Android SDK 35, and Android NDK `27.0.12077973`.

```bash
git clone --recurse-submodules https://github.com/veedy-dev/driftlight-android.git
cd driftlight-android
./gradlew assembleNonRoot_gameRelease
```

The unsigned release APK is written to:

```text
app/build/outputs/apk/nonRoot_game/release/app-nonRoot_game-release-unsigned.apk
```

For a side-by-side development build:

```bash
./gradlew assembleNonRoot_gameDebug
```

> The Android NDK cannot build from a path containing spaces. Use a space-free checkout or the included GitHub Actions workflow.

## Lineage and license

Driftlight is based on [Moonlight Android](https://github.com/moonlight-stream/moonlight-android) and [Artemis / Moonlight Noir](https://github.com/ClassicOldSong/moonlight-android). Existing upstream copyright notices and GPL-3.0 terms remain in force.

Driftlight is distributed under [GPL-3.0](LICENSE.txt). Bundled typefaces are licensed under the SIL Open Font License in [`store-assets/fonts`](store-assets/fonts).
