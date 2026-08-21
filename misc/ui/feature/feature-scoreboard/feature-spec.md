# Feature Specification

> Canonical UX contract for the Scoreboard feature.
>
> Owner: Product & UX Architect · Consumers: Design System Architect, UI
> Designer, Compose Architect, Orchestrator · Status: draft

## Metadata

- Feature: Scoreboard — Game State Table
- Version: 0.1.0
- Status: draft
- Owner: @ui-product-ux-architect
- Last updated: 2026-08-21
- Supersedes: —
- Depends on: Design System v1.3.0, Game domain model (Game, Round, RoundParticipation)

---

## 1. Feature

### Objective

Display a live, read-only scoreboard table that shows every player's prediction
("mans demanades") and earned score for each round of the current game, plus
their running total.

### User Outcome

Players can, at any point during the game, glance at the scoreboard and know:
- Who is leading and by how much.
- What each player predicted and scored in every completed round.
- What the current round's prediction is (before scoring).
- Which rounds are still to come.

### Scope

- Read-only scoreboard table embedded in (or replacing) the current `GameScreen` stub.
- All rounds visible (past, current, future) in one scrollable table.
- Player name header row.
- Row header column showing round number and cards-per-player count.
- Per-cell display: prediction (left/narrow) + round score (right/wider).
- Totals row pinned at the bottom (or top — see §10 UX Decisions).
- Visual differentiation of completed / current / future rounds.
- Horizontal scroll for many players; vertical scroll for many rounds.

### Out of Scope

- Entering or editing predictions or scores (separate interaction feature).
- Game history / past games list.
- Animations or live score updates beyond state recomposition.
- Undo, edit, or correction flows.
- Exporting or sharing the scoreboard.

---

## 2. User Goals

- **See the current standings**: quickly identify who is winning.
- **Audit past rounds**: verify that predictions and scores were recorded correctly.
- **Track the current round**: confirm what each player predicted before the round is scored.
- **Look ahead**: know how many rounds remain and how many cards each will have.

---

## 3. Primary Flow

1. User starts or resumes a game → lands on `GameScreen`.
2. `GameScreen` displays the Scoreboard table immediately with the game's current state.
3. User reads predictions and scores for completed rounds; sees the current round's prediction; sees blank future rounds.
4. User scrolls horizontally (if many players) or vertically (if many rounds) to see the full table.
5. Totals row always visible (sticky) or reached at the end of the vertical scroll.

---

## 4. Secondary Flows

### Game just started (Round 1 — prediction phase)
- Row 1 (current) shows each player's prediction in the left cell area; right (score) area is empty.
- Rows 2..N (future) are fully empty/dimmed.
- Totals row shows all zeros.

### Mid-game — scoring phase of current round
- Current-round row still highlighted; prediction is filled; score area is still empty until confirmed.
- This state is identical in appearance to the prediction-phase state from the scoreboard's perspective — the scoreboard shows what is stored; entry belongs to a separate flow.

### Game finished
- All round rows show complete data.
- `isFinished = true` → totals row may use a distinct "winner" highlight (highest score).
- No "current round" highlight active.

---

## 5. Navigation

- **Entry point**: `GameScreen` (navigated to from `MenuScreen` via "New Game" or "Continue Game").
- **Destination**: The scoreboard is a view within `GameScreen`; it does not navigate away.
- **Back behaviour**: Back from `GameScreen` returns to `MenuScreen`. No confirmation dialog is specified in this feature (deferred).
- **Deep-link behaviour**: Not applicable for v1.

---

## 6. Screens

| Screen | Purpose | Added/Changed |
|--------|---------|---------------|
| `GameScreen` | Hosts the Scoreboard table; replaces the current stub | Changed |

No new top-level screen is introduced. The scoreboard is the primary content of `GameScreen`.

---

## 7. States

### Loading

- **When**: `GameViewModel` is fetching the game from `CurrentGameRepository`.
- **Behaviour**: Show a full-screen loading indicator (centred) in place of the table. No partial table skeleton — data must be complete before rendering.

### Empty

- Not applicable. A `GameScreen` is always entered with a valid `Game` object (created in `GameSettings` flow). If no game exists, the app should not navigate here.

### Error

- **When**: Repository fails to load the game.
- **Behaviour**: Show a full-screen error state with a descriptive message and a "Back to menu" action. No retry on this screen (user must restart from Menu).

### Success — Future round row

- Round has not been played yet (`roundIndex > currentRoundIndex`).
- Row header: round number + cards-per-player count — normal visibility.
- Prediction cell: empty / blank.
- Score cell: empty / blank.
- Row visual treatment: dimmed (reduced opacity or muted colour) to signal "not yet".

