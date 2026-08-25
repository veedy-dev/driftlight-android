<p align="center">
  <img src="store-assets/driftlight-icon.svg" width="96" alt="Driftlight icon">
</p>

<h1 align="center">Driftlight</h1>

<p align="center">
  Moonlight Android, reworked for touchscreens and handhelds.
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

## About

Driftlight is based on [Moonlight Android](https://github.com/moonlight-stream/moonlight-android). It keeps Moonlight's streaming code and updates the Android app around it. The aim is simple. Make the client easier to use on current Android devices without giving up Moonlight compatibility.

## What Driftlight changes

- New host library and settings screens
- An in-stream quick controls drawer for keyboards, task switching, process controls, and session actions
- Left or right drawer placement with a vertically movable handle
- A full desktop keyboard with Esc, F1 through F12, navigation keys, arrows, Win, Ctrl, and Alt
- Controller navigation that stays in an open menu instead of sending button presses to the remote PC
- Control-channel keepalive for Moonshine and fixes for audio and video FEC crashes
- Immersive fullscreen, picture-in-picture, external displays, and clipboard sync

## Screenshots

| Host selection | Quick controls |
| --- | --- |
| <img src="docs/screenshots/host-library.png" alt="Driftlight host selection"> | <img src="docs/screenshots/quick-controls.png" alt="Quick controls over a live stream"> |

| Settings | Full desktop keyboard |
| --- | --- |
| <img src="docs/screenshots/settings.png" alt="Driftlight settings"> | <img src="docs/screenshots/full-keyboard.png" alt="Full desktop keyboard over a live stream"> |

## Install

Download `Driftlight-0.1.0.apk` from the [latest release](https://github.com/veedy-dev/driftlight-android/releases/latest), then open it on Android or install it with ADB.

```bash
adb install -r Driftlight-0.1.0.apk
```

The release package ID is `com.veedy.driftlight`.

Signing certificate SHA-256:

```text
66:C8:97:5E:4C:11:BF:9A:99:34:3F:B4:FE:D7:77:E3:C4:7D:1D:69:3F:F8:CF:BE:3B:3F:91:27:3F:8D:CD:E0
```

## Host software

For Linux, [Moonshine](https://github.com/hgaiser/moonshine) is our first recommendation. It runs streams in isolated compositor sessions and can work without a physical monitor.

[Apollo](https://github.com/ClassicOldSong/Apollo) is a good choice when you want a Sunshine-based host with virtual-display management.

[Sunshine](https://github.com/LizardByte/Sunshine) and other Moonlight-compatible GameStream hosts also work.

## Build

You need JDK 17, Android SDK 35, and Android NDK `27.0.12077973`.

```bash
git clone --recurse-submodules https://github.com/veedy-dev/driftlight-android.git
cd driftlight-android
./gradlew assembleNonRoot_gameRelease
```

Gradle writes the unsigned release APK here:

```text
app/build/outputs/apk/nonRoot_game/release/app-nonRoot_game-release-unsigned.apk
```

For a development build that can sit beside the release app:

```bash
./gradlew assembleNonRoot_gameDebug
```

The Android NDK cannot build from a path containing spaces. Use a space-free checkout or the GitHub Actions workflow.

## Origin and license

Driftlight is based on Moonlight Android and [Artemis / Moonlight Noir](https://github.com/ClassicOldSong/moonlight-android). The upstream copyright notices and GPL-3.0 terms remain in place.

Driftlight uses [GPL-3.0](LICENSE.txt). The bundled fonts use the SIL Open Font License in [`store-assets/fonts`](store-assets/fonts).
