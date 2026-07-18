# Task 001: Update Gradle Wrapper

## Context
Project uses Gradle 8.9 (from late 2024). Current latest stable is 8.14.

## Objective
Update the Gradle wrapper to 8.14.

## Scope
- Run `./gradlew wrapper --gradle-version 8.14` to update the wrapper.
- This updates: `gradle/wrapper/gradle-wrapper.properties`, `gradle/wrapper/gradle-wrapper.jar`, `gradlew`, `gradlew.bat`.

## Non-goals
- Do NOT update any other dependencies.
- Do NOT modify build scripts.

## Constraints
- Verify the wrapper updated correctly by running `./gradlew --version`.
