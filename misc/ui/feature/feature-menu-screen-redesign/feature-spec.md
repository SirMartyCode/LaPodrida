# Feature Specification: Menu Screen Redesign

> Canonical UX contract for the Menu Screen redesign.
>
> Owner: Product & UX Architect · Consumers: Design System Architect, UI Designer, Compose Architect, Orchestrator · Status: draft

## Metadata

- Feature: Menu Screen Redesign
- Version: 1.0
- Status: draft
- Owner: @ui-product-ux-architect
- Last updated: 2026-08-10
- Depends on: Design System (theme tokens, custom components)

## 1. Feature

### Objective

Replace the placeholder MenuScreen with a thematic, polished entry point that communicates "card game" personality and provides clear navigation hierarchy.

### User Outcome

User opens the app and immediately understands it's a card game companion. They can quickly start a new game, continue an existing one, or browse history — with no confusion about what's available.

### Scope

- Menu screen layout, hierarchy, and interaction states
- Visual identity direction (theme guidance for designers)
- Language picker redesign (subtle)
- Dialog redesign (thematic)

### Out of Scope

- Actual visual design tokens, color values, typography choices (→ Design System Architect)
- Component APIs and implementation (→ Compose Architect)
- Other screens beyond Menu

## 2. User Goals

- **Primary:** Navigate to the desired game action (new/continue/history) within 1-2 seconds
- **Secondary:** Change language without disrupting flow

## 3. Primary Flow

1. User opens app
2. Screen loads; checks if a game-in-progress exists and if history exists
3. Screen displays title/branding + action buttons with correct enabled/disabled states
4. User taps desired action → navigates away

## 4. Secondary Flows

### New Game with existing game in progress

1. User taps "New Game"
2. Themed confirmation dialog appears warning current game will be deleted
3. User confirms → game deleted → navigates to new game setup
4. (Or dismisses → returns to menu)

### Game was deleted externally

1. User taps "Continue"
2. Info dialog appears explaining game no longer exists
3. User acknowledges → returns to menu with updated state

### Language change

1. User taps language selector (subtle, top-right)
2. Dropdown/popover shows 3 options (EN/ES/CA) with flags or short labels
3. User selects → UI re-renders in new language, selector closes

## 5. Navigation

- Entry point: App launch (root destination)
- Destinations: New Game Setup, Continue Game (scoreboard), Game History
- Back behaviour: System back exits app (this is root)

## 6. Screens

| Screen | Purpose | Changed |
|--------|---------|---------|
| MenuScreen | App entry, primary navigation hub | Redesigned |

## 7. States

### Loading

- Shown briefly while checking if game-in-progress/history exist
- "New Game" is always enabled (no dependency on async check)
- "Continue" and "History" buttons show a subtle loading indicator (skeleton shimmer or muted state) until data is resolved
- Duration: typically <500ms; no full-screen spinner

### No Game In Progress (idle state)

- "New Game" — prominent, primary action
- "Continue" — visually muted/disabled, non-interactive
- "Game History" — enabled or disabled depending on history existence

### Game In Progress

- "Continue" becomes enabled and visually elevated (secondary prominence)
- "New Game" remains available but user should understand it may overwrite

### Error

- If the async check fails, default to: New Game enabled, Continue disabled, History disabled
- No error banner needed (silent graceful degradation)

## 8. Edge Cases

- **First-ever launch:** No game, no history → only "New Game" is actionable. This should feel inviting, not empty.
- **Rapid tap:** Buttons should debounce/disable after first tap to prevent double-navigation.
- **Language change mid-dialog:** If a dialog is showing and language changes, dialog text should update in-place (already handled by string resources).

## 9. Accessibility Requirements

- All buttons must have content descriptions
- Disabled buttons must announce their disabled state to screen readers
- Dialogs must trap focus and announce title on open
- Language picker must be operable via keyboard/switch access
- Minimum touch target: 48dp for all interactive elements
- Contrast: follow WCAG 2.1 AA (specific values → Design System)

## 10. UX Decisions

### Decision: Visual Identity — Card Table Theme

- **Decision:** The menu evokes a classic card table — dark green felt background, elegant serif or semi-serif title typography, subtle card suit decorations (♠♥♦♣) as ornamental accents (not as buttons)
- **Rationale:** Immediately communicates the app's domain; creates premium/fun atmosphere without being garish
- **Alternatives rejected:** Minimalist flat (too generic), skeuomorphic casino (too heavy/dated), playful cartoon (doesn't match the strategic nature of La Podrida)

### Decision: Button Hierarchy — Card-shaped buttons

- **Decision:** Menu actions styled as cards (rounded-rect with subtle border/shadow, portrait proportion). "New Game" is largest/most prominent. "Continue" and "History" are smaller, side-by-side or stacked below.
- **Rationale:** Reinforces card-game metaphor while providing clear visual hierarchy
- **Alternatives rejected:** Standard Material buttons (no personality), FAB-style (doesn't suit 3 equal-ish options)

### Decision: Language Picker — Icon-only dropdown

- **Decision:** Small globe icon or current-language flag in top-right corner. Tapping opens a compact dropdown with 3 language options.
- **Rationale:** Language changes are rare; picker should not compete with primary actions for attention
- **Alternatives rejected:** Full-width segmented control (too prominent), settings screen (too hidden for 1 option)

### Decision: Dialogs — Themed card-style containers

- **Decision:** Dialogs use the same card-table aesthetic: dark card-shaped container with elegant typography, suit icon instead of generic Material icon
- **Rationale:** Maintains immersion; standard AlertDialog breaks the themed experience
- **Alternatives rejected:** Bottom sheets (overkill for confirmations), snackbars (too dismissible for destructive actions)

### Decision: Title — App name "La Podrida" as hero branding

- **Decision:** Replace "MenuScreen" placeholder with "La Podrida" in elegant display typography, optionally with a subtle suit motif or decorative underline
- **Rationale:** Establishes brand identity and fills the visual space meaningfully

## 11. Open Issues

### Issue: Animated background?

- **Problem:** Should the felt background have any subtle animation (e.g., floating suit icons, parallax) or be static?
- **Impact:** Affects perceived polish vs. battery/performance on low-end devices
- **Recommended resolution:** Start static; add subtle parallax/floating suits as enhancement if performance budget allows

### Issue: Sound effects on button press?

- **Problem:** Card game apps sometimes use audio feedback (card flip sound on tap). Is this desired?
- **Impact:** Adds polish but needs mute option and accessibility consideration
- **Recommended resolution:** Defer to v2; not blocking for redesign

## 12. Change Log

- Version 1.0 — Initial spec
- Change: Created from scratch
- Reason: MenuScreen is placeholder; needs themed redesign
