---
name: Driftlight
description: Controller-first streaming controls for Android handhelds
colors:
  basalt: "#0D1111"
  graphite: "#171D1C"
  graphite-raised: "#202826"
  signal-mint: "#9FE7D0"
  signal-ink: "#06261F"
  warm-white: "#F2F4EF"
  stone: "#AAB4AF"
  mineral-line: "#3B4743"
  error: "#FFB4AB"
typography:
  display:
    fontFamily: "Android sans-serif"
    fontSize: "32sp"
    fontWeight: 700
    lineHeight: 1.15
    letterSpacing: "-0.02em"
  title:
    fontFamily: "Android sans-serif"
    fontSize: "20sp"
    fontWeight: 700
    lineHeight: 1.2
  body:
    fontFamily: "Android sans-serif"
    fontSize: "16sp"
    fontWeight: 400
    lineHeight: 1.4
  label:
    fontFamily: "Android sans-serif"
    fontSize: "14sp"
    fontWeight: 500
    lineHeight: 1.2
    letterSpacing: "0.08em"
rounded:
  key: "8dp"
  control: "12dp"
  card: "14dp"
  panel: "18dp"
spacing:
  xs: "8dp"
  sm: "12dp"
  md: "18dp"
  lg: "24dp"
components:
  quick-action:
    backgroundColor: "{colors.graphite-raised}"
    textColor: "{colors.warm-white}"
    rounded: "{rounded.control}"
    height: "64dp"
    padding: "0 16dp"
  library-card:
    backgroundColor: "{colors.graphite}"
    textColor: "{colors.warm-white}"
    rounded: "{rounded.card}"
    padding: "18dp"
  keyboard-key:
    backgroundColor: "{colors.graphite-raised}"
    textColor: "{colors.warm-white}"
    rounded: "{rounded.key}"
---

# Design System: Driftlight

## Overview

**Creative North Star: "The Mineral Field Kit"**

Driftlight feels like a precise handheld instrument used in a dark room: matte, quiet, direct, and readable at arm’s length. Brand expression comes from disciplined spacing, mineral-dark tonal layers, and one pale-mint signal rather than gaming neon or decorative effects.

The stream is always the primary surface. Client chrome stays compact until the user asks for desktop control, then appears as a clear edge rail or keyboard sheet.

**Key Characteristics:**

- Landscape-first composition with useful portrait adaptation
- Controller focus visible through both a structural outline and tonal change
- Matte tonal depth instead of glass or glow
- Large, explicit actions with concise labels
- One mint signal color across connection, focus, progress, and active modifiers
- Soft-3D Drift Sprite mascot with a simple dark-mode-first silhouette

## Colors

The palette uses cool mineral neutrals and one low-saturation mint signal.

### Primary

- **Signal Mint:** Focus, progress, active controls, and positive connection state.
- **Signal Ink:** Dark foreground or selected-key fill paired with mint.

### Neutral

- **Basalt:** Full-screen ground.
- **Graphite:** Cards, keyboard sheet, and side panels.
- **Raised Graphite:** Keys and interactive rows.
- **Warm White:** Primary text and icons.
- **Stone:** Secondary text and explanatory copy.
- **Mineral Line:** Resting borders and separators.

**The One Signal Rule.** Mint communicates focus or live state; it is never ambient decoration.

## Typography

**Display Font:** Android sans-serif  
**Body Font:** Android sans-serif

Typography is blunt and legible rather than branded through a downloaded face. Large headings use bold weight and restrained negative tracking; labels use modest positive tracking only when they identify a stable section.

### Hierarchy

- **Display:** Screen names and host context.
- **Title:** Dialogs, panels, and primary items.
- **Body:** Settings summaries and empty-state guidance.
- **Label:** Compact section identifiers and action metadata.

**The Arm’s-Length Rule.** Critical actions never depend on text smaller than 14sp or touch targets smaller than 48dp.

## Layout

Use an 8dp base rhythm. Major screen edges receive 24dp, compact controls separate by 8–12dp, and content regions separate by 18–24dp. Expanded landscape layouts keep titles on the left and utility actions on the right. Libraries preserve generous negative space rather than filling every column.

The in-stream rail is 332dp wide and right-aligned. The desktop keyboard uses the lower portion of the stream and retains user-controlled height, width, alignment, and opacity.

## Elevation & Depth

Depth is primarily tonal. Graphite sits above basalt; raised graphite marks controls. Shadows are limited to temporary overlays and keyboard sheets where separation from moving video is functional.

**The Flat-at-Rest Rule.** Library and settings surfaces do not float; elevation appears only for in-stream layers.

## Shapes

Keys use an 8dp radius, controls 12dp, library cards 14dp, and temporary panels 18dp. Focus borders are 2–3dp and always accompany a fill change. Pills are reserved for compact status readouts.

## Components

### Quick actions

Five 64dp rows appear in a fixed order: Full keys, Alt+Tab, Android keyboard, Task manager, More. Resting rows use raised graphite; focused and pressed rows use signal ink with a mint outline.

### Library cards

Host cards use a matte graphite container with a one-pixel mineral line. Poster cards prioritize artwork and use the same focus selector on top of the card so controller focus remains visible.

### Desktop keyboard

Every key retains its real desktop keycode. Resting keys use raised graphite and a mineral border. Focused keys use a mint border. Sticky modifiers use signal ink with a mint border. The sheet itself uses graphite with rounded top corners.

### Inputs and dialogs

Inputs use Material outlined fields with 12dp corners. Dialogs use raised graphite, warm-white titles, stone body copy, and mint actions. Loading uses a mint circular indicator beside stable title and message text.

### Navigation

The host library uses a compact top action group. Settings preserves search-first navigation and native Android Back behavior. In-stream controls collapse on Back or outside touch and disappear entirely in Picture-in-Picture.

## Do's and Don'ts

### Do:

- **Do** keep gameplay unobscured while quick controls are collapsed.
- **Do** pair controller focus with outline, fill, and readable labels.
- **Do** reuse Material 3 interaction structure and native Android Back behavior.
- **Do** keep every desktop shortcut reachable within two touch actions.

### Don't:

- **Don't** use neon glow, purple gradients, or decorative glass blur.
- **Don't** add card-inside-card hierarchy where spacing is sufficient.
- **Don't** invent a second accent color for warnings or categories.
- **Don't** intercept stream touches outside an open overlay.