### Success — Current round row (active)

- `roundIndex == currentRoundIndex` and `isFinished == false`.
- Row header: round number + cards-per-player — full visibility + current-round indicator (e.g. gold accent or highlight).
- Prediction cell: shows `prediction` value if set; if the round has just started and no prediction entered yet, shows a placeholder dash.
- Score cell: empty (scoring not yet done).
- Row visual treatment: highlighted/accented to draw attention. This is the only row with a persistent visual accent.

### Success — Completed round row

- `roundIndex < currentRoundIndex`.
- Prediction cell: shows `prediction` value.
- Score cell: shows `score` value; if `hitPrediction == true`, apply a success indicator (e.g. subtle positive tint or a small marker); if `hitPrediction == false`, neutral or no indicator.
- Row visual treatment: normal (full opacity, no accent). Completed rows are the "default" state.

### Success — Totals row

- Always visible; pinned behaviour TBD (see §10).
- Displays cumulative score per player (sum of all `score` values across completed rounds).
- No prediction sub-cell (the prediction column is blank or hidden for this row).
- When `isFinished == true`, the player(s) with the highest total may receive a distinct visual emphasis (winner highlight). This is aspirational for v1 — see §11 Open Issues.

### Disabled

- Not applicable — this is a view-only screen with no interactive controls.

---

## 8. Edge Cases

- **2 players, few rounds**: Table fits comfortably. No scroll needed. Totals row is visible without scrolling.
- **Many players (e.g. 6–8)**: Player name headers and all data columns must scroll horizontally together. The row header column (round number + cards) must be **sticky/frozen** on the left so context is not lost during horizontal scroll.
- **Many rounds (e.g. 13+ rounds for 3–4 players)**: Table scrolls vertically. The player name header row must be **sticky/frozen** at the top so column identity is always visible.
- **Both many players AND many rounds**: Both sticky header row and sticky row-header column apply simultaneously — the top-left corner cell (empty or labelled) is frozen in both axes.
- **Long player names**: Names in the header must truncate with an ellipsis (`…`) at a defined max width. Names must not break column widths or wrap to multiple lines. Truncated names should be fully accessible via content description.
- **Indian round**: Rendered as the last row with `cardsPerPlayer = 1`. Row header should visually distinguish it (e.g. label "Indian" alongside the cards count, or a small icon marker).
- **Tied totals at game end**: Both players receive the winner highlight. No tiebreaker logic is in scope.
- **Single player**: Not a valid game configuration per `GameSettings` (minimum 2 players). No special handling needed.
- **`prediction` is 0**: A valid prediction (player bets zero tricks). Must display "0", not a blank. Distinguish explicitly from a "no data" state (dashes or empty).
- **Score of 0**: Valid outcome. Must display "0", not blank.
- **`currentRoundIndex` pointing beyond last round**: Only possible when `isFinished = true`. No current-round highlight in this state.

---

## 9. Accessibility Requirements

- **Row headers**: Each row header cell (round number + cards) must be a semantic row header so screen readers announce it before the row's cells.
- **Column headers**: Each player name header must be a semantic column header.
- **Cell content descriptions**: Each data cell must have a content description combining its row and column context, e.g. "Round 3, 3 cards – [PlayerName]: prediction 2, score 10, hit prediction".
- **Current round indicator**: Must not rely on colour alone. Use a textual or semantic marker (e.g. `stateDescription = "current round"`) in addition to any visual accent.
- **Hit prediction indicator**: Must not rely on colour alone. A textual annotation or semantic description must accompany any colour-based success marker.
- **Scrollable regions**: Both horizontal and vertical scroll regions must be accessible to assistive technology (correct scroll semantics, not blocked by gesture interceptors).
- **Minimum touch targets**: Not applicable (view-only), but focusable cells must still meet 48dp minimum for keyboard/switch-access navigation.
- **Text sizing**: All text must respect system font-scale. Cell layout must accommodate at least 1.3× system font size without clipping content.
- **Contrast**: All text must meet WCAG AA (4.5:1 for body text, 3:1 for large/UI text) against their respective backgrounds, including dimmed future-round rows.

---

## 10. UX Decisions

### Decision: Totals row position — bottom (not top)

- **Decision**: Totals row is placed at the **bottom** of the table, below the last round row.
- **Rationale**: The natural reading order follows round progression from top to bottom; the total is the result of all rounds, so it belongs at the end. This mirrors how physical score sheets work.
- **Alternatives rejected**: Pinned at the top — feels like leading with the answer before the data; would require complex sticky behaviour in both axes simultaneously.
- **Trade-off**: On a game with many rounds, the totals row may not be immediately visible. Mitigation: consider a floating/persistent score summary bar above or below the table (out of scope for v1 — see §11).

