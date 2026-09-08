<p align="center">
  <img src="store-assets/driftlight-icon.svg" width="96" alt="Driftlight icon">
</p>

<h1 align="center">Driftlight</h1>

<p align="center">
  A Moonlight Android fork with quick controls and a redesigned UI.
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
  <a href="#build-from-source">Build from source</a>
  ·
  <a href="https://github.com/veedy-dev/driftlight-android/issues">Report an issue</a>
</p>

## About

Driftlight is a Moonlight Android fork with a new interface, quick controls, and desktop keyboard support.

## Features

- Updated host library and settings
- Quick controls inside the stream
- Full desktop and Android keyboards
- Touch, mouse, keyboard, and gamepad input
- Left or right quick-controls handle that fades away when idle; touch its edge position to reveal it
- Controller navigation in menus
- Moonshine and Apollo support
- Control-channel and FEC crash fixes

## Screenshots

| Host selection | Quick controls |
| --- | --- |
| <img src="docs/screenshots/host-library.png" alt="Driftlight host selection"> | <img src="docs/screenshots/quick-controls.png" alt="Quick controls over a live stream"> |

| Settings | Full desktop keyboard |
| --- | --- |
| <img src="docs/screenshots/settings.png" alt="Driftlight settings"> | <img src="docs/screenshots/full-keyboard.png" alt="Full desktop keyboard over a live stream"> |

## Install

Download the APK from the [latest release](https://github.com/veedy-dev/driftlight-android/releases/latest) and open it on your Android device.

If Android blocks the installation, allow your browser or file manager to install unknown apps, then try again.

## Streaming servers

Install one of these on your computer:

- [Apollo](https://github.com/ClassicOldSong/Apollo)
- [Sunshine](https://github.com/LizardByte/Sunshine)
- [Moonshine for Linux](https://github.com/hgaiser/moonshine)

Open Driftlight and add the computer running the server.

## Build from source

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
