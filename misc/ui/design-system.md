# Design System Specification

> Canonical Design System contract for La Podrida.
>
> Owner: Design System Architect · Consumers: UI Designer, Component
> Builder, Compose Architect, Compose Developer, Guardian · Status: approved

## Metadata

- Feature: Menu Screen Redesign (establishes global DS)
- Version: 1.3.0
- Status: approved
- Owner: @ui-design-system-architect
- Last updated: 2026-08-11
- Supersedes: 1.2.0
- Base Design System version: 1.0.0

## 1. Design System Principles

- **Card-table elegance**: Dark, rich palette evoking a felt card table with gold accents.
- **Component boundaries**: Never use raw Material 3 composables directly in screens. Always go through Foundation or Application components.
- **Token-driven**: All colors, spacing, typography, and shapes are consumed via tokens, never hardcoded.
- **Accessibility first**: All interactive components meet WCAG AA contrast (4.5:1 text, 3:1 large text/UI).

## 2. Tokens

### Colour

| Token | Hex | Usage |
|-------|-----|-------|
| `primary` | `#1B5E20` | Deep felt-green; primary buttons, app bar |
| `onPrimary` | `#FFF8E1` | Ivory text/icons on primary surfaces |
| `primaryContainer` | `#2E7D32` | Lighter green for containers/cards |
| `onPrimaryContainer` | `#FFF8E1` | Ivory on primary containers |
| `secondary` | `#FFB300` | Gold/amber; accents, highlights, borders |
| `onSecondary` | `#1B1B1B` | Dark text on gold surfaces |
| `secondaryContainer` | `#FFC107` | Brighter gold for selected states |
| `onSecondaryContainer` | `#1B1B1B` | Dark text on bright gold |
| `background` | `#0D1F0F` | Near-black green; screen background |
| `onBackground` | `#FFFDE7` | Cream/ivory text on background |
| `surface` | `#1A3A1C` | Card-table green; cards, dialogs |
| `onSurface` | `#FFFDE7` | Cream text on surface |
| `surfaceVariant` | `#234726` | Slightly lighter green for hover/secondary surfaces |
| `onSurfaceVariant` | `#C8E6C9` | Muted light green for secondary text |
| `outline` | `#FFB300` | Gold outline for card-like borders |
| `outlineVariant` | `#4E7D50` | Muted green border for subtle dividers |
| `error` | `#C62828` | Muted red for errors |
| `onError` | `#FFFDE7` | Ivory on error |
| `scrim` | `#000000` | Dialog overlay (alpha 60%) |

### Typography

All use system default font family (platform sans-serif). Custom font may be added later.

| Token | Weight | Size | Line Height | Letter Spacing | Usage |
|-------|--------|------|-------------|----------------|-------|
| `displayLarge` | Bold (700) | 36sp | 44sp | -0.25sp | App title "La Podrida" |
| `titleLarge` | SemiBold (600) | 22sp | 28sp | 0sp | Section headers |
| `titleMedium` | Medium (500) | 18sp | 24sp | 0.15sp | Dialog titles |
| `bodyLarge` | Normal (400) | 16sp | 24sp | 0.5sp | Button text, descriptions |
| `bodyMedium` | Normal (400) | 14sp | 20sp | 0.25sp | General body text |
| `labelLarge` | Medium (500) | 14sp | 20sp | 0.1sp | Button labels |
| `labelMedium` | Medium (500) | 12sp | 16sp | 0.5sp | Small info, badges |
| `labelSmall` | Medium (500) | 10sp | 14sp | 0.5sp | Captions |

### Spacing

| Token | Value | Usage |
|-------|-------|-------|
| `xxs` | 4.dp | Tight inner padding (icon gaps) |
| `xs` | 8.dp | Compact padding, small gaps |
| `sm` | 12.dp | Between related elements |
| `md` | 16.dp | Standard content padding |
| `lg` | 24.dp | Section spacing |
| `xl` | 32.dp | Large gaps between groups |
| `xxl` | 48.dp | Screen-level vertical padding |

### Shape