### Decision: Row header column shows "round number" + "cards per player"

- **Decision**: The row header cell displays two pieces of information: the sequential round number (1, 2, 3…) and the number of cards dealt that round (`cardsPerPlayer`).
- **Rationale**: `cardsPerPlayer` is directly actionable context — players need to know how many cards are in play to assess predictions and scores. Round number alone is insufficient.
- **Format**: Suggested label — `"R{n}"` on one line, `"{c}♠"` (or card icon) on the second line, or a compact `"R{n} · {c}"` single line. Final format is delegated to UI Designer.
- **Alternatives rejected**: Round number only — loses the cards-count context that players actively use.

### Decision: Cell split — narrow left (prediction) + wider right (score)

- **Decision**: Each data cell is horizontally divided: a narrow left area for `prediction` ("mans demanades") and a wider right area for `score`.
- **Rationale**: Prediction is a small integer (0–`cardsPerPlayer`); score is a larger number that benefits from more space. The asymmetric split matches their relative information density. The split also mirrors physical score sheets used in La Podrida.
- **Visual weight**: The prediction sub-cell should be visually de-emphasised relative to the score (e.g. smaller type, muted colour, or a subtle divider). The score is the primary data point.
- **Alternatives rejected**: Equal split — wastes space on predictions; stacked (prediction on top, score below) — increases row height and hurts density.

### Decision: View-only for v1

- **Decision**: The scoreboard is entirely read-only. No tapping on cells, no inline editing.
- **Rationale**: Keeps scope focused. Prediction/score entry is a separate, more complex interaction flow.
- **Trade-off**: Users must use a separate input flow to record data (not yet designed). The scoreboard alone does not complete the game loop.

### Decision: Horizontal scroll for many players; sticky row-header column

- **Decision**: The table scrolls horizontally when columns overflow. The row-header column (round info) is sticky.
- **Rationale**: Preserves round context as players scroll right through many player columns. Without stickiness, users lose orientation.
- **Alternatives rejected**: Horizontal paging/tabs per player — loses the comparative side-by-side view which is the primary value of a shared scoreboard.

### Decision: Indian round visually distinguished in row header

- **Decision**: The Indian round row header is marked distinctly (label or icon) in addition to its card count.
- **Rationale**: The Indian round has special rules and status in La Podrida. Players expect it to stand out. A purely numeric row header would not convey its special nature.
- **Exact visual treatment**: Delegated to UI Designer.

---

## 11. Open Issues

### Issue 1: Persistent score summary outside the table

- **Problem**: With many rounds, the totals row is not visible without scrolling to the bottom. Players frequently want to know the current standings at a glance.
- **Impact**: Reduces usability for long games; players must scroll down to see who is winning.
- **Recommended resolution**: Design a persistent "score summary bar" (e.g. condensed player names + current totals) anchored above or below the table, visible at all times. Defer to v1.1.

### Issue 2: Prediction placeholder vs. "not yet entered"

- **Problem**: `RoundParticipation.prediction` is always initialised to `0` in the domain model (see `Game.create()`). There is currently no way to distinguish "player predicted 0 tricks" from "prediction not yet entered for this round".
- **Impact**: The scoreboard cannot reliably distinguish an intentional 0-prediction from an unrecorded prediction for the current or future rounds. This could mislead users.
- **Recommended resolution**: The domain model should introduce a nullable `prediction: Int?` (null = not yet entered, 0 = valid zero prediction). This is a domain-level change outside the UI scope but blocks accurate scoreboard display for the current round. @ui-orchestrator should flag this to the domain/data layer owners.

### Issue 3: Winner highlight on tie

- **Problem**: If two or more players share the highest score at game end, the spec says both receive the winner highlight — but no tiebreaker rule exists.
- **Impact**: Low — cosmetic only. Does not affect gameplay.
- **Recommended resolution**: Confirm with product owner whether ties are valid outcomes in La Podrida or if a tiebreaker rule exists. Cosmetic highlight behaviour follows from that answer.

### Issue 4: Confirm minimum/maximum player count

- **Problem**: The scoreboard layout (sticky column, column widths, name truncation) must be tuned to the real player count range. The domain allows `48 / playerNames.size` as maxRounds logic, implying at minimum 2 and practically up to ~8 players.
- **Impact**: Column width strategy and truncation thresholds depend on this range.
- **Recommended resolution**: Confirm the exact min/max player count enforced by `GameSettings`. Provide this to UI Designer before layout decisions are made.

---

## 12. Change Log

- Version: 0.1.0
- Change: Initial draft.
- Reason: Greenfield feature spec for Scoreboard / GameScreen.
