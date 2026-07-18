# Task 005: Update Koin 3.x → 4.1.0

## Context
Koin 4.x unified its artifacts. The project currently uses 3 separate version entries:
- `koin = "3.5.6"` (koin-core, koin-android)
- `koin-compose = "1.1.5"` (koin-compose)
- `koin-compose-viewmodel = "1.2.0-Beta4"` (koin-compose-viewmodel)

In Koin 4.x, everything uses a single version: `4.1.0`. The `koin-compose-viewmodel` artifact no longer exists separately — it's included in `koin-compose`.

## Objective
Update Koin to 4.1.0 and fix all code that uses changed APIs.

## Scope

### 1. `gradle/libs.versions.toml`
- Replace the 3 koin version entries with a single one: `koin = "4.1.0"`
- Remove `koin-compose` and `koin-compose-viewmodel` version entries
- Update library declarations:
  - `koin-core`, `koin-android`: use `koin` version
  - `koin-compose`: version.ref = "koin"
  - REMOVE `koin-compose-viewmodel` library entry entirely

### 2. `composeApp/build.gradle.kts`
- Remove the `implementation(libs.koin.compose.viewmodel)` line from commonMain dependencies (its functionality is now in koin-compose)

### 3. Code changes (imports)
- In `UiModule.kt`: change `import org.koin.compose.viewmodel.dsl.viewModelOf` → `import org.koin.core.module.dsl.viewModelOf`
- In all Screen files using `koinViewModel`: change `import org.koin.compose.viewmodel.koinViewModel` → `import org.koin.compose.viewmodel.koinViewModel` (this import stays the same in 4.x)
- In all Screen files: REMOVE `import org.koin.core.annotation.KoinExperimentalAPI` and remove `@OptIn(KoinExperimentalAPI::class)` annotations (no longer needed)

### 4. Check for `@OptIn(KoinExperimentalAPI::class)` usage
Search and remove all `@OptIn(KoinExperimentalAPI::class)` annotations in the codebase.

## Non-goals
- Do NOT update any other dependencies.

## Constraints
- The `koin-compose` module in Koin 4.x uses `io.insert-koin:koin-compose` with version `4.1.0`.
- The `koin-android` module also uses version `4.1.0`.