| Token | Value | Usage |
|-------|-------|-------|
| `small` | RoundedCorner 8.dp | Chips, small elements |
| `medium` | RoundedCorner 12.dp | Buttons, cards |
| `large` | RoundedCorner 16.dp | Dialogs, large cards |
| `extraLarge` | RoundedCorner 24.dp | Bottom sheets |

### Elevation

| Token | Value | Usage |
|-------|-------|-------|
| `none` | 0.dp | Flat surfaces |
| `low` | 2.dp | Cards, buttons at rest |
| `medium` | 4.dp | Dialogs, floating elements |
| `high` | 8.dp | Dropdowns, popovers |

## 3. Existing Components Reused

| Component | Layer | Usage |
|-----------|-------|-------|
| `IncrementalNumberInput` | Application (legacy) | Player count selection — **superseded**: migrated to `AppIncrementalNumberInput` (see §6) |

## 4. Component Decisions

### Text Input Field
- Decision: **create Foundation Component** (`AppTextField`)
- Reason: Raw M3 `TextField` must not appear in screens. A themed wrapper enforces token-driven colours, shapes, and consistent focused/error states.
- Existing alternative: None
- Migration impact: `GameSettingsScreen` player-name inputs migrate from raw `TextField` to `AppTextField`.
- Consumers: `GameSettingsScreen`, all future input screens.

### Toggle Switch
- Decision: **create Foundation Component** (`AppSwitch`)
- Reason: Raw M3 `Switch` must not appear in screens. A themed wrapper locks thumb/track colours to design tokens (gold checked, muted unchecked) and enforces a11y labelling.
- Existing alternative: None
- Migration impact: `GameSettingsScreen` indian-round toggle migrates from raw `Switch` to `AppSwitch`.
- Consumers: `GameSettingsScreen`, all future toggle settings.

### Primary Action Button
- Decision: **create Foundation Component** (`AppButton`)
- Reason: No themed button exists. Raw M3 Button must not appear in screens.
- Existing alternative: None
- Migration impact: None (greenfield)
- Consumers: MenuScreen, all future screens

### App Title Display
- Decision: **create Foundation Component** (`AppTitle`)
- Reason: Stylized app name with card-game typography, reusable in splash/menu.
- Existing alternative: None
- Consumers: MenuScreen

### Themed Dialog
- Decision: **create Foundation Component** (`AppDialog`)
- Reason: Dialogs must use surface/outline tokens consistently.
- Existing alternative: None
- Consumers: MenuScreen (player setup dialog), all screens

### Icon Button
- Decision: **create Foundation Component** (`AppIconButton`)
- Reason: Consistent icon button styling with gold accents.
- Existing alternative: None
- Consumers: Language picker, settings

### Numeric Stepper (IncrementalNumberInput)
- Decision: **convert existing ad-hoc component into a proper Application Component** (`AppIncrementalNumberInput`), renamed from `IncrementalNumberInput`.
- Reason: The legacy component used raw `FilledIconButton` (bypasses DS boundary), hardcoded `Color.White` background, and empty `contentDescription` strings. Converting it to an Application Component that composes `AppIconButton` enforces token compliance and accessibility, and gives it a first-class DS identity.
- Existing alternative: `IncrementalNumberInput` (legacy) — to be replaced.
- Migration impact: `GameSettingsScreen` three usages rename from `IncrementalNumberInput` to `AppIncrementalNumberInput`; API shape preserved (both overloads kept).
- Consumers: `GameSettingsScreen`, all future numeric-stepper needs.

- Decision: **create Application Component** (`MenuButton`)
- Reason: Specific pattern (icon + label + card-like container) used only in menu.
- Existing alternative: None
- Consumers: MenuScreen

## 5. New Foundation Components

### AppTextField

- Name: `AppTextField`
- Purpose: Themed single- or multi-line text input wrapping M3 `TextField` with card-game token styling.
- Material 3 primitive: `TextField`
- API intent:
  ```
  @Composable fun AppTextField(
      value: String,
      onValueChange: (String) -> Unit,
      modifier: Modifier = Modifier,
      label: String? = null,
      enabled: Boolean = true,
      singleLine: Boolean = true,
      maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
      isError: Boolean = false,
      keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
      keyboardActions: KeyboardActions = KeyboardActions.Default,
  )
  ```
