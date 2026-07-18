# Task 006: Fix collectAsState compilation errors

## Context
After updating Kotlin to 2.3.21, the compiler can no longer infer the type parameter for `collectAsState()` with `StateFlow` + `by` delegate in 3 screen files. This is a known Kotlin 2.3 stricter type inference change.

## Objective
Fix the compilation errors by replacing `collectAsState()` with `collectAsStateWithLifecycle()` from the lifecycle-runtime-compose library, which is the recommended approach and works correctly with the updated compiler.

## Scope
In each of these 3 files:
- `composeApp/src/commonMain/kotlin/com/sirmarty/lapodrida/ui/screens/menu/MenuScreen.kt`
- `composeApp/src/commonMain/kotlin/com/sirmarty/lapodrida/ui/screens/game/GameScreen.kt`
- `composeApp/src/commonMain/kotlin/com/sirmarty/lapodrida/ui/screens/gamesettings/GameSettingsScreen.kt`

Do:
1. Replace `import androidx.compose.runtime.collectAsState` with `import androidx.lifecycle.compose.collectAsStateWithLifecycle`
2. Replace `.collectAsState()` with `.collectAsStateWithLifecycle()`

## Non-goals
- Do NOT change any other code.
