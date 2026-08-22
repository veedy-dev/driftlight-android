# Design System: Driftlight

## 1. Visual Theme & Atmosphere

**Creative north star: the technical field instrument.** Driftlight should feel like a purpose-built handheld control surface: matte, exact, calm under low light, and immediately readable at arm’s length. It is not a generic gaming dashboard and not a cinematic mascot brand.

- **Density:** 4/10 — Daily App Balanced. Enough room for controller focus and touch, without empty showcase-space inside operational screens.
- **Variance:** 7/10 — Offset Asymmetric. Titles, controls, and product imagery occupy distinct zones rather than centering everything.
- **Motion:** 5/10 — Fluid Native. Motion confirms state and then gets out of the stream.
- **Identity:** The authored **Drift Gate** mark: a soft dimensional `D`, two quiet eyes, and three signal trails. It is geometric, reproducible, and deliberately non-animal.
- **Material:** Near-black mineral surfaces, low tonal elevation, thin structural rules, and one pale-mint focus signal.

The stream is always the primary surface. Client chrome stays compact until requested. README and project imagery must use real interface captures, authored vector geometry, and typographic composition — never synthetic mascot wallpaper or generic AI concept art.

## 2. Color Palette & Roles

One palette everywhere. Pale mint is the only accent.

- **Basalt Canvas** (`#0D1111`) — App background and README canvas. Never replace with pure black.
- **Graphite Surface** (`#171D1C`) — Cards, keyboard sheet, quick-control panel, and form surfaces.
- **Raised Graphite** (`#202826`) — Keys, active rows, and controls that need tactile separation.
- **Signal Mint** (`#9FE7D0`) — The single accent for focus rings, connection state, progress, selected actions, and the idle stripe.
- **Signal Ink** (`#06261F`) — Selected-control fill paired with Signal Mint.
- **Warm White** (`#F2F4EF`) — Primary text and icon foreground.
- **Stone Text** (`#AAB4AF`) — Secondary copy and metadata.
- **Mineral Line** (`#3B4743`) — One-pixel boundaries and resting outlines.
- **Error Coral** (`#FFB4AB`) — Errors only; never a decorative second accent.

**The One Signal Rule.** Signal Mint communicates focus, state, or a live control path. It never becomes ambient glow.

## 3. Typography Rules

- **Display / Brand:** **Unbounded Bold** — screen names, README headlines, the Driftlight wordmark. Use controlled scale, weight-led hierarchy, and tracking between `-0.03em` and `-0.01em`.
- **Body / UI:** **Outfit Regular** — settings, descriptions, empty states, and action labels. Use relaxed leading around `1.4` and keep prose within `65ch`.
- **UI Emphasis:** **Outfit Medium** — selected actions, section labels, and compact navigation.
- **Metrics / Diagnostics:** **JetBrains Mono** — latency, packet loss, frame rate, and fixed-width technical values.
- **Fallback:** Android sans-serif only below API 26 where bundled font resources are unavailable.

### Type hierarchy

- Screen title: Unbounded Bold, `28–32sp`
- Panel title: Unbounded Bold, `20–22sp`
- Action title: Outfit Medium, `16–18sp`
- Body: Outfit Regular, `15–16sp`
- Metadata: Outfit Medium or JetBrains Mono, `12–14sp`

**Banned:** Inter, Times New Roman, Georgia, Garamond, decorative techno fonts, and all-caps body paragraphs.

## 4. Component Stylings

- **Primary actions:** Signal Mint fill with Signal Ink foreground, `12dp` corners, at least `48dp` high. Pressed state moves inward by `1dp`; no outer glow.
- **Secondary actions:** Raised Graphite fill, Mineral Line border, Warm White label. Focus adds a `2dp` Signal Mint outline and Signal Ink fill.
- **Library cards:** `14dp` corners, Graphite fill, one Mineral Line stroke. Use cards only for hosts, artwork, or an actual elevation boundary.
- **Quick-control rail:** `332dp` right panel with five `64dp` rows. Full keys, Alt+Tab, Android keyboard, Task manager, More — always in that order.
- **Idle overlay:** The normal handle collapses after three seconds into a visually `4dp` Signal Mint stripe while retaining a transparent `48dp` touch target.
- **Desktop keyboard:** Six rows with at least `48dp` per row. Sticky modifiers use Signal Ink plus a Signal Mint outline.
- **Inputs:** Label above or within the standard Material outline, `12dp` corners, helper/error text below. Never use floating decoration that competes with the value.
- **Loading:** Prefer layout-shaped skeletons. For indeterminate host discovery, use one branded mint arc and specific recovery copy — never a stack of generic spinners.
- **Empty states:** Explain both the state and the next action. Host discovery explicitly points to manual IP entry.
- **Errors:** Name the failure and the recovery. A stream timeout must not masquerade as a graceful user exit.

## 5. Layout Principles

- Use an `8dp` base rhythm; major screen edges use `24dp`.
- All touch targets are at least `48dp`; place at least `8dp` between adjacent targets.
- Landscape handheld is the primary composition. Portrait uses a two-row header so the wordmark never collides with utility actions.
- Respect status, cutout, and navigation-bar insets on every non-stream screen.
- Streaming uses true immersive fullscreen with transient system bars available by swipe.
- Keep every element in a clear spatial zone. Overlay is allowed only where it is the product behavior, never as decorative stacking.
- README heroes use a left typographic field and a right real-product capture. Centered logo-over-gradient compositions are banned.
- Do not use equal three-card feature rows. Prefer asymmetric screenshot pairs, one large proof image, or a clear sequence.

## 6. Motion & Interaction

- Use spring-like native motion equivalent to **stiffness 100 / damping 20**; never linear easing.
- Animate only `transform` and `opacity`. Layout width changes occur discretely before or after the animation.
- Lists may fade through in a short cascade, but streaming frames and input paths never wait on animation.
- The quick-control handle fades to its stripe state after three seconds of inactivity and restores immediately on focus or touch.
- Active connection progress may loop; stable controls remain still so gameplay is not visually noisy.
- Respect Android’s Remove animations setting by replacing motion with an immediate state change.

## 7. Anti-Patterns (Banned)

**NEVER DO:**

- No AI-generated mascot scenes, glossy concept wallpaper, or fake product screenshots.
- No animals or creature mascots for Driftlight. Use the authored Drift Gate mark.
- No emojis.
- No Inter.
- No pure black (`#000000`).
- No purple/blue neon treatment, outer glows, or oversaturated gradients.
- No glassmorphism as decoration.
- No gradient text.
- No overlapping text and imagery.
- No centered high-variance hero.
- No equal three-column feature-card rows.
- No generic placeholder names or fake round metrics.
- No custom cursor.
- No copy such as “Elevate”, “Seamless”, “Unleash”, “Next-Gen”, “Scroll to explore”, or “Swipe down”.
- No text-heavy app icon; the Drift Gate silhouette must work at `48px`.
- No navigation or control surface that covers Android system bars without either insetting or intentionally entering immersive fullscreen.
