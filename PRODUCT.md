# Product

<!-- impeccable:product-schema 1 -->

## Platform

android

## Users

Primary user: a handheld PC-streaming player using an AYN Odin 3 in landscape, usually at arm’s length and often in a dim room. The user needs controller-first navigation, dependable touch fallbacks, and immediate access to desktop keys without leaving a stream.

## Product Purpose

Driftlight is a Moonlight-compatible Android streaming client that keeps the protocol reliability and performance of its Moonlight/Artemis lineage while making desktop control and host navigation feel native to a current gaming handheld.

Success means the user can find a host, launch a game, recover missing keyboard or desktop controls, and change streaming settings without fighting legacy UI or breaking immersion.

## Positioning

Driftlight combines the mature Moonlight streaming core with a minimal in-stream action rail and a complete desktop-key overlay designed specifically for landscape Android handhelds.

## Operating Context

- Primary device: AYN Odin 3, 1920×1080 landscape.
- Primary input: built-in gamepad; touch is the fast secondary path.
- Host compatibility: Moonlight-compatible GameStream hosts, including Moonshine and Sunshine-family servers.
- Typical conditions: local Wi-Fi streaming, dim-room use, frequent switching between games and desktop applications.

## Capabilities and Constraints

- Preserve existing stream, pairing, controller, touch, PiP, external-display, clipboard, and Apollo-compatible behavior.
- Preserve the existing Java/XML Android View stack and native streaming submodule; no framework migration.
- Provide a settings-controlled in-stream overlay.
- Provide full desktop keys, Android soft keyboard access, direct Alt+Tab, Task Manager, and extensible shortcut access.
- Keep overlays out of PiP and avoid intercepting gameplay input when closed.
- Maintain Android 5.0+ compatibility where the existing project supports it, while optimizing presentation for modern Android handhelds.
- Produce an installable APK.

## Brand Commitments

Working product name: Driftlight. The visual identity must be original and clearly distinct from Moonlight and Artemis while preserving upstream licensing and attribution.

## Evidence on Hand

The existing `moonlight-noir` branch already contains a full virtual keyboard, custom keys, quick menu, controller fixes, and streaming extensions. These implementations are production evidence to reuse rather than rewrite. No user-provided photography or commercial claims exist.

## Product Principles

1. Streaming remains invisible until the user needs control.
2. Every critical desktop action is reachable in two touches or one controller path.
3. Controller focus and touch affordances receive equal design attention.
4. Visual expression never obscures connection state or gameplay.
5. Reuse proven streaming behavior; redesign the shell, not the protocol core.

## Accessibility & Inclusion

Maintain 48dp minimum touch targets, clear focus indication, readable type at arm’s length, high contrast in dark environments, descriptive labels, and functional controller navigation without relying on color alone.
