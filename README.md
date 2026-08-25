<p align="center">
  <img src="store-assets/driftlight-icon.svg" width="96" alt="Driftlight Driftwave icon">
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

## Stream first. Controls on demand.

Driftlight keeps the game in view and puts the awkward desktop moments one gesture away. Move naturally between touch, gamepad, mouse, and keyboard without leaving the stream.

- A configurable edge drawer for keyboards, task switching, process controls, and session actions
- A compact desktop keyboard with Esc, F1–F12, navigation, arrows, Win, Ctrl, and Alt
- Immersive fullscreen, picture-in-picture, external-display support, and clipboard sync
- Moonshine control-channel keepalive and resilient audio/video FEC handling

## In action

| Pick a host and launch | Pull in controls only when needed |
| --- | --- |
| <img src="docs/screenshots/host-library.png" alt="Driftlight host library"> | <img src="docs/screenshots/quick-controls.png" alt="Quick controls over a live stream"> |

### A full desktop keyboard, without leaving the stream

<img src="docs/screenshots/full-keyboard.png" alt="Full desktop keyboard over a live stream">

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
