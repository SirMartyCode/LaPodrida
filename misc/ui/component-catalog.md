# Component Catalog

> Canonical inventory of reusable UI components.
>
> Owner: Design System Architect · Implementation owner: Component Builder
> Consumers: All UI agents · Status: active

## Metadata

- Design System version: 1.0.0
- Last updated: 2026-08-10
- Owner: @ui-design-system-architect

## 1. Foundation Components

| Name | Material 3 Base | Purpose | Variants | Status |
|------|----------------|---------|----------|--------|
| `AppButton` | Button / OutlinedButton | Themed action button | Primary, Secondary, Tertiary | spec-ready |
| `AppTitle` | Text | Stylized app name display | Large, Small | spec-ready |
| `AppDialog` | AlertDialog / Dialog | Themed modal dialog | Single | spec-ready |
| `AppIconButton` | IconButton | Themed icon-only button | Single | spec-ready |

## 2. Application Components

| Name | Foundation Dependencies | Purpose | Variants | Status |
|------|------------------------|---------|----------|--------|
| `MenuButton` | AppButton | Card-shaped menu navigation button | Single | spec-ready |
| `AppGhostButton` | BaseButton | Low-emphasis ghost button (no fill, no border) | Single | spec-ready |
| `IncrementalNumberInput` | (legacy, needs migration) | Number +/- selector | Single | needs-migration |

## 3. Component Contracts

### `AppButton`

- Layer: Foundation
- Purpose: Primary themed button for all actions.
- When to use: Any clickable action in the app.
- When not to use: Icon-only actions (use AppIconButton).
- API:
  ```kotlin
  @Composable fun AppButton(
      text: String,
      onClick: () -> Unit,
      modifier: Modifier = Modifier,
      variant: AppButtonVariant = AppButtonVariant.Primary,
      enabled: Boolean = true,
      icon: (@Composable () -> Unit)? = null
  )
  enum class AppButtonVariant { Primary, Secondary, Tertiary }
  ```
- Variants: Primary (filled green), Secondary (gold outline), Tertiary (text-only gold)
- States: enabled, disabled, pressed, focused
- Accessibility: Min 48dp touch, button role, disabled announced
- Composition: Wraps M3 Button/OutlinedButton/TextButton based on variant
- Implementation location: `ui.components.foundation.AppButton.kt`
- Introduced in: DS 1.0.0

### `AppTitle`

- Layer: Foundation
- Purpose: Decorative app title with card suit motifs.
- When to use: Menu header, splash screen.
- When not to use: Generic headings.
- API:
  ```kotlin
  @Composable fun AppTitle(
      modifier: Modifier = Modifier,
      size: AppTitleSize = AppTitleSize.Large
  )
  enum class AppTitleSize { Large, Small }
  ```
- Variants: Large (displayLarge + suits), Small (titleLarge, no suits)
- States: Static
- Accessibility: Suits marked decorative (semantics clearAndSetSemantics on suit text)
- Composition: Text composables with theme typography
- Implementation location: `ui.components.foundation.AppTitle.kt`
- Introduced in: DS 1.0.0

### `AppDialog`

- Layer: Foundation
- Purpose: Consistent themed dialog with surface colors and gold outline.
- When to use: Modal confirmations, input prompts.
- When not to use: Full-screen flows.
- API:
  ```kotlin
  @Composable fun AppDialog(
      onDismissRequest: () -> Unit,
      title: String? = null,
      confirmButton: @Composable () -> Unit,
      dismissButton: (@Composable () -> Unit)? = null,
      content: @Composable () -> Unit
  )
  ```
- Variants: Single
- States: shown/dismissed
- Accessibility: Focus trap, title announced, back-dismiss
- Composition: M3 AlertDialog with theme tokens
- Implementation location: `ui.components.foundation.AppDialog.kt`
- Introduced in: DS 1.0.0

### `AppIconButton`

- Layer: Foundation
- Purpose: Consistent icon button with gold accent tint.
- When to use: Toolbar icons, language picker.
- When not to use: Actions needing text labels.
- API:
  ```kotlin
  @Composable fun AppIconButton(
      onClick: () -> Unit,
      contentDescription: String,
      modifier: Modifier = Modifier,
      enabled: Boolean = true,
      content: @Composable () -> Unit
  )
  ```
- Variants: Single
- States: enabled, disabled, pressed, focused
- Accessibility: contentDescription required by API, 48dp min touch
- Composition: M3 IconButton with tint = secondary color
- Implementation location: `ui.components.foundation.AppIconButton.kt`
- Introduced in: DS 1.0.0

### `AppGhostButton`

- Layer: Application
- Purpose: Low-emphasis action button with no fill and no border. Lightest touch in the Primary / Secondary / Ghost button hierarchy.
- When to use: Tertiary CTAs, cancel/dismiss actions in cards or dialogs where a border would be visually heavy.
- When not to use: Primary or secondary screen CTAs; icon-only actions (use `AppIconButton`).
- API:
  ```kotlin
  @Composable fun AppGhostButton(
      text: String,
      onClick: () -> Unit,
      modifier: Modifier = Modifier,
      enabled: Boolean = true,
      icon: ImageVector? = null
  )
  ```
- Colors:
  - Enabled: container = `Color.Transparent`, content (text + icon) = `secondary` (#FFB300 gold)
  - Disabled: container = `Color.Transparent`, content = `onSurface.copy(alpha = 0.38f)`
- Variants: Single
- States: enabled, disabled, pressed (ripple on transparent), focused (gold focus ring)
- Accessibility: Min 48dp touch, button role, disabled announced
- Composition: Delegates to `BaseButton` — inherits shape=large (16dp), padding 24h×20v, textStyle=titleMedium, 24dp icons. Sets `border = null`.
- Implementation location: `ui.components.AppGhostButton.kt`
- Introduced in: DS 1.1.0

### `MenuButton`

- Layer: Application
- Purpose: Card-shaped menu option with icon + label and gold accent border.
- When to use: Main menu screen navigation items.
- When not to use: In-game UI, dialogs.
- API:
  ```kotlin
  @Composable fun MenuButton(
      text: String,
      onClick: () -> Unit,
      modifier: Modifier = Modifier,
      icon: ImageVector? = null,
      enabled: Boolean = true
  )
  ```
- Variants: Single (surface background, gold left-border, 12dp corners)
- States: enabled, disabled, pressed, focused
- Accessibility: Button role, merged icon+text semantics
- Composition: Surface + Row(icon, text) styled with theme tokens; gold 3dp left border
- Implementation location: `ui.components.MenuButton.kt`
- Introduced in: DS 1.0.0

## 4. Deprecated Components

| Component | Reason | Replacement | Migration status |
|-----------|--------|-------------|------------------|
| — | — | — | — |

## 5. Design Tokens Referenced

- Colors: primary, onPrimary, secondary, onSecondary, background, onBackground, surface, onSurface, surfaceVariant, outline, error, onError
- Typography: displayLarge, titleLarge, titleMedium, bodyLarge, labelLarge, labelMedium
- Shapes: medium (12dp), large (16dp)
- Spacing: xs (8dp), sm (12dp), md (16dp), lg (24dp), xl (32dp)
- Elevation: low (2dp), medium (4dp)

## 6. Change Log

- Version: 1.1.0
- Change: Added `AppGhostButton` Application Component. Transparent container, gold content, BaseButton foundation.
- Reason: Completes Primary / Secondary / Ghost button hierarchy for low-emphasis actions.

- Version: 1.0.0
- Change: Initial catalog with 4 Foundation + 1 Application components for Menu Screen.
- Reason: Greenfield design system establishment.