- Token bindings:
  - Container fill: `MaterialTheme.colorScheme.surfaceVariant`
  - Container shape: `MaterialTheme.shapes.medium` (12dp)
  - Input text: `MaterialTheme.colorScheme.onSurface`, style `bodyLarge`
  - Label (unfocused): `MaterialTheme.colorScheme.onSurfaceVariant`, style `labelMedium`
  - Label (focused, floated): `MaterialTheme.colorScheme.secondary` (gold)
  - Cursor & focused indicator: `MaterialTheme.colorScheme.secondary`
  - Error indicator & label: `MaterialTheme.colorScheme.error`
  - Disabled: 38% alpha on content and container
- States: enabled, focused (gold label + cursor), error, disabled.
- Accessibility: Label text is announced. `isError` triggers error semantics. Minimum touch target 48dp height.
- When to use: Any free-text input field (player names, search, etc.).
- When not to use: Numeric steppers (use `AppIncrementalNumberInput`); dropdowns.

### AppSwitch

- Name: `AppSwitch`
- Purpose: Themed boolean toggle wrapping M3 `Switch` with gold-checked / muted-unchecked card-game colours.
- Material 3 primitive: `Switch`
- API intent:
  ```
  @Composable fun AppSwitch(
      checked: Boolean,
      onCheckedChange: (Boolean) -> Unit,
      modifier: Modifier = Modifier,
      enabled: Boolean = true,
      contentDescription: String? = null,
  )
  ```
- Token bindings:
  - Track (checked): `MaterialTheme.colorScheme.secondary` (gold)
  - Thumb (checked): `MaterialTheme.colorScheme.onSecondary` (dark)
  - Track (unchecked): `MaterialTheme.colorScheme.surfaceVariant`
  - Thumb (unchecked): `MaterialTheme.colorScheme.onSurfaceVariant`
  - Disabled: 38% alpha across all colour slots
- States: checked, unchecked, disabled-checked, disabled-unchecked.
- Accessibility: `contentDescription` surfaced via `Modifier.semantics` on the wrapping element so TalkBack announces both the role and the setting name. Minimum touch target 48x48dp.
- When to use: Binary on/off settings (Indian Round toggle, future feature flags).
- When not to use: Multi-option selection (use radio/chips).

### AppButton

- Name: `AppButton`
- Purpose: Primary themed button wrapping M3 Button with card-game styling.
- Material 3 primitive: `Button`, `OutlinedButton`
- API intent:
  ```
  @Composable fun AppButton(
      text: String,
      onClick: () -> Unit,
      modifier: Modifier = Modifier,
      variant: AppButtonVariant = AppButtonVariant.Primary,
      enabled: Boolean = true,
      icon: (@Composable () -> Unit)? = null
  )
  ```
- Variants:
  - `Primary` — filled green background, ivory text
  - `Secondary` — gold outline, gold text on transparent
  - `Tertiary` — text-only, gold color
- States: enabled, disabled (50% alpha), pressed (slight darken), focused (gold ring)
- Accessibility: Minimum touch target 48x48dp. Button role announced. Disabled state announced.
- When to use: Any actionable button in the app.
- When not to use: Icon-only actions (use `AppIconButton`).

### AppTitle

- Name: `AppTitle`
- Purpose: Stylized display of the app name "La Podrida" with decorative card suit motifs.
- Material 3 primitive: `Text`
- API intent:
  ```
  @Composable fun AppTitle(
      modifier: Modifier = Modifier,
      size: AppTitleSize = AppTitleSize.Large
  )
  ```
- Variants:
  - `Large` — displayLarge, with suit decorations (♠♥♦♣)
  - `Small` — titleLarge, text only
- States: Static display only.
- Accessibility: Decorative suits are hidden from TalkBack. Text is readable.
- When to use: Menu screen header, splash screen.
- When not to use: Section headers (use plain `Text` with `titleLarge`).

### AppDialog

