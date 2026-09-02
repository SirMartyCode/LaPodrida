## Purpose

Lets the user switch the app's active display language among the supported languages.

## ADDED Requirements

### Requirement: Supported languages
The system SHALL support exactly three languages: English, Spanish, and Catalan, each identified by its ISO language code ("en", "es", "ca").

#### Scenario: Available languages
- **WHEN** the user opens the language picker
- **THEN** the system offers exactly English, Spanish, and Catalan

### Requirement: Changing the active language
The system SHALL let the user select one of the supported languages from the home screen, and SHALL apply it as the app's active language.

#### Scenario: Selecting a language
- **WHEN** the user selects a language from the picker
- **THEN** the system sets that language as the app's active language
