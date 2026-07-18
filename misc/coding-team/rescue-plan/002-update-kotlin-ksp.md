# Task 002: Update Kotlin + KSP

## Context
Project uses Kotlin 2.0.20 and KSP 2.0.20-1.0.24. We need to update to Kotlin 2.3.21 (required by Compose Multiplatform 1.11.1) and KSP 2.3.10 (aligned with Kotlin 2.3.x).

## Objective
Update Kotlin and KSP versions in `gradle/libs.versions.toml`.

## Scope
Edit `gradle/libs.versions.toml`:
- `kotlin = "2.0.20"` → `"2.3.21"`
- `ksp = "2.0.20-1.0.24"` → `"2.3.10"`

Note: KSP 2.3.x changed its versioning scheme — it no longer embeds the Kotlin version.

## Non-goals
- Do NOT update any other dependencies yet.
- Do NOT modify build.gradle.kts files.
- Do NOT try to compile.