- Name: `AppDialog`
- Purpose: Themed modal dialog with surface color, gold outline, and consistent layout.
- Material 3 primitive: `AlertDialog` / `Dialog`
- API intent:
  ```
  @Composable fun AppDialog(
      onDismissRequest: () -> Unit,
      title: String? = null,
      confirmButton: @Composable () -> Unit,
      dismissButton: (@Composable () -> Unit)? = null,
      content: @Composable () -> Unit
  )
  ```
- Variants: Single variant (consistent styling).
- States: Shown/dismissed (controlled by caller).
- Accessibility: Focus trapped inside dialog. Title announced. Dismiss on back gesture.
- When to use: Any modal confirmation or input collection.
- When not to use: Full-screen flows (use navigation instead).

### AppIconButton

- Name: `AppIconButton`
- Purpose: Themed icon-only button with consistent sizing and gold accent.
- Material 3 primitive: `IconButton`
- API intent:
  ```
  @Composable fun AppIconButton(
      onClick: () -> Unit,
      modifier: Modifier = Modifier,
      enabled: Boolean = true,
      contentDescription: String,
      content: @Composable () -> Unit
  )
  ```
- Variants: Single (gold-tinted icon on transparent, with circular ripple).
- States: enabled, disabled, focused, pressed.
- Accessibility: contentDescription required (enforced by API). Min 48x48dp touch target.
- When to use: Toolbar actions, language picker, settings icon.
- When not to use: Actions that need a label (use `AppButton`).

## 6. New Application Components

### MenuButton

- Name: `MenuButton`
- Purpose: Card-shaped navigation button for the main menu, displaying icon + label.
- Foundation Components used: `AppButton` (extends Primary variant with custom layout)
- API intent:
  ```
  @Composable fun MenuButton(
      text: String,
      onClick: () -> Unit,
      modifier: Modifier = Modifier,
      icon: ImageVector? = null,
      enabled: Boolean = true
  )
  ```
- Variants: Single (filled card-table surface with gold left-border accent, 12dp corners).
- States: enabled, disabled, pressed (gold border brightens), focused.
- Accessibility: Button role. Text + icon content description merged.
- When to use: Main menu screen options (New Game, Continue, Rules, Settings).
- When not to use: In-game actions, dialogs.

### AppIncrementalNumberInput

- Name: `AppIncrementalNumberInput`
- Purpose: Numeric stepper for bounded integer input (e.g. player count). Composes `AppIconButton` for increment/decrement controls and a themed `Text` display for the current value.
- Foundation Components used: `AppIconButton` (both increment and decrement buttons)
- API intent (two overloads, both kept):
  ```
  // Convenience overload
  @Composable fun AppIncrementalNumberInput(
      value: Int,
      onValueUpdated: (Int) -> Unit,
      modifier: Modifier = Modifier,
      minValue: Int = Int.MIN_VALUE,
      maxValue: Int = Int.MAX_VALUE,
  )

  // Explicit overload
  @Composable fun AppIncrementalNumberInput(
      value: Int,
      onValueIncreased: () -> Unit,
      onValueDecreased: () -> Unit,
      modifier: Modifier = Modifier,
      minValue: Int = Int.MIN_VALUE,
      maxValue: Int = Int.MAX_VALUE,
  )
  ```
- Token bindings:
  - Container background: `MaterialTheme.colorScheme.surfaceVariant` (`#234726`)
  - Container shape: `MaterialTheme.shapes.small` (8dp rounded corners)
  - Value text: `MaterialTheme.colorScheme.onSurface`, style `bodyLarge`
  - Decrement / Increment buttons: delegated entirely to `AppIconButton` (inherits gold tint, 48dp touch target, ripple)
  - No hardcoded colors anywhere
- Variants: Single.
- States:
  - `enabled`: both buttons active; value in range.
  - `atMin`: decrement `AppIconButton` disabled (`enabled = false`).
  - `atMax`: increment `AppIconButton` disabled (`enabled = false`).
- Accessibility:
  - Decrement button: `contentDescription = "Decrement"` (required, non-empty).
  - Increment button: `contentDescription = "Increment"` (required, non-empty).
  - Value `Text` is announced by TalkBack as part of the row traversal.
  - Each `AppIconButton` inherits min 48×48dp touch target from Foundation.
- When to use: Any bounded integer stepper (player count, round count, etc.).
- When not to use: Free-form numeric text entry (use `AppTextField` with numeric keyboard); non-integer ranges.

## 7. Existing Component Changes

> No pending token-only migrations. The former `IncrementalNumberInput` migration entry has been superseded: that component is now fully redesigned as `AppIncrementalNumberInput` (Application Component, §6).

## 8. Design System Constraints

- All screens must wrap content in `LaPodridaTheme {}` (custom MaterialTheme wrapper).
- No raw `MaterialTheme.colorScheme` in composables — access via typed helper or theme object.
- No hardcoded color hex values in screen/component code.
- Foundation components live in `ui.components.foundation` package.
- Application components live in `ui.components` package (feature-grouped).

## 9. Material 3 Boundary

| M3 Primitive | Wrapped By | Rule |
|--------------|-----------|------|
| `Button` | `AppButton` | Never use `Button` directly |
| `OutlinedButton` | `AppButton(variant=Secondary)` | Never use directly |
| `TextButton` | `AppButton(variant=Tertiary)` | Never use directly |
| `IconButton` | `AppIconButton` | Never use directly |
| `AlertDialog` | `AppDialog` | Never use directly |
| `TextField` | `AppTextField` | Never use directly |
| `Switch` | `AppSwitch` | Never use directly |
| `Text` | Allowed directly | Use with theme typography tokens only |
| `Icon` | Allowed directly | Always provide contentDescription |
| `Surface` | Allowed directly | Use theme color tokens |

## 10. Open Design System Issues

### Dark/Light Mode

- Problem: Only dark theme defined (card-table aesthetic). Light mode undefined.
- Impact: Users in bright environments may struggle.
- Recommendation: Defer light mode. The card-game identity is inherently dark. Revisit if user feedback demands it.

### Custom Font

- Problem: System font works but a serif/display font would enhance card-game feel.
- Impact: Visual polish only.
- Recommendation: Evaluate adding a custom font (e.g., Playfair Display for titles) in v1.1.

## 11. Change Log

- Version: 1.3.0
- Change: Replaced `IncrementalNumberInput` "token-only migration" entry with `AppIncrementalNumberInput` as a new Application Component (§6). Component now composes `AppIconButton` for increment/decrement controls instead of raw `FilledIconButton`; container uses `surfaceVariant` + `shapes.small` tokens; value display uses `Text` with theme typography; `contentDescription` changed from `""` to `"Increment"`/`"Decrement"`; both overloads preserved. Updated §3 (Existing Components table), §4 (Component Decisions), §7 (Existing Component Changes), and §9 M3 Boundary cross-reference in `AppTextField`. Version bumped 1.2.0 → 1.3.0.
- Reason: Correction of prior decision — `IncrementalNumberInput` was incorrectly classified as a token-only migration. It requires a proper Application Component identity (`AppIncrementalNumberInput`) to enforce DS boundaries, reuse `AppIconButton`, and guarantee a11y compliance.

- Version: 1.2.0
- Change: Added `AppTextField` and `AppSwitch` Foundation Components. Specified concrete token-migration plan for `IncrementalNumberInput`. Extended M3 Boundary table with `TextField` and `Switch` rules.
- Reason: `GameSettingsScreen` adaptation — raw `TextField`, `Switch`, and whitespace-colored `IncrementalNumberInput` must be replaced with Design System components before the screen can be considered DS-compliant.

- Version: 1.1.0
- Change: Added `AppGhostButton` Application Component. Defined ghost button contract (transparent container, gold content, shared BaseButton aesthetic).
- Reason: Third button affordance needed for low-emphasis actions. Completes Primary / Secondary / Ghost hierarchy.

- Version: 1.0.0
- Change: Initial design system creation for Menu Screen redesign.
- Reason: Greenfield — no prior design system existed. App used bare MaterialTheme with hardcoded colors.
